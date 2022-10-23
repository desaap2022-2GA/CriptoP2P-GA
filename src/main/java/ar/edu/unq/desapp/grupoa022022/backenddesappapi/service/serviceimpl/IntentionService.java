package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceNotInAValidRange;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IIntentionRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.ICryptocurrencyService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IIntentionService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IntentionService implements IIntentionService {

    @Autowired
    private IIntentionRepo intentionRepo;

    @Autowired
    private ICryptocurrencyService cryptocurrencyService;

    @Autowired
    private IUserService userService;

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
    public void update(Intention intention) {
        intentionRepo.save(intention);
    }

    @Override
    public void delete(int id) {
        intentionRepo.findById(id).ifPresent(intention -> {
            Cryptocurrency cryptocurrency = intention.getCryptocurrency();
            cryptocurrency.removeIntention(intention);
            cryptocurrencyService.update(cryptocurrency);
            intentionRepo.deleteById(id);
        });
    }

    @Override
    public void deleteAll() {
        intentionRepo.findAll().forEach(intention -> this.delete(intention.getId()));
    }

    @Override
    public Intention findById(int id) throws ResourceNotFound {
        return intentionRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("Intention not found with id " + id)
        );
    }

    @Override
    public List<Intention> getAll() {
        return intentionRepo.findAll();
    }

    @Override
    public double amountPriceInPesos(Intention intention) {
        return intention.amountPriceInPesos();
    }

    @Override
    public String transactionInfoToShow(Intention intention) {
        return intention.transactionInfoToShow();
    }

    @Override
    public int getOperationNumberUser(Intention intention) {
        return intention.numberOperationsUser();
    }

    @Override
    public int getUserReputation(Intention intention) {
        return intention.getUserReputation();
    }
}
