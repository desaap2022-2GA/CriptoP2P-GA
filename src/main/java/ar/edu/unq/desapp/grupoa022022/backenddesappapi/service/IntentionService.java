package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IIntentionRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IntentionService implements IIntentionService {

    @Autowired
    private IIntentionRepo intentionRepo;

    @Override
    public Intention create(IntentionType type, Cryptocurrency cryptocurrency, Double price, int units, User user) {
        Intention intention = new Intention(type, cryptocurrency, price, units, user);
        return intentionRepo.save(intention);
    }

    @Override
    public void update(Intention intention) {
        intentionRepo.save(intention);
    }

    @Override
    public void delete(int id) {
        intentionRepo.deleteById(id);
    }

    @Override
    public void deleteAll() {
        intentionRepo.deleteAll();
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
    public double amountPriceInDollars(Double dollarPrice, Intention intention) {
        return intention.amountPriceInDollars(dollarPrice);
    }

    @Override
    public double amountPriceInPesos(Intention intention) {
        return intention.amountPriceInPesos();
    }

    @Override
    public String transactionInfoToShow(Intention intention) {
        return intention.getTransactionInfoToShow();
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
