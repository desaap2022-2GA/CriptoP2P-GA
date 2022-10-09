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

    public Cryptocurrency getCryptocurrencyDB() {
        return cryptocurrencyService.create("DAI");
    }

    public Cryptocurrency getCryptocurrencyDB2() {
        return cryptocurrencyService.create("BITCOIN");
    }

    public User getUserDB() {
        return userService.saveToDataBase(dataSet.getUserRegister());
    }

    public User getUserWith50Point5NumberOperations() {
        User userDB = getUserDB();
        userDB.setPoints(50);
        userDB.setNumberOperations(5);
        return userRepo.save(userDB);
    }

    //**************** SERVICE - REPOSITORY ****************

    @Test
    void recoversPersistenceANewIntention() {
        User someUserDB = userRepo.save(dataSet.getUserTest());
        Cryptocurrency someCryptocurrencyDB = cryptocurrencyRepo.save(dataSet.getCryptocurrency2());
        Intention intentionDB = intentionRepo.save(new Intention(dataSet.getSomeType(), someCryptocurrencyDB,
                dataSet.getSomePrice(), dataSet.getSomeUnit(), someUserDB));
        int idSaved = intentionDB.getId();

        assertEquals(intentionRepo.findById(idSaved).get().getId(), idSaved);
    }

    @Test
    void getAIntentionCreated() throws ResourceNotFound {
        Intention intention = intentionService.create(dataSet.getSomeType(), getCryptocurrencyDB(),
                dataSet.getSomePrice(), dataSet.getSomeUnit(), getUserDB());

        assertEquals(intention.getId(), intentionService.findById(intention.getId()).getId());
    }

    @Test
    void getPriceOfAIntentionUpdated() throws ResourceNotFound {
        Intention intention = intentionService.create(dataSet.getSomeType(), getCryptocurrencyDB(), 1500.00,
                dataSet.getSomeUnit(), getUserDB());
        intention.setPrice(2000.00);
        intentionService.update(intention);

        assertEquals(2000.00, intentionService.findById(intention.getId()).getPrice());
    }

    @Test
    void getResourceNotFoundWhenAskForIntentionIdThatDoesNotExist() {
        assertThrows(ResourceNotFound.class, () -> intentionService.findById(1));
    }

    @Test
    void getNoIntentionsWhenAskForIntentionsAfterDeleteAll() {
        intentionService.create(dataSet.getSomeType(), getCryptocurrencyDB(), dataSet.getSomePrice(),
                dataSet.getSomeUnit(), getUserDB());
        intentionService.deleteAll();

        assertTrue(intentionService.getAll().isEmpty());
    }

    @Test
    void get2IntentionsWhenAskForAllIntentions() {
        User user = userRepo.save(dataSet.getUserTest());
        intentionService.create(dataSet.getSomeType(), getCryptocurrencyDB(), dataSet.getSomePrice(), dataSet.getSomeUnit(), user);
        intentionService.create(dataSet.getSomeType(), getCryptocurrencyDB2(), dataSet.getSomePrice(), dataSet.getSomeUnit(), user);

        assertEquals(2, intentionService.getAll().size());
    }

    @Test
    void getResourceNotFoundWhenAskForIntentionDeleted() {
        int intentionId = intentionService.create(dataSet.getSomeType(), getCryptocurrencyDB(), dataSet.getSomePrice(),
                dataSet.getSomeUnit(), getUserDB()).getId();
        intentionService.delete(intentionId);

        assertThrows(ResourceNotFound.class, () -> intentionService.findById(intentionId));
    }

    @Test
    void getAmountPriceInDollars() {
        Intention intention = intentionService.create(dataSet.getSomeType(), getCryptocurrencyDB(), 5000.00,
                2, getUserDB());

        assertEquals(10000.00 / 149.00, intentionService.amountPriceInDollars(149.00, intention));
    }

    @Test
    void getAmountPriceInPesos() {
        Intention intention = intentionService.create(dataSet.getSomeType(), getCryptocurrencyDB(), 5000.00,
                2, getUserDB());

        assertEquals(10000, intentionService.amountPriceInPesos(intention));
    }

    @Test
    void getAddressCryptoWhenAskForInfoToShowOnBuyTypeIntention() {
        Intention intention = intentionService.create(IntentionType.BUY, getCryptocurrencyDB(), dataSet.getSomePrice(),
                dataSet.getSomeUnit(), getUserDB());

        assertEquals("Xwf5u5ef", intentionService.transactionInfoToShow(intention));
    }

    @Test
    void getReputation10FromUserWhoPostTheIntentionWith50Points5NumbersOperations() throws ResourceNotFound {
        int intentionId = intentionService.create(dataSet.getSomeType(), getCryptocurrencyDB(), dataSet.getSomePrice(),
                dataSet.getSomeUnit(), getUserWith50Point5NumberOperations()).getId();

        assertEquals(10, intentionService.getUserReputation(intentionService.findById(intentionId)));
    }

    @Test
    void getOperationNumber5FromUserWhoPostTheIntentionWith5NumbersOperations() throws ResourceNotFound {
        int intentionId = intentionService.create(dataSet.getSomeType(), getCryptocurrencyDB(), dataSet.getSomePrice(),
                dataSet.getSomeUnit(), getUserWith50Point5NumberOperations()).getId();

        assertEquals(5, intentionService.getOperationNumberUser(intentionService.findById(intentionId)));
    }
}