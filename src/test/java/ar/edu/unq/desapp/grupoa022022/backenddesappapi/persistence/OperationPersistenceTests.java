package ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DateTimeInMilliseconds;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.OperationState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
        return cryptocurrencyService.create("DAI");
    }

    public Cryptocurrency getCryptocurrencyDB2() {
        return cryptocurrencyService.create("BITCOIN");
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
        return new IntentionRegister(dataSet.getSomeType(),
                getSomeCryptocurrencyDBId(), dataSet.getSomePrice(), dataSet.getSomeUnit(), getUserWhoPostDBId());
    }

    public IntentionRegister getIntentionRegisterWithPrice5000Units2() {
        return new IntentionRegister(dataSet.getSomeType(),
                getSomeCryptocurrencyDBId(), 5000.00, 2, getUserWhoPostDBId());
    }

    public IntentionRegister getIntentionRegisterWithUserWhoHas30Point3NumberOperations() {
        return new IntentionRegister(dataSet.getSomeType(), getSomeCryptocurrencyDBId(), dataSet.getSomePrice(),
                dataSet.getSomeUnit(), getUserWith30Point3NumberOperationsDBId());
    }

    public IntentionRegister getIntentionRegisterBUYType() {
        return new IntentionRegister(IntentionType.BUY,
                getSomeCryptocurrencyDBId(), dataSet.getSomePrice(), dataSet.getSomeUnit(), getUserWhoPostDBId());
    }

    public IntentionRegister getIntentionRegisterSELLType() {
        return new IntentionRegister(IntentionType.SELL,
                getSomeCryptocurrencyDB2Id(), dataSet.getSomePrice(), dataSet.getSomeUnit(), getUserWhoPostDBId());
    }

    public Intention getIntentionDB() throws ResourceNotFound {
        return intentionService.create(getSomeIntentionRegister());
    }

    public Intention getSELLTypeIntentionDB() throws ResourceNotFound {
        return intentionService.create(getIntentionRegisterSELLType());
    }

    public Intention getBUYTypeIntentionDB() throws ResourceNotFound {
        return intentionService.create(getIntentionRegisterBUYType());
    }

    public Intention getIntentionWhoUserHas30Points3NumberOperationsDB() throws ResourceNotFound {
        return intentionService.create(getIntentionRegisterWithUserWhoHas30Point3NumberOperations());
    }

    //**************** SERVICE - REPOSITORY ****************

    @Test
    void recoversPersistenceANewOperation() {
        Intention intentionDB = intentionRepo.save(new Intention(dataSet.getSomeType(), getCryptocurrencyDB(),
                dataSet.getSomePrice(), dataSet.getSomeUnit(), getUserWhoPostDB()));
        Operation saved = operationRepo.save(new Operation(intentionDB, getUserWhoAcceptDB2()));

        assertEquals(operationRepo.findById(saved.getId()).get().getId(), saved.getId());
    }

    @Test
    void getOperationIdFromANewPersistedOperation() throws ResourceNotFound {
        int operationId = operationService.create(getIntentionDB(), getUserWhoAcceptDB2()).getId();

        assertEquals(operationId, operationService.findById(operationId).getId());
    }

    @Test
    void getResourceNotFoundWhenAskForOperationThatNotExists() {
        assertThrows(ResourceNotFound.class, () -> operationService.findById(1));
    }

    @Test
    void getOperationStateFromAnOperationThatChangeState() throws ResourceNotFound {
        Operation operation = operationService.create(getIntentionDB(), getUserWhoAcceptDB2());
        operation.setState(OperationState.CRYPTOSENDED);
        operationService.update(operation);

        assertEquals(OperationState.CRYPTOSENDED, operationService.findById(operation.getId()).getState());
    }

    @Test
    void get2OperationsWhenAskForAllOfIt() throws ResourceNotFound {
        operationService.create(getBUYTypeIntentionDB(), getUserWhoAcceptDB2());
        operationService.create(getSELLTypeIntentionDB(), getUserWhoAcceptDB2());

        assertEquals(2, operationService.getAll().size());
    }

    @Test
    void getResourceNotFoundAfterDeleteTheOnlyOneAndAskForIt() throws ResourceNotFound {
        Operation operation = operationService.create(getIntentionDB(), getUserWhoAcceptDB2());
        operationService.delete(operation.getId());

        assertThrows(ResourceNotFound.class, () -> operationService.findById(operation.getId()));
    }

    @Test
    void getEmptyAfterDeleteAllOperationAndAskForAllOfIt() throws ResourceNotFound {
        operationService.create(getIntentionDB(), getUserWhoAcceptDB2());
        operationService.deleteAll();

        assertTrue(operationService.getAll().isEmpty());
    }

    @Test
    void getSellTypeWhenAskForAnOperationTypeMadeWithASellIntentionType() throws ResourceNotFound {
        Operation operation = operationService.create(getSELLTypeIntentionDB(), getUserWhoAcceptDB2());

        assertEquals(IntentionType.SELL, operationService.getType(operation));
    }

    @Test
    void getAddressWalletInfoWhenAskTransactionInfoInABuyIntentionTypeOperation() throws ResourceNotFound {
        Operation operation = operationService.create(getBUYTypeIntentionDB(), getUserWhoAcceptDB2());

        assertEquals("Xwf5u5ef", operationService.getTransactionInfoToShow(operation));
    }

    @Test
    void getMercadoPagoCvuInfoWhenAskTransactionInfoInASellIntentionTypeOperation() throws ResourceNotFound {
        Operation operation = operationService.create(getSELLTypeIntentionDB(), getUserWhoAcceptDB2());

        assertEquals("6352879863528798635287", operationService.getTransactionInfoToShow(operation));
    }

    @Test
    void getUserReputationFromOperation() throws ResourceNotFound {
        Operation operation = operationService.create(getIntentionWhoUserHas30Points3NumberOperationsDB(),
                getUserWhoAcceptDB2());

        assertEquals(10, operationService.getUserReputation(operation));
    }

    @Test
    void getActionToDoMakeTransferFromOperationWithIntentionTypeSELLAndUserUserWhoAccept() throws ResourceNotFound {
        Operation operation = operationService.create(getSELLTypeIntentionDB(), getUserWhoAcceptDB2());

        assertEquals("Make transfer", operationService.actionToDo(operation, operation.getUserWhoAccepts()));
    }

    @Test
    void getActionToDoConfirmReceptionFromOperationWithIntentionTypeSELLAndUserUserWhoPost() throws ResourceNotFound {
        Intention intention = getSELLTypeIntentionDB();
        Operation operation = operationService.create(intention, getUserWhoAcceptDB2());

        assertEquals("Confirm reception", operationService.actionToDo(operation, intention.getUser()));
    }

    @Test
    void getActionToDoConfirmReceptionFromOperationWithIntentionTypeBUYAndUserUserWhoAccept() throws ResourceNotFound {
        Operation operation = operationService.create(getBUYTypeIntentionDB(), getUserWhoAcceptDB2());

        assertEquals("Confirm reception", operationService.actionToDo(operation, operation.getUserWhoAccepts()));
    }

    @Test
    void getActionToDoMakeTransferFromOperationWithIntentionTypeBUYAndUserUserWhoPost() throws ResourceNotFound {
        Intention intention = getBUYTypeIntentionDB();
        Operation operation = operationService.create(intention, getUserWhoAcceptDB2());

        assertEquals("Make transfer", operationService.actionToDo(operation, intention.getUser()));
    }

    @Test
    void get10WhenAskForPointsFromUserWhoPostWith30PointsAfterCancelOperation() throws ResourceNotFound {
        Intention intention = getIntentionWhoUserHas30Points3NumberOperationsDB();
        Operation operation = operationService.create(intention, getUserWhoAcceptDB2());
        operationService.cancelOperationByUser(operation, intention.getUser());

        assertEquals(10, userService.findById(intention.getUser().getId()).getPoints());
    }

    @Test
    void get10WhenAskForPointsFromUserWhoAcceptWith30PointsAfterCancelOperation() throws ResourceNotFound {
        Operation operation = operationService.create(getIntentionDB(), getUserWith30Point3NumberOperationsDB());
        operationService.cancelOperationByUser(operation, operation.getUserWhoAccepts());

        assertEquals(10, userService.findById(operation.getUserWhoAccepts().getId()).getPoints());
    }

    @Test
    void getACTIVEStateFromNewOperation() throws ResourceNotFound {
        Operation operation = operationService.create(getIntentionDB(), getUserWhoAcceptDB2());

        assertEquals(OperationState.ACTIVE, operationService.getState(operation));
    }

    @Test
    void getCANCELLEDStateFromOperationAfterCancel() throws ResourceNotFound {
        Operation operation = operationService.create(getIntentionDB(), getUserWhoAcceptDB2());
        operationService.cancelOperationByUser(operation, operation.getUserWhoAccepts());

        assertEquals(OperationState.CANCELLED, operationService.getState(operation));
    }

    @Test
    void getPAIDStateFromOperationAfterMoneyTransferDone() throws ResourceNotFound {
        Operation operation = operationService.create(getIntentionDB(), getUserWhoAcceptDB2());
        operationService.moneyTransferDone(operation);

        assertEquals(OperationState.PAID, operationService.getState(operation));
    }

    @Test
    void getCRYPTOSENDEDStateFromOperationAfterCryptoSendDone() throws ResourceNotFound {
        Operation operation = operationService.create(getIntentionDB(), getUserWhoAcceptDB2());
        operationService.cryptoSendDone(operation);

        assertEquals(OperationState.CRYPTOSENDED, operationService.getState(operation));
    }

    @Test
    void get10WhenAskForPointsOnUsersFromOperationDoneInLess30Minutes() throws ResourceNotFound {
        Intention intention = getIntentionDB();
        Operation operation = operationService.create(intention, getUserWhoAcceptDB2());
        operationService.assignBonusTimeToUsers(operation);

        assertEquals(10, userService.findById(intention.getUser().getId()).getPoints());
        assertEquals(10, userService.findById(operation.getUserWhoAccepts().getId()).getPoints());
    }

    @Test
    void get5WhenAskForPointsOnUsersFromOperationDoneInMore30Minutes() throws ResourceNotFound {
        Intention intention = getIntentionDB();
        Operation operation = operationService.create(intention, getUserWhoAcceptDB2());
        operation.setDateTime(new DateTimeInMilliseconds().getCurrentTimeMinus30MinutesInMilliseconds());
        operationService.assignBonusTimeToUsers(operation);

        assertEquals(5, userService.findById(intention.getUser().getId()).getPoints());
        assertEquals(5, userService.findById(operation.getUserWhoAccepts().getId()).getPoints());
    }

    @Test
    void getAmountInDollars() throws ResourceNotFound {
        Operation operation = operationService.create(getIntentionDB(), getUserWhoAcceptDB2());

        assertEquals(200, operationService.amountInDollars(operation, 30000.00, 150));
    }
}