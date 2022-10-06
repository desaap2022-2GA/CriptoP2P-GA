package ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.*;
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
    void recoversPersistanceANewOperation() {
        User someuserDB = userRepo.save(dataSet.getUserTest());
        User someuser2DB = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency somecryptocurrencyDB = cryptocurrencyRepo.save(dataSet.getCryptocurrency4());
        Intention intentionDB = intentionRepo.save(new Intention(dataSet.getSomeType(), somecryptocurrencyDB,
                dataSet.getSomePrice(), dataSet.getSomeUnit(), someuserDB));
        Operation saved = operationRepo.save(new Operation(intentionDB, someuser2DB));

        int idSaved = saved.getId();
        assertEquals(operationRepo.findById(idSaved).get().getId(), idSaved);
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
        assertThrows(ResourceNotFound.class, () -> {
            operationService.findById(1);
        });
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
        Operation operation = operationService.create(intention, userWhoAccept);
        Cryptocurrency cryptocurrency2 = cryptocurrencyService.create("LUNA");
        Intention intention2 = intentionService.create(dataSet.getSomeType(), cryptocurrency2, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        Operation operation2 = operationService.create(intention2, userWhoAccept);

        assertEquals(2, operationService.getAll().size());
    }

    @Test
    void getResourseNotFoundAfterDeleteTheOnlyOneAndAskForIt() {
        User userWhoPost = userRepo.save(dataSet.getUserTest());
        User userWhoAccept = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("ADA");
        Intention intention = intentionService.create(dataSet.getSomeType(), cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        Operation operation = operationService.create(intention, userWhoAccept);
        operationService.delete(operation.getId());

        assertThrows(ResourceNotFound.class, () -> {
            operationService.findById(operation.getId());
        });
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
    void getAddressWalletInfoWhenAskTransactionInfoInABuyIntentionTypeOperation(){
        User userWhoPost = userRepo.save(dataSet.getUserTest());
        User userWhoAccept = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("ADA");
        Intention intention = intentionService.create(IntentionType.BUY, cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        Operation operation = operationService.create(intention, userWhoAccept);

        assertEquals("Xwf5u5ef", operationService.getTransactionInfoToShow(operation));
    }

    @Test
    void getMercadoPagoCvuInfoWhenAskTransactionInfoInASellIntentionTypeOperation(){
        User userWhoPost = userRepo.save(dataSet.getUserTest());
        User userWhoAccept = userRepo.save(dataSet.getUserTest2());
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("ADA");
        Intention intention = intentionService.create(IntentionType.SELL, cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), userWhoPost);
        Operation operation = operationService.create(intention, userWhoAccept);

        assertEquals("6352879863528798635287", operationService.getTransactionInfoToShow(operation));
    }
    /*
    int getUserReputation(Operation operation);
    String actionToDo(Operation operation, User user);
    void cancelOperationByUser(Operation operation, User user);
    void moneyTransferDone(Operation operation);
    void cryptoSendDone(Operation operation);
    void assignBonusTimeToUsers(Operation operation);
    double amountInDollars(Operation operation, double amount, double dollarQuote);
    */
}