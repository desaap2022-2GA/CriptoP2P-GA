package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationModify;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationView;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.IntentionAlreadyTaken;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.InvalidState;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceExceedVariationWithRespectIntentionTypeLimits;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import java.util.List;

public interface IOperationService {
    Operation create(OperationRegister operationRegister) throws ResourceNotFound, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits;

    OperationView open(OperationRegister operationRegister) throws ResourceNotFound, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits;

    void update(Operation operation);

    void delete(int id);

    void deleteAll();

    Operation findById(int id) throws ResourceNotFound;

    List<Operation> getAll();

    void cancelOperationByUser(Operation operation, User user) throws ResourceNotFound;

    void moneyTransferDone(Operation operation) throws ResourceNotFound;

    void cryptoSendDone(Operation operation) throws ResourceNotFound;

    void assignBonusTimeToUsers(Operation operation);

    void addAnOperationToUsers(Operation operation);

    void modify(OperationModify operationModify) throws ResourceNotFound, InvalidState;
}
