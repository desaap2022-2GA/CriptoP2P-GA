package ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationModify;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.IntentionAlreadyTaken;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceExceedVariationWithRespectIntentionTypeLimits;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceNotInAValidRange;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DateTimeInMilliseconds;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.OperationState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
class OperationPersistenceTests {

    DataSet dataSet = new DataSet();

    @Autowired
    IIntentionRepo intentionRepo;
    @Autowired
    IOperationRepo operationRepo;
    @Autowired
    IUserRepo userRepo;
    @Autowired
    ICryptocurrencyRepo cryptocurrencyRepo;
    @Autowired
    IUserService userService;
    @Autowired
    IQuoteService quoteService;
    @Autowired
    ICryptocurrencyService cryptocurrencyService;
    @Autowired
    IIntentionService intentionService;
    @Autowired
    IOperationService operationService;

    public Cryptocurrency getCryptocurrencyDB() {
        return cryptocurrencyService.create(dataSet.getCryptocurrencyRegisterDAI());
    }

    public Cryptocurrency getCryptocurrencyDB2() {
        return cryptocurrencyService.create(dataSet.getCryptocurrencyRegisterBITCOIN());
    }

    public User getUserWhoPostDB() {
        return userService.saveToDataBase(dataSet.getUserRegister());
    }

    public User getUserWhoAcceptDB2() {
        return userService.saveToDataBase(dataSet.getUserRegister2());
    }

    public User getUserWith30Point3NumberOperationsDB() {
        User userDB = getUserWhoPostDB();
        userDB.setPoints(30);
        userDB.setNumberOperations(3);
        return userRepo.save(userDB);
    }

    public int getUserWhoPostDBId() {
        return getUserWhoPostDB().getId();
    }

    public int getUserWhoAcceptDB2Id() {
        return getUserWhoAcceptDB2().getId();
    }

    public int getUserWith30Point3NumberOperationsDBId() {
        return getUserWith30Point3NumberOperationsDB().getId();
    }

    public int getSomeCryptocurrencyDBId() {
        return getCryptocurrencyDB().getId();
    }

    public int getSomeCryptocurrencyDB2Id() {
        return getCryptocurrencyDB2().getId();
    }

    public IntentionRegister getSomeIntentionRegister() {
        return new IntentionRegister(dataSet.getSomeTypeBUY(),
                getSomeCryptocurrencyDBId(), dataSet.getSomePriceInRangeDAI(), dataSet.getSomeUnit(), getUserWhoPostDBId());
    }

    public IntentionRegister getIntentionRegisterWithPrice5000Units2() {
        return new IntentionRegister(dataSet.getSomeTypeBUY(),
                getSomeCryptocurrencyDBId(), 5000.00, 2, getUserWhoPostDBId());
    }

    public IntentionRegister getIntentionRegisterWithUserWhoHas30Point3NumberOperations() {
        return new IntentionRegister(dataSet.getSomeTypeBUY(), getSomeCryptocurrencyDBId(), dataSet.getSomePriceInRangeDAI(),
                dataSet.getSomeUnit(), getUserWith30Point3NumberOperationsDBId());
    }

    public IntentionRegister getIntentionRegisterBUYType() {
        return new IntentionRegister(IntentionType.BUY,
                getSomeCryptocurrencyDBId(), dataSet.getSomePriceInRangeDAI(), dataSet.getSomeUnit(), getUserWhoPostDBId());
    }

    public IntentionRegister getIntentionRegisterSELLType() {
        return new IntentionRegister(IntentionType.SELL,
                getSomeCryptocurrencyDB2Id(), dataSet.getSomePriceInRangeBITCOIN(), dataSet.getSomeUnit(), getUserWhoPostDBId());
    }

    public Intention getIntentionDB() throws ResourceNotFound, PriceNotInAValidRange {
        return intentionService.create(getSomeIntentionRegister());
    }

    public Intention getSELLTypeIntentionDB() throws ResourceNotFound, PriceNotInAValidRange {
        return intentionService.create(getIntentionRegisterSELLType());
    }

