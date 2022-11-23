package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationModify;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationView;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.IntentionAlreadyTakenException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.InvalidStateException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceExceedVariationWithRespectIntentionTypeLimitsException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;

public interface IOperationService {
    Operation create(OperationRegister operationRegister) throws ResourceNotFoundException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException;

    OperationView open(OperationRegister operationRegister) throws ResourceNotFoundException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException;

    void update(Operation operation);

    void delete(int id);

    void deleteAll();

    OperationView getOperationById(int operationId, int userId) throws ResourceNotFoundException;

    Operation findById(int id) throws ResourceNotFoundException;

    void cancelOperationByUser(Operation operation, User user) throws ResourceNotFoundException;

    void moneyTransferDone(Operation operation) throws ResourceNotFoundException;

    void cryptoSendDone(Operation operation) throws ResourceNotFoundException;

    void assignBonusTimeToUsers(Operation operation);

    void addAnOperationToUsers(Operation operation);


    void modify(OperationModify operationModify) throws ResourceNotFoundException, InvalidStateException;

}
