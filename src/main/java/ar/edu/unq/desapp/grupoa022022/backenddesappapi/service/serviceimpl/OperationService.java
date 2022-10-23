package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.HelperDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationModify;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.IntentionAlreadyTaken;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.InvalidState;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceExceedVariationWithRespectIntentionTypeLimits;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IOperationRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IIntentionService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IOperationService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IUserService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.OperationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationService implements IOperationService {

    private final HelperDTO helper = new HelperDTO();
    @Autowired
    IOperationRepo operationRepo;

    @Autowired
    IUserService userService;

    @Autowired
    IIntentionService intentionService;

    @Override
    public Operation create(OperationRegister operationRegister) throws ResourceNotFound, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        User user = userService.getFromDataBase(operationRegister.getUserId());
        Intention intention = intentionService.findById(operationRegister.getIntentionId());
        if (!intention.isTaken()) {
            intention.setTaken(true);
            intentionService.update(intention);
            Operation operation = new Operation(intention, user);//usuarioQueAceptaLaIntencion
            if (intention.getCryptocurrency().latestQuote()
                    .priceExceedVariationWithRespectTheIntentionPriceAccordingIntentionTypeLimits(intention.getPrice(),
                            intention.getType())) {
                operation.cancelOperationBySystem();
                operationRepo.save(operation);
                throw new PriceExceedVariationWithRespectIntentionTypeLimits("Price exceeds variation according the intention type limits");
            }
           // userService.update(operation.getIntention().getUser());
           // intentionService.update(intention);
            return operationRepo.save(operation);
        } else {
            throw new IntentionAlreadyTaken("The intention is already taken");
        }
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
    public void cancelOperationByUser(Operation operation, User user) throws ResourceNotFound {
        operation.cancelOperationByUser(user);
        Operation originalOperation = findById(operation.getId());
        operationRepo.save(helper.operationUpdate(originalOperation, operation));
    }

    @Override
    public void moneyTransferDone(Operation operation) throws ResourceNotFound {
        Operation originalOperation = operation;
        operation.moneyTransferredDone();
        operationRepo.save(helper.operationUpdate(originalOperation, operation));
    }

    @Override
    public void cryptoSendDone(Operation operation) throws ResourceNotFound {
        Operation originalOperation = operation;
        operation.cryptoSendDone();
       operationRepo.save(helper.operationUpdate(originalOperation, operation));
    }

    @Override
    public void assignBonusTimeToUsers(Operation operation) {
        operation.bonusTimeOperationAssign();
        this.update(operation);
    }

    public void addAnOperationToUsers(Operation operation) {
        operation.addAnOperationToUsers();
        this.update(operation);
    }

    @Override
    public OperationState getState(Operation operation) {
        return operation.getState();
    }

    @Override
    public void modify(OperationModify operationModify) throws ResourceNotFound, InvalidState {
        Operation operation = findById(operationModify.getOperationId());
        User user = userService.getFromDataBase(operationModify.getUserId());

        switch (operationModify.getState()) {
            case PAID -> moneyTransferDone(operation);
            case CRYPTOSENT -> {
                cryptoSendDone(operation);
                assignBonusTimeToUsers(operation);
                addAnOperationToUsers(operation);
            }
            case CANCELLED -> cancelOperationByUser(operation, user);
            default -> throw new InvalidState("You must provide a valid State");
        }
    }
}
