package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.HelperDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionView;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceNotInAValidRange;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IIntentionRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IOperationRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.ICryptocurrencyService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IIntentionService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IOperationService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class IntentionService implements IIntentionService {

    private final HelperDTO helper = new HelperDTO();

    @Autowired
    private IIntentionRepo intentionRepo;

   // @Autowired
    //private IOperationRepo operationRepo;

    @Autowired
    private ICryptocurrencyService cryptocurrencyService;

    @Autowired
    private IUserService userService;

   // @Autowired
  //  private IOperationService operationService;

    @Override
    public Intention create(IntentionRegister intentionRegister) throws ResourceNotFound, PriceNotInAValidRange {
        Cryptocurrency cryptocurrency = cryptocurrencyService.findById(intentionRegister.getCryptocurrencyId());
        User user = userService.getFromDataBase(intentionRegister.getUserId());
        if (cryptocurrency.latestQuote().intentionPriceInARangeOfFiveUpAndDown(intentionRegister.getPrice())) {
            Intention intention = new Intention(intentionRegister.getType(), cryptocurrency, intentionRegister.getPrice(),
                    intentionRegister.getUnits(), user);
            return intentionRepo.save(intention);
        } else {
            throw new PriceNotInAValidRange("Price exceed five percent with respect to latest quote, price selected: " + intentionRegister.getPrice()
                    + " ,price latest quote: " + cryptocurrency.latestQuote().getPrice());
        }
    }

    @Override
    public IntentionView open(IntentionRegister intentionRegister) throws PriceNotInAValidRange, ResourceNotFound {
        Intention intention = this.create(intentionRegister);
        return helper.intentionToIntentionView(intention, intention.getUser());
    }

    @Override
    public void update(Intention intention) {
        intentionRepo.save(intention);
    }

    @Override
    public void delete(int id) {
        intentionRepo.findById(id).ifPresent(intention -> {
            Cryptocurrency cryptocurrency = intention.getCryptocurrency();
            cryptocurrency.removeIntention(intention);
            cryptocurrencyService.update(cryptocurrency);
            intention.setOperation(null);
            //this.existsOperation(intention);
            intentionRepo.deleteById(id);
        });
    }
/*
    public void existsOperation(Intention intention){
        operationRepo.findById(intention.getOperation().getId()).ifPresent(operation -> {
                operationService.delete(intention.getOperation().getId());
        });
    }
*/
    @Override
    public void deleteAll() {
        intentionRepo.findAll().forEach(intention -> this.delete(intention.getId()));
    }

    @Override
    public IntentionView getIntentionById(int id) throws ResourceNotFound {
        Intention intention = this.findById(id);
        return helper.intentionToIntentionView(intention, intention.getUser());
    }

    @Override
    public List<IntentionView> getActiveIntentions() {
        return this.findActiveIntentions().stream().map(intention -> helper.intentionToIntentionView(intention
                ,intention.getUser())).toList();
    }

    public Intention findById(int id) throws ResourceNotFound {
        return intentionRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("Intention not found with id " + id)
        );
    }

    public List<Intention> findActiveIntentions(){
        return intentionRepo.findAll(Sort.by(Sort.Direction.ASC,"id")).stream()
                .filter(i -> !i.isTaken())
                .toList();
    }

    @Override
    public List<IntentionView> getAll() {
        return intentionRepo.findAll(Sort.by(Sort.Direction.ASC,"id")).stream().map(intention -> helper.intentionToIntentionView(intention
        ,intention.getUser())).toList();
    }
}