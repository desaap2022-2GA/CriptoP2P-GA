package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice;


import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionView;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceNotInAValidRange;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;

import java.util.List;

public interface IIntentionService {
    Intention create(IntentionRegister intentionRegister) throws ResourceNotFound, PriceNotInAValidRange;

    void update(Intention intention);

    void delete(int id);

    void deleteAll();

    List<IntentionView> getActiveIntentions();

    Intention findById(int id) throws ResourceNotFound;

    IntentionView getIntentionById(int id) throws ResourceNotFound;

    IntentionView open(IntentionRegister intentionRegister) throws PriceNotInAValidRange, ResourceNotFound;

    List<IntentionView> getAll();
}