    public Intention getBUYTypeIntentionDB() throws ResourceNotFound, PriceNotInAValidRange {
        return intentionService.create(getIntentionRegisterBUYType());
    }

    public Intention getIntentionWhoUserHas30Points3NumberOperationsDB() throws ResourceNotFound, PriceNotInAValidRange {
        return intentionService.create(getIntentionRegisterWithUserWhoHas30Point3NumberOperations());
    }

    public int getIntentionDBId() throws ResourceNotFound, PriceNotInAValidRange {
        return getIntentionDB().getId();
    }

    public int getSELLIntentionDBId() throws ResourceNotFound, PriceNotInAValidRange {
        return getSELLTypeIntentionDB().getId();
    }

    public int getBUYIntentionDBId() throws ResourceNotFound, PriceNotInAValidRange {
        return getBUYTypeIntentionDB().getId();
    }

    public OperationRegister getOperationRegisterWithUserPostWhoHas30Point3NumberOperations() throws ResourceNotFound, PriceNotInAValidRange {
        return new OperationRegister(getIntentionWhoUserHas30Points3NumberOperationsDB().getId(), getUserWhoAcceptDB2Id());
    }

    public OperationRegister getBUYOperationRegister() throws ResourceNotFound, PriceNotInAValidRange {
        return new OperationRegister(getBUYIntentionDBId(), getUserWhoAcceptDB2Id());
    }

    public OperationRegister getSELLOperationRegister() throws ResourceNotFound, PriceNotInAValidRange {
        return new OperationRegister(getSELLIntentionDBId(), getUserWhoAcceptDB2Id());
    }

    public OperationRegister getSomeOperationRegister() throws ResourceNotFound, PriceNotInAValidRange {
        return new OperationRegister(getIntentionDBId(), getUserWhoAcceptDB2Id());
    }

    public OperationRegister getOperationRegisterWithUserAcceptWhoHas30Point3NumberOperations() throws ResourceNotFound, PriceNotInAValidRange {
        return new OperationRegister(getIntentionDBId(), getUserWith30Point3NumberOperationsDBId());
    }

    @BeforeEach
    public void init() {
        //       LOG.info("startup");
        operationService.deleteAll();
        intentionService.deleteAll();
        cryptocurrencyService.deleteAll();
        userService.deleteAllUsers();
    }

    //**************** SERVICE - REPOSITORY ****************

    @Test
    void recoversPersistenceANewOperation() {
        Intention intentionDB = intentionRepo.save(new Intention(dataSet.getSomeTypeBUY(), getCryptocurrencyDB(),
                dataSet.getSomePriceInRangeDAI(), dataSet.getSomeUnit(), getUserWhoPostDB()));
        Operation saved = operationRepo.save(new Operation(intentionDB, getUserWhoAcceptDB2()));

        assertEquals(operationRepo.findById(saved.getId()).get().getId(), saved.getId());
    }

    @Test
    void getOperationIdFromANewPersistedOperation() throws ResourceNotFound, PriceNotInAValidRange, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        int operationId = operationService.create(getSomeOperationRegister()).getId();

