package ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.ICryptocurrencyService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.IIntentionService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.IUserService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class IntentionPersistenceTests {

    DataSet dataSet = new DataSet();

    @Autowired
    IIntentionRepo intentionRepo;
    @Autowired
    IUserRepo userRepo;
    @Autowired
    ICryptocurrencyRepo cryptocurrencyRepo;

    @Autowired
    IIntentionService intentionService;

    @Autowired
    ICryptocurrencyService cryptocurrencyService;

    @Autowired
    IUserService userService;

    //**************** SERVICE - REPOSITORY ****************

    @Test
    void recoversPersistanceANewIntention() {
        User someuserDB = userRepo.save(dataSet.getUserTest());
        Cryptocurrency somecryptocurrencyDB = cryptocurrencyRepo.save(dataSet.getCryptocurrency2());
        Intention intentionDB = intentionRepo.save(new Intention(dataSet.getSomeType(), somecryptocurrencyDB,
                dataSet.getSomePrice(), dataSet.getSomeUnit(), someuserDB));
        int idSaved = intentionDB.getId();

        assertEquals(intentionRepo.findById(idSaved).get().getId(), idSaved);
    }

    @Test
    void getAIntentionCreated() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("SOL");
        User user = userRepo.save(dataSet.getUserTest());
        Intention intention = intentionService.create(dataSet.getSomeType(), cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), user);

        assertEquals(intention.getId(), intentionService.findById(intention.getId()).getId());
    }

    @Test
    void getPriceOfAIntentionUpdated() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("SOL");
        User user = userRepo.save(dataSet.getUserTest());
        Intention intention = intentionService.create(dataSet.getSomeType(), cryptocurrency, 1500.00, dataSet.getSomeUnit(), user);
        intention.setPrice(2000.00);
        intentionService.update(intention);

        assertEquals(2000.00, intentionService.findById(intention.getId()).getPrice());
    }

    @Test
    void getResouceNotFoundWhenAskForIntentionIdThatDoesNotExist() {
        assertThrows(ResourceNotFound.class, () -> {
            intentionService.findById(1);
        });
    }

    @Test
    void getNoIntentionsWhenAskForIntentionsAfterDeleteAll() {
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("SOL");
        User user = userRepo.save(dataSet.getUserTest());
        intentionService.create(dataSet.getSomeType(), cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), user);
        intentionService.deleteAll();

        assertTrue(intentionService.getAll().isEmpty());
    }

    @Test
    void get2IntentionsWhenAskForAllIntentions() {
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("SOL");
        User user = userRepo.save(dataSet.getUserTest());
        intentionService.create(dataSet.getSomeType(), cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), user);
        Cryptocurrency cryptocurrency2 = cryptocurrencyService.create("ADA");
        intentionService.create(dataSet.getSomeType(), cryptocurrency2, dataSet.getSomePrice(), dataSet.getSomeUnit(), user);

        assertEquals(2, intentionService.getAll().size());
    }

    @Test
    void getResourceNotFoundWhenAskForIntentionDeleted() {
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("SOL");
        User user = userRepo.save(dataSet.getUserTest());
        int intentionId = intentionService.create(dataSet.getSomeType(), cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), user).getId();
        intentionService.delete(intentionId);

        assertThrows(ResourceNotFound.class, () -> {
            intentionService.findById(intentionId);
        });
    }

    @Test
    void getAmountPriceInDollars() {
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("SOL");
        User user = userRepo.save(dataSet.getUserTest());
        Intention intention = intentionService.create(dataSet.getSomeType(), cryptocurrency, 5000.00, 2, user);

        assertEquals(10000.00 / 149.00, intentionService.amountPriceInDollars(149.00, intention));
    }

    @Test
    void getAmountPriceInPesos() {
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("SOL");
        User user = userRepo.save(dataSet.getUserTest());
        Intention intention = intentionService.create(dataSet.getSomeType(), cryptocurrency, 5000.00, 2, user);

        assertEquals(10000, intentionService.amountPriceInPesos(intention));
    }

    @Test
    void getAddressCryptoWhenAskForInfoToShowOnBuyTypeIntention() {
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("SOL");
        User user = userRepo.save(dataSet.getUserTest());
        Intention intention = intentionService.create(IntentionType.BUY, cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), user);

        assertEquals("Xwf5u5ef", intentionService.transactionInfoToShow(intention));
    }

    @Test
    void getReputationFromUserWhoPostTheIntention() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("SOL");
        User userToPersist = dataSet.getUserTest();
        userToPersist.setPoints(50);
        userToPersist.setNumberOperations(5);
        User user = userRepo.save(userToPersist);
        int intentionId = intentionService.create(dataSet.getSomeType(), cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), user).getId();

        assertEquals(10, intentionService.getUserReputation(intentionService.findById(intentionId)));
    }

    @Test
    void getOperationNumberOfUserWhoPostTheIntention() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("SOL");
        User userToPersist = dataSet.getUserTest();
        userToPersist.setNumberOperations(5);
        User user = userRepo.save(userToPersist);
        int intentionId = intentionService.create(dataSet.getSomeType(), cryptocurrency, dataSet.getSomePrice(), dataSet.getSomeUnit(), user).getId();

        assertEquals(5, intentionService.getOperationNumberUser(intentionService.findById(intentionId)));
    }
}