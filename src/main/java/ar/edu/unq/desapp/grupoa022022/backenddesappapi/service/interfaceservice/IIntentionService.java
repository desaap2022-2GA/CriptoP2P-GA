package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice;


import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.ActiveIntentionView;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionView;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceNotInAValidRangeException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IIntentionService {
    Intention create(IntentionRegister intentionRegister) throws ResourceNotFoundException, PriceNotInAValidRangeException;

    void update(Intention intention);

    void delete(int id);

    void deleteAll();

    List<ActiveIntentionView> getActiveIntentions();

    Intention findById(int id) throws ResourceNotFoundException;

    IntentionView getIntentionById(int id) throws ResourceNotFoundException;

    IntentionView open(IntentionRegister intentionRegister) throws PriceNotInAValidRangeException, ResourceNotFoundException;

    List<IntentionView> getAll();
}
