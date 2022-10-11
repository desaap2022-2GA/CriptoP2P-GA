package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IOperationRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.OperationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationService implements IOperationService {

    @Autowired
    IOperationRepo operationRepo;

    @Autowired
    IUserService userService;

    @Autowired
    IIntentionService intentionService;

    @Override
    public Operation create(OperationRegister operationRegister) throws ResourceNotFound {
        User user = userService.getFromDataBase(operationRegister.getUserId());
        Intention intention = intentionService.findById(operationRegister.getIntentionId());
        Operation operation = new Operation(intention, user);
        return operationRepo.save(operation);
    }

    @Override
    public void update(Operation operation) {
        operationRepo.save(operation);
    }

    @Override
    public void delete(int id) {
        operationRepo.deleteById(id);
    }

    @Override
    public void deleteAll() {
        operationRepo.deleteAll();
    }

    @Override
    public Operation findById(int id) throws ResourceNotFound {
        return operationRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("Operation not found with id " + id)
        );
    }

    @Override
    public List<Operation> getAll() {
        return operationRepo.findAll();
    }

    @Override
    public IntentionType getType(Operation operation) {
        return operation.getType();
    }

    @Override
    public String getTransactionInfoToShow(Operation operation) {
        return operation.getTransactionInfoToShow();
    }

    @Override
    public int getUserReputation(Operation operation) {
        return operation.getUserReputation();
    }

    @Override
    public String actionToDo(Operation operation, User user) {
        return operation.actionToDo(user);
    }

    @Override
    public void cancelOperationByUser(Operation operation, User user) {
        operation.cancelOperationByUser(user);
        this.update(operation);
    }

    @Override
    public void moneyTransferDone(Operation operation) {
        operation.moneyTranferedDone();
        this.update(operation);
    }

    @Override
    public void cryptoSendDone(Operation operation) {
        operation.cryptoSendDone();
    }

    @Override
    public void assignBonusTimeToUsers(Operation operation) {
        operation.bonusTimeOperationAssign();
        this.update(operation);
    }

    @Override
    public double amountInDollars(Operation operation, double amount, double dollarQuote) {
        return operation.amountInDollars(amount, dollarQuote);
    }

    @Override
    public OperationState getState(Operation operation) {
        return operation.getState();
    }
}
