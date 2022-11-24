package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice;


import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.ActiveIntentionViewDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionRegisterDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionViewDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceNotInAValidRangeException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IIntentionService {
    Intention create(IntentionRegisterDTO intentionRegisterDTO) throws ResourceNotFoundException, PriceNotInAValidRangeException;

    void update(Intention intention);

    void delete(int id);

    void deleteAll();

    List<ActiveIntentionViewDTO> getActiveIntentions();

    Intention findById(int id) throws ResourceNotFoundException;

    IntentionViewDTO getIntentionById(int id) throws ResourceNotFoundException;

    IntentionViewDTO open(IntentionRegisterDTO intentionRegisterDTO) throws PriceNotInAValidRangeException, ResourceNotFoundException;

    List<IntentionViewDTO> getAll();
}
