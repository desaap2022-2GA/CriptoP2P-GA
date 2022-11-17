package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.HelperDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationModify;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationView;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Operation operation;
        if (!intention.isTaken()) {
            intention.setTaken(true);
            intentionService.update(intention);
            operation = new Operation(intention, user);//usuario Que Acepta La Intencion
            if (intention.getCryptocurrency().latestQuote()
                    .priceExceedVariationWithRespectTheIntentionPriceAccordingIntentionTypeLimits(intention.getPrice(),
                            intention.getType())) {
                operation.cancelOperationBySystem();
                operationRepo.save(operation);
                throw new PriceExceedVariationWithRespectIntentionTypeLimits("Price exceeds variation according the " +
                        "intention type limits, price of intention: " + intention.getPrice() + " ,price latest quote: " +
                        intention.getCryptocurrency().latestQuote().getPrice());
            }
        } else {
            throw new IntentionAlreadyTaken("The intention is already taken");
        }
        return operationRepo.save(operation);
    }

    @Override
    public OperationView open(OperationRegister operationRegister) throws ResourceNotFound, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operationCreated = this.create(operationRegister);
        return helper.operationToOperationView(operationCreated, operationCreated.getUserWhoAccepts());
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

    public OperationView getOperationById(int operationId, int userId) throws ResourceNotFound {
        Operation operation = findById(operationId);
        User userWhoAsk = userService.getFromDataBase(userId);
        return helper.operationToOperationView(operation, userWhoAsk);
    }

    @Override
    public void cancelOperationByUser(Operation operation, User user) {
        operation.cancelOperationByUser(user);
        operationRepo.save(operation);
    }

    @Override
    public void moneyTransferDone(Operation operation) {
        operation.moneyTransferredDone();
        operationRepo.save(operation);
    }

    @Override
    public void cryptoSendDone(Operation operation) {
        operation.cryptoSendDone();
        operationRepo.save(operation);
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
