package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.ActiveIntentionViewDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.HelperDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionRegisterDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionViewDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceNotInAValidRangeException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IIntentionRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.ICryptocurrencyService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IIntentionService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class IntentionService implements IIntentionService {

    private final HelperDTO helper = new HelperDTO();

    @Autowired
    private IIntentionRepo intentionRepo;

    @Autowired
    private ICryptocurrencyService cryptocurrencyService;

    @Autowired
    private IUserService userService;

    @Override
    public Intention create(IntentionRegisterDTO intentionRegisterDTO) throws ResourceNotFoundException, PriceNotInAValidRangeException {
        Cryptocurrency cryptocurrency = cryptocurrencyService.findById(intentionRegisterDTO.getCryptocurrencyId());
        User user = userService.getFromDataBase(intentionRegisterDTO.getUserId());
        if (cryptocurrency.latestQuote().intentionPriceInARangeOfFiveUpAndDown(intentionRegisterDTO.getPrice())) {
            Intention intention = new Intention(intentionRegisterDTO.getType(), cryptocurrency, intentionRegisterDTO.getPrice(),
                    intentionRegisterDTO.getUnits(), user);
            return intentionRepo.save(intention);
        } else {
            throw new PriceNotInAValidRangeException("Price exceed five percent with respect to latest quote, price selected: " + intentionRegisterDTO.getPrice()
                    + " ,price latest quote: " + cryptocurrency.latestQuote().getPrice());
        }
    }

    @Override
    public IntentionViewDTO open(IntentionRegisterDTO intentionRegisterDTO) throws PriceNotInAValidRangeException, ResourceNotFoundException {
        Intention intention = this.create(intentionRegisterDTO);
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
    public IntentionViewDTO getIntentionById(int id) throws ResourceNotFoundException {
        Intention intention = this.findById(id);
        return helper.intentionToIntentionView(intention, intention.getUser());
    }

    @Override
    public List<ActiveIntentionViewDTO> getActiveIntentions() {
        return this.findActiveIntentions().stream().map(intention -> helper.intentionToActiveIntentionView(intention
                ,intention.getUser())).toList();
    }

    public Intention findById(int id) throws ResourceNotFoundException {
        return intentionRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Intention not found with id " + id)
        );
    }

    public List<Intention> findActiveIntentions(){
        return intentionRepo.findAll(Sort.by(Sort.Direction.ASC,"id")).stream()
                .filter(i -> !i.isTaken())
                .toList();
    }

    @Override
    public List<IntentionViewDTO> getAll() {
        return intentionRepo.findAll(Sort.by(Sort.Direction.ASC,"id")).stream().map(intention -> helper.intentionToIntentionView(intention
        ,intention.getUser())).toList();
    }
}