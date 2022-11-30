package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationModifyDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationRegisterDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationViewDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.IntentionAlreadyTakenException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.InvalidStateException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceExceedVariationWithRespectIntentionTypeLimitsException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;

public interface IOperationService {
    Operation create(OperationRegisterDTO operationRegisterDTO, User user) throws ResourceNotFoundException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException;

    OperationViewDTO open(OperationRegisterDTO operationRegisterDTO, User user) throws ResourceNotFoundException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException;

    void update(Operation operation);

    void delete(int id);

    void deleteAll();

    OperationViewDTO getOperationById(int operationId, int userId) throws ResourceNotFoundException;

    Operation findById(int id) throws ResourceNotFoundException;

    void cancelOperationByUser(Operation operation, User user) throws ResourceNotFoundException;

    void moneyTransferDone(Operation operation) throws ResourceNotFoundException;

    void cryptoSendDone(Operation operation) throws ResourceNotFoundException;

    void assignBonusTimeToUsers(Operation operation);

    void addAnOperationToUsers(Operation operation);


    void modify(OperationModifyDTO operationModifyDTO) throws ResourceNotFoundException, InvalidStateException;

}
