package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service;


import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;

import java.util.List;

public interface IIntentionService {


    Intention create(IntentionRegister intentionRegister) throws ResourceNotFound;


    void update(Intention intention);

    void delete(int id);

    void deleteAll();

    Intention findById(int id) throws ResourceNotFound;

    List<Intention> getAll();

    double amountPriceInDollars(Double dollarPrice, Intention intention);

    double amountPriceInPesos(Intention intention);

    String transactionInfoToShow(Intention intention);

    int getOperationNumberUser(Intention intention) throws ResourceNotFound;

    int getUserReputation(Intention intention) throws ResourceNotFound;
}
