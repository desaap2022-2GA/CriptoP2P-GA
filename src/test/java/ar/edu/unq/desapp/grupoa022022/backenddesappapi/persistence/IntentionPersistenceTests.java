package ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionRegister;
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
    public int getSomeUserDBId(){return getUserDB().getId();}

    public int getUserWith50Point5NumberOperationsId(){return getUserWith50Point5NumberOperations().getId();}

    public int getSomeCryptocurrencyDBId(){return getCryptocurrencyDB().getId();}

    public int getSomeCryptocurrencyDB2Id(){return getCryptocurrencyDB2().getId();}
    public IntentionRegister getSomeIntentionRegister() { return new IntentionRegister(dataSet.getSomeType(),
            getSomeCryptocurrencyDBId(), dataSet.getSomePrice(), dataSet.getSomeUnit(), getSomeUserDBId());}
    public IntentionRegister getSomeIntentionRegister2() { return new IntentionRegister(dataSet.getSomeType(),
            getSomeCryptocurrencyDB2Id(), dataSet.getSomePrice(), dataSet.getSomeUnit(), getSomeUserDBId());}
    public IntentionRegister getIntentionRegisterWithPrice5000Units2() { return new IntentionRegister(dataSet.getSomeType(),
            getSomeCryptocurrencyDBId(), 5000.00, 2, getSomeUserDBId());}
    public IntentionRegister getIntentionRegisterWithUserWhoHas50Point5NumberOperations() { return new IntentionRegister(dataSet.getSomeType(),
            getSomeCryptocurrencyDBId(), dataSet.getSomePrice(), dataSet.getSomeUnit(), getUserWith50Point5NumberOperationsId());}
    public IntentionRegister getIntentionRegisterBUYType() { return new IntentionRegister(IntentionType.BUY,
            getSomeCryptocurrencyDBId(), dataSet.getSomePrice(), dataSet.getSomeUnit(), getSomeUserDBId());}
    public IntentionRegister getIntentionRegisterSELLType() { return new IntentionRegister(IntentionType.SELL,
            getSomeCryptocurrencyDBId(), dataSet.getSomePrice(), dataSet.getSomeUnit(), getSomeUserDBId());}

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
        Intention intention = intentionService.create(getSomeIntentionRegister());

        assertEquals(intention.getId(), intentionService.findById(intention.getId()).getId());
    }

    @Test
    void getPriceOfAIntentionUpdated() throws ResourceNotFound {
        Intention intention = intentionService.create(getSomeIntentionRegister());
        intention.setPrice(2000.00);
        intentionService.update(intention);

        assertEquals(2000.00, intentionService.findById(intention.getId()).getPrice());
    }

    @Test
    void getResourceNotFoundWhenAskForIntentionIdThatDoesNotExist() {
        assertThrows(ResourceNotFound.class, () -> intentionService.findById(1));
    }

    @Test
    void getNoIntentionsWhenAskForIntentionsAfterDeleteAll() throws ResourceNotFound {
        intentionService.create(getSomeIntentionRegister());
        intentionService.deleteAll();

        assertTrue(intentionService.getAll().isEmpty());
    }

    @Test
    void get2IntentionsWhenAskForAllIntentions() throws ResourceNotFound {
        User user = userRepo.save(dataSet.getUserTest());
        intentionService.create(getSomeIntentionRegister());
        intentionService.create(getSomeIntentionRegister2());
        assertEquals(2, intentionService.getAll().size());
    }

    @Test
    void getResourceNotFoundWhenAskForIntentionDeleted() throws ResourceNotFound {
        int intentionId = intentionService.create(getSomeIntentionRegister()).getId();
        intentionService.delete(intentionId);

        assertThrows(ResourceNotFound.class, () -> intentionService.findById(intentionId));
    }

    @Test
    void getAmountPriceInDollarsFromAnIntentionWithPrice5000Units2() throws ResourceNotFound {
        Intention intention = intentionService.create(getIntentionRegisterWithPrice5000Units2());

        assertEquals(10000.00 / 149.00, intentionService.amountPriceInDollars(149.00, intention));
    }

    @Test
    void getAmountPriceInPesosFromAnIntentionWithPrice5000Units2() throws ResourceNotFound {
        Intention intention = intentionService.create(getIntentionRegisterWithPrice5000Units2());

        assertEquals(10000, intentionService.amountPriceInPesos(intention));
    }

    @Test
    void getAddressCryptoWhenAskForInfoToShowOnBuyTypeIntention() throws ResourceNotFound {
        Intention intention = intentionService.create(getIntentionRegisterBUYType());

        assertEquals("Xwf5u5ef", intentionService.transactionInfoToShow(intention));
    }

    @Test
    void getmercadoPagoCVUWhenAskForInfoToShowOnSELLTypeIntention() throws ResourceNotFound {
        Intention intention = intentionService.create(getIntentionRegisterSELLType());

        assertEquals("6352879863528798635287", intentionService.transactionInfoToShow(intention));
    }

    @Test
    void getReputation10FromUserWhoPostTheIntentionWith50Points5NumbersOperations() throws ResourceNotFound {
        int intentionId = intentionService.create(getIntentionRegisterWithUserWhoHas50Point5NumberOperations()).getId();

        assertEquals(10, intentionService.getUserReputation(intentionService.findById(intentionId)));
    }

    @Test
    void getOperationNumber5FromUserWhoPostTheIntentionWith5NumbersOperations() throws ResourceNotFound {
        int intentionId = intentionService.create(getIntentionRegisterWithUserWhoHas50Point5NumberOperations()).getId();

        assertEquals(5, intentionService.getOperationNumberUser(intentionService.findById(intentionId)));
    }
}