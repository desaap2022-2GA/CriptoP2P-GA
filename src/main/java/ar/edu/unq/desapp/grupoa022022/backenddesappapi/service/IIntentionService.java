package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;

import java.util.List;

public interface IIntentionService {

    Intention create(IntentionType type, Cryptocurrency cryptocurrency, Double price, int units, User user);

    void update(Intention intention);

    void delete(int id);

    void deleteAll();

    Intention findById(int id) throws ResourceNotFound;

    List<Intention> getAll();

    double amountPriceInDollars(Double dollarPrice, Intention intention);

    double amountPriceInPesos(Intention intention);

    String transactionInfoToShow(Intention intention);

    int getOperationNumberUser(Intention intention);

    int getUserReputation(Intention intention);

}