        assertEquals(operationId, operationService.findById(operationId).getId());
    }

    @Test
    void getResourceNotFoundWhenAskForOperationThatNotExists() {
        assertThrows(ResourceNotFound.class, () -> operationService.findById(1));
    }

    @Test
    void getOperationStateFromAnOperationThatChangeState() throws ResourceNotFound, PriceNotInAValidRange, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.create(getSomeOperationRegister());
        operation.setState(OperationState.CRYPTOSENT);
        operationService.update(operation);

        assertEquals(OperationState.CRYPTOSENT, operationService.findById(operation.getId()).getState());
    }

    @Test
    void get2OperationsWhenAskForAllOfIt() throws ResourceNotFound, PriceNotInAValidRange, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        operationService.create(getBUYOperationRegister());
        operationService.create(getSELLOperationRegister());

        assertEquals(2, operationService.getAll().size());
    }

    @Test
    void getResourceNotFoundAfterDeleteTheOnlyOneAndAskForIt() throws ResourceNotFound, PriceNotInAValidRange, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.create(getSomeOperationRegister());
        operationService.delete(operation.getId());

        assertThrows(ResourceNotFound.class, () -> operationService.findById(operation.getId()));
    }

    @Test
    void getEmptyAfterDeleteAllOperationAndAskForAllOfIt() throws ResourceNotFound, PriceNotInAValidRange, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        operationService.create(getSomeOperationRegister());
        operationService.deleteAll();

        assertTrue(operationService.getAll().isEmpty());
    }

    @Test
    void getSellTypeWhenAskForAnOperationTypeMadeWithASellIntentionType() throws ResourceNotFound, PriceNotInAValidRange, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.create(getSELLOperationRegister());
        assertEquals(IntentionType.SELL, operationService.getType(operation));
    }

    @Test
    void getAddressWalletInfoWhenAskTransactionInfoInABuyIntentionTypeOperation() throws ResourceNotFound, PriceNotInAValidRange, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.create(getBUYOperationRegister());

        assertEquals("Xwf5u5ef", operationService.getTransactionInfoToShow(operation));
    }

    @Test
    void getMercadoPagoCvuInfoWhenAskTransactionInfoInASellIntentionTypeOperation() throws ResourceNotFound, PriceNotInAValidRange, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.create(getSELLOperationRegister());

        assertEquals("6352879863528798635287", operationService.getTransactionInfoToShow(operation));
    }

    @Test
    void getUserReputationFromOperation() throws ResourceNotFound, PriceNotInAValidRange, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.create(getOperationRegisterWithUserPostWhoHas30Point3NumberOperations());

        assertEquals(10, operationService.getUserReputation(operation));
    }

    @Test
    void getActionToDoMakeTransferFromOperationWithIntentionTypeSELLAndUserUserWhoAccept() throws ResourceNotFound, PriceNotInAValidRange, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.create(getSELLOperationRegister());

        assertEquals("Make transfer", operationService.actionToDo(operation, operation.getUserWhoAccepts()));
    }

    @Test
    void getActionToDoConfirmReceptionFromOperationWithIntentionTypeSELLAndUserUserWhoPost() throws ResourceNotFound, PriceNotInAValidRange, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.create(getSELLOperationRegister());

        assertEquals("Confirm reception", operationService.actionToDo(operation, operation.getIntention().getUser()));
    }

    @Test
    void getActionToDoConfirmReceptionFromOperationWithIntentionTypeBUYAndUserUserWhoAccept() throws ResourceNotFound, PriceNotInAValidRange, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.create(getBUYOperationRegister());

        assertEquals("Confirm reception", operationService.actionToDo(operation, operation.getUserWhoAccepts()));
    }

    @Test
    void getActionToDoMakeTransferFromOperationWithIntentionTypeBUYAndUserUserWhoPost() throws ResourceNotFound, PriceNotInAValidRange, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.create(getBUYOperationRegister());

        assertEquals("Make transfer", operationService.actionToDo(operation, operation.getIntention().getUser()));
    }

    @Test
    void get10WhenAskForPointsFromUserWhoPostWith30PointsAfterCancelOperation() throws ResourceNotFound, PriceNotInAValidRange, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.create(getOperationRegisterWithUserPostWhoHas30Point3NumberOperations());
        operationService.cancelOperationByUser(operation, operation.getIntention().getUser());

        assertEquals(10, userService.findById(operation.getIntention().getUser().getId()).getPoints());
    }

    @Test
    void get10WhenAskForPointsFromUserWhoAcceptWith30PointsAfterCancelOperation() throws ResourceNotFound, PriceNotInAValidRange, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.create(getOperationRegisterWithUserAcceptWhoHas30Point3NumberOperations());
        operationService.cancelOperationByUser(operation, operation.getUserWhoAccepts());

        assertEquals(10, userService.findById(operation.getUserWhoAccepts().getId()).getPoints());
    }

    @Test
    void getACTIVEStateFromNewOperation() throws ResourceNotFound, PriceNotInAValidRange, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.create(getSomeOperationRegister());
        assertEquals(OperationState.ACTIVE, operationService.getState(operation));
    }

    @Test
    void getCANCELLEDStateFromOperationAfterCancel() throws ResourceNotFound, PriceNotInAValidRange, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.create(getSomeOperationRegister());
        operationService.cancelOperationByUser(operation, operation.getUserWhoAccepts());

        assertEquals(OperationState.CANCELLED, operationService.getState(operation));
    }

    @Test
    void getPAIDStateFromOperationAfterMoneyTransferDone() throws ResourceNotFound, PriceNotInAValidRange, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.create(getSomeOperationRegister());
        operationService.moneyTransferDone(operation);

        assertEquals(OperationState.PAID, operationService.getState(operation));
    }

    @Test
    void getCRYPTOSENDEDStateFromOperationAfterCryptoSendDone() throws ResourceNotFound, PriceNotInAValidRange, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.create(getSomeOperationRegister());
        operationService.cryptoSendDone(operation);

        assertEquals(OperationState.CRYPTOSENT, operationService.getState(operation));
    }

    @Test
    void get10WhenAskForPointsOnUsersFromOperationDoneInLess30Minutes() throws ResourceNotFound, PriceNotInAValidRange, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.create(getSomeOperationRegister());
        operationService.assignBonusTimeToUsers(operation);

        assertEquals(10, userService.findById(operation.getIntention().getUser().getId()).getPoints());
        assertEquals(10, userService.findById(operation.getUserWhoAccepts().getId()).getPoints());
    }

    @Test
    void get5WhenAskForPointsOnUsersFromOperationDoneInMore30Minutes() throws ResourceNotFound, PriceNotInAValidRange, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.create(getSomeOperationRegister());
        operation.setDateTime(new DateTimeInMilliseconds().getCurrentTimeMinus30MinutesInMilliseconds());
        operationService.assignBonusTimeToUsers(operation);

        assertEquals(5, userService.findById(operation.getIntention().getUser().getId()).getPoints());
        assertEquals(5, userService.findById(operation.getUserWhoAccepts().getId()).getPoints());
    }

    @Test
    void getAmountInDollars() throws ResourceNotFound, PriceNotInAValidRange, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.create(getSomeOperationRegister());

        assertEquals(200, operationService.amountInDollars(operation, 30000.00, 150));
    }

    @Test
    void modifyAnOperationWithPaidState() throws PriceNotInAValidRange, ResourceNotFound, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.create(getSELLOperationRegister());
        OperationModify operationModify = new OperationModify(operation.getId(), OperationState.PAID, operation.getUserWhoAccepts().getId());
        operationService.modify(operationModify);

        assertEquals(OperationState.PAID, operationService.findById(operation.getId()).getState());
    }

    @Test
    void modifyAnOperationWithCryptoSentState() throws PriceNotInAValidRange, ResourceNotFound, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.create(getSELLOperationRegister());
        OperationModify operationModify = new OperationModify(operation.getId(), OperationState.CRYPTOSENT, operation.getUserWhoAccepts().getId());
        operationService.modify(operationModify);

        assertEquals(OperationState.CRYPTOSENT, operationService.findById(operation.getId()).getState());
    }

    @Test
    void modifyAnOperationWithCancelledState() throws PriceNotInAValidRange, ResourceNotFound, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.create(getSELLOperationRegister());
        OperationModify operationModify = new OperationModify(operation.getId(), OperationState.CANCELLED, operation.getUserWhoAccepts().getId());
        operationService.modify(operationModify);

        assertEquals(OperationState.CANCELLED, operationService.findById(operation.getId()).getState());
    }
    @Test
    void resourceNotFoundWhenModifyAnOperationToActiveState() throws PriceNotInAValidRange, ResourceNotFound, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        Operation operation = operationService.create(getSELLOperationRegister());
        OperationModify operationModify = new OperationModify(operation.getId(), OperationState.ACTIVE, operation.getUserWhoAccepts().getId());

        assertThrows(ResourceNotFound.class, () -> operationService.modify(operationModify));
    }
}