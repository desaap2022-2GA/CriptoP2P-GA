package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.HelperDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationModifyDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationRegisterDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationViewDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.IntentionAlreadyTakenException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.InvalidStateException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceExceedVariationWithRespectIntentionTypeLimitsException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IOperationRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IIntentionService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IOperationService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IUserService;
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
    public Operation create(OperationRegisterDTO operationRegisterDTO) throws ResourceNotFoundException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException {
        User user = userService.getFromDataBase(operationRegisterDTO.getUserId());
        Intention intention = intentionService.findById(operationRegisterDTO.getIntentionId());
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
                throw new PriceExceedVariationWithRespectIntentionTypeLimitsException("Price exceeds variation according the " +
                        "intention type limits, price of intention: " + intention.getPrice() + " ,price latest quote: " +
                        intention.getCryptocurrency().latestQuote().getPrice());
            }
        } else {
            throw new IntentionAlreadyTakenException("The intention is already taken");
        }
        return operationRepo.save(operation);
    }

    @Override
    public OperationViewDTO open(OperationRegisterDTO operationRegisterDTO) throws ResourceNotFoundException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException {
        Operation operationCreated = this.create(operationRegisterDTO);
        return helper.operationToOperationView(operationCreated, operationCreated.getUserWhoAccepts());
    }

    @Override
    public void update(Operation operation) {
        operationRepo.save(operation);
    }

    public List<Operation> getAll(){
        return operationRepo.findAll();
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
    public Operation findById(int id) throws ResourceNotFoundException {
        return operationRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Operation not found with id " + id)
        );
    }

    public OperationViewDTO getOperationById(int operationId, int userId) throws ResourceNotFoundException {
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
    public void modify(OperationModifyDTO operationModifyDTO) throws ResourceNotFoundException, InvalidStateException {
        Operation operation = findById(operationModifyDTO.getOperationId());
        User user = userService.getFromDataBase(operationModifyDTO.getUserId());

        switch (operationModifyDTO.getState()) {
            case PAID -> moneyTransferDone(operation);
            case CRYPTOSENT -> {
                cryptoSendDone(operation);
                assignBonusTimeToUsers(operation);
                addAnOperationToUsers(operation);
            }
            case CANCELLED -> cancelOperationByUser(operation, user);
            default -> throw new InvalidStateException("You must provide a valid State");
        }
    }
}
