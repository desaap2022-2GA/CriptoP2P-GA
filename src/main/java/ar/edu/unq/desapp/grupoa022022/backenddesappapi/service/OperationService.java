package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IOperationRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationService implements IOperationService {

    @Autowired
    IOperationRepo operationRepo;

    @Override
    public Operation create(Intention intention, User userWhoAccepts) {
        Operation operation = new Operation(intention, userWhoAccepts);
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
    }

    @Override
    public void moneyTransferDone(Operation operation) {
        operation.moneyTranferedDone();
    }

    @Override
    public void cryptoSendDone(Operation operation) {
        operation.cryptoSendDone();
    }

    @Override
    public void assignBonusTimeToUsers(Operation operation) {
        operation.bonusTimeOperationAssign();
    }

    @Override
    public double amountInDollars(Operation operation, double amount, double dollarQuote) {
        return operation.amountInDollars(amount, dollarQuote);
    }
}
