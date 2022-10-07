package ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
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

    //**************** SERVICE - REPOSITORY ****************

    @Test
    void recoversPersistenceANewOperation() {
        User someUserDB = userRepo.save(dataSet.getUserTest());
        User someUser2DB = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency someCryptocurrencyDB = cryptocurrencyRepo.save(dataSet.getCryptocurrency4());
        Intention intentionDB = intentionRepo.save(new Intention(dataSet.getSomeType(), someCryptocurrencyDB,
                dataSet.getSomePrice(), dataSet.getSomeUnit(), someUserDB));
        Operation saved = operationRepo.save(new Operation(intentionDB, someUser2DB));

        assertEquals(operationRepo.findById(saved.getId()).get().getId(), saved.getId());
    }

    @Test
    void getOperationIdFromANewPersistedOperation() throws ResourceNotFound {
        User userWhoPost = userRepo.save(dataSet.getUserTest());
        User userWhoAccept = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("ADA");
        Intention intention = intentionService.create(dataSet.getSomeType(), cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        int operationId = operationService.create(intention, userWhoAccept).getId();

        assertEquals(operationId, operationService.findById(operationId).getId());
    }

    @Test
    void getResourceNotFoundWhenAskForOperationThatNotExists() {
        assertThrows(ResourceNotFound.class, () -> operationService.findById(1));
    }

    @Test
    void getOperationStateFromAnOperationThatChangeState() throws ResourceNotFound {
        User userWhoPost = userRepo.save(dataSet.getUserTest());
        User userWhoAccept = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("ADA");
        Intention intention = intentionService.create(dataSet.getSomeType(), cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        Operation operation = operationService.create(intention, userWhoAccept);
        operation.setState(OperationState.CRYPTOSENDED);
        operationService.update(operation);

        assertEquals(OperationState.CRYPTOSENDED, operationService.findById(operation.getId()).getState());
    }

    @Test
    void get2OperationsWhenAskForAllOfIt() {
        User userWhoPost = userRepo.save(dataSet.getUserTest());
        User userWhoAccept = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("ADA");
        Intention intention = intentionService.create(dataSet.getSomeType(), cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        operationService.create(intention, userWhoAccept);
        Cryptocurrency cryptocurrency2 = cryptocurrencyService.create("LUNA");
        Intention intention2 = intentionService.create(dataSet.getSomeType(), cryptocurrency2, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        operationService.create(intention2, userWhoAccept);

        assertEquals(2, operationService.getAll().size());
    }

    @Test
    void getResourceNotFoundAfterDeleteTheOnlyOneAndAskForIt() {
        User userWhoPost = userRepo.save(dataSet.getUserTest());
        User userWhoAccept = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("ADA");
        Intention intention = intentionService.create(dataSet.getSomeType(), cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        Operation operation = operationService.create(intention, userWhoAccept);
        operationService.delete(operation.getId());

        assertThrows(ResourceNotFound.class, () -> operationService.findById(operation.getId()));
    }

    @Test
    void getEmptyAfterDeleteAllOperationAndAskForAllOfIt() {
        User userWhoPost = userRepo.save(dataSet.getUserTest());
        User userWhoAccept = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("ADA");
        Intention intention = intentionService.create(dataSet.getSomeType(), cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        operationService.create(intention, userWhoAccept);
        operationService.deleteAll();

        assertTrue(operationService.getAll().isEmpty());
    }

    @Test
    void getSellTypeWhenAskForAnOperationTypeMadeWithASellIntentionType() {
        User userWhoPost = userRepo.save(dataSet.getUserTest());
        User userWhoAccept = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("ADA");
        Intention intention = intentionService.create(IntentionType.SELL, cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        Operation operation = operationService.create(intention, userWhoAccept);

        assertEquals(IntentionType.SELL, operationService.getType(operation));
    }

    @Test
    void getAddressWalletInfoWhenAskTransactionInfoInABuyIntentionTypeOperation() {
        User userWhoPost = userRepo.save(dataSet.getUserTest());
        User userWhoAccept = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("ADA");
        Intention intention = intentionService.create(IntentionType.BUY, cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        Operation operation = operationService.create(intention, userWhoAccept);

        assertEquals("Xwf5u5ef", operationService.getTransactionInfoToShow(operation));
    }

    @Test
    void getMercadoPagoCvuInfoWhenAskTransactionInfoInASellIntentionTypeOperation() {
        User userWhoPost = userRepo.save(dataSet.getUserTest());
        User userWhoAccept = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("ADA");
        Intention intention = intentionService.create(IntentionType.SELL, cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        Operation operation = operationService.create(intention, userWhoAccept);

        assertEquals("6352879863528798635287", operationService.getTransactionInfoToShow(operation));
    }

    @Test
    void getUserReputationFromOperation() {
        User userIntention = dataSet.getUserTest();
        userIntention.setPoints(30);
        userIntention.setNumberOperations(3);
        User userWhoPost = userRepo.save(userIntention);
        User userWhoAccept = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("ADA");
        Intention intention = intentionService.create(IntentionType.SELL, cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        Operation operation = operationService.create(intention, userWhoAccept);

        assertEquals(10, operationService.getUserReputation(operation));
    }

    @Test
    void getActionToDoMakeTransferFromOperationWithIntentionTypeSELLAndUserUserWhoAccept() {
        User userIntention = dataSet.getUserTest();
        userIntention.setPoints(30);
        userIntention.setNumberOperations(3);
        User userWhoPost = userRepo.save(userIntention);
        User userWhoAccept = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("ADA");
        Intention intention = intentionService.create(IntentionType.SELL, cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        Operation operation = operationService.create(intention, userWhoAccept);

        assertEquals("Make transfer", operationService.actionToDo(operation, userWhoAccept));
    }

    @Test
    void getActionToDoConfirmReceptionFromOperationWithIntentionTypeSELLAndUserUserWhoPost() {
        User userIntention = dataSet.getUserTest();
        userIntention.setPoints(30);
        userIntention.setNumberOperations(3);
        User userWhoPost = userRepo.save(userIntention);
        User userWhoAccept = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("ADA");
        Intention intention = intentionService.create(IntentionType.SELL, cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        Operation operation = operationService.create(intention, userWhoAccept);

        assertEquals("Confirm reception", operationService.actionToDo(operation, userWhoPost));
    }

    @Test
    void getActionToDoMakeTransferFromOperationWithIntentionTypeBUYAndUserUserWhoAccept() {
        User userIntention = dataSet.getUserTest();
        userIntention.setPoints(30);
        userIntention.setNumberOperations(3);
        User userWhoPost = userRepo.save(userIntention);
        User userWhoAccept = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("ADA");
        Intention intention = intentionService.create(IntentionType.BUY, cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        Operation operation = operationService.create(intention, userWhoAccept);

        assertEquals("Confirm reception", operationService.actionToDo(operation, userWhoAccept));
    }

    @Test
    void getActionToDoConfirmReceptionFromOperationWithIntentionTypeBUYAndUserUserWhoPost() {
        User userIntention = dataSet.getUserTest();
        userIntention.setPoints(30);
        userIntention.setNumberOperations(3);
        User userWhoPost = userRepo.save(userIntention);
        User userWhoAccept = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("ADA");
        Intention intention = intentionService.create(IntentionType.BUY, cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        Operation operation = operationService.create(intention, userWhoAccept);

        assertEquals("Make transfer", operationService.actionToDo(operation, userWhoPost));
    }

    @Test
    void get10WhenAskForPointsFromUserWith30PointsAfterCancelOperation() throws ResourceNotFound {
        User userIntention = dataSet.getUserTest();
        userIntention.setPoints(30);
        User userWhoPost = userRepo.save(userIntention);
        User userWhoAccept = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("ADA");
        Intention intention = intentionService.create(IntentionType.BUY, cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        Operation operation = operationService.create(intention, userWhoAccept);
        operationService.cancelOperationByUser(operation, userWhoPost);

        assertEquals(10, userService.findById(userWhoPost.getId()).getPoints());
    }

    @Test
    void getACTIVEStateFromNewOperation() {
        User userIntention = dataSet.getUserTest();
        User userWhoPost = userRepo.save(userIntention);
        User userWhoAccept = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("ADA");
        Intention intention = intentionService.create(IntentionType.BUY, cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        Operation operation = operationService.create(intention, userWhoAccept);

        assertEquals(OperationState.ACTIVE, operationService.getState(operation));
    }

    @Test
    void getPAIDStateFromOperationAfterMoneyTransferDone() {
        User userIntention = dataSet.getUserTest();
        User userWhoPost = userRepo.save(userIntention);
        User userWhoAccept = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("ADA");
        Intention intention = intentionService.create(IntentionType.BUY, cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        Operation operation = operationService.create(intention, userWhoAccept);
        operationService.cancelOperationByUser(operation, userWhoPost);
        operationService.moneyTransferDone(operation);

        assertEquals(OperationState.PAID, operationService.getState(operation));
    }

    @Test
    void getCRYPTOSENDEDStateFromOperationAfterCryptoSendDone() {
        User userIntention = dataSet.getUserTest();
        User userWhoPost = userRepo.save(userIntention);
        User userWhoAccept = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("ADA");
        Intention intention = intentionService.create(IntentionType.BUY, cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        Operation operation = operationService.create(intention, userWhoAccept);
        operationService.cancelOperationByUser(operation, userWhoPost);
        operationService.cryptoSendDone(operation);

        assertEquals(OperationState.CRYPTOSENDED, operationService.getState(operation));
    }

    @Test
    void get10WhenAskForPointsOnUsersFromOperationDoneInLess30Minutes() throws ResourceNotFound {
        User userIntention = dataSet.getUserTest();
        User userWhoPost = userRepo.save(userIntention);
        User userWhoAccept = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("ADA");
        Intention intention = intentionService.create(IntentionType.BUY, cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        Operation operation = operationService.create(intention, userWhoAccept);
        operationService.assignBonusTimeToUsers(operation);

        assertEquals(10, userService.findById(userWhoPost.getId()).getPoints());
        assertEquals(10, userService.findById(userWhoAccept.getId()).getPoints());
    }

    @Test
    void get5WhenAskForPointsOnUsersFromOperationDoneInMore30Minutes() throws ResourceNotFound {
        User userIntention = dataSet.getUserTest();
        User userWhoPost = userRepo.save(userIntention);
        User userWhoAccept = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("ADA");
        Intention intention = intentionService.create(IntentionType.BUY, cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        Operation operation = operationService.create(intention, userWhoAccept);
        operation.setDateTime(new DateTimeInMilliseconds().getCurrentTimeMinus30MinutesInMilliseconds());
        operationService.assignBonusTimeToUsers(operation);

        assertEquals(5, userService.findById(userWhoPost.getId()).getPoints());
        assertEquals(5, userService.findById(userWhoAccept.getId()).getPoints());
    }

    @Test
    void getAmountInDollars() throws ResourceNotFound {
        User userIntention = dataSet.getUserTest();
        User userWhoPost = userRepo.save(userIntention);
        User userWhoAccept = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("ADA");
        Intention intention = intentionService.create(IntentionType.BUY, cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        Operation operation = operationService.create(intention, userWhoAccept);

        assertEquals(200, operationService.amountInDollars(operation, 30000.00, 150));
    }
}