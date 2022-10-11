package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.OperationState;
import java.util.List;

public interface IOperationService {


    Operation create(OperationRegister operationRegister) throws ResourceNotFound;

    void update(Operation operation);

    void delete(int id);

    void deleteAll();

    Operation findById(int id) throws ResourceNotFound;

    List<Operation> getAll();

    IntentionType getType(Operation operation);

    String getTransactionInfoToShow(Operation operation);

    int getUserReputation(Operation operation);

    String actionToDo(Operation operation, User user);

    void cancelOperationByUser(Operation operation, User user);

    void moneyTransferDone(Operation operation);

    void cryptoSendDone(Operation operation);

    void assignBonusTimeToUsers(Operation operation);

    double amountInDollars(Operation operation, double amount, double dollarQuote);
    OperationState  getState(Operation operation);
}
