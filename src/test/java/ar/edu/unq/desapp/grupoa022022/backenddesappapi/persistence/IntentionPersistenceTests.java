package ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionRegisterDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceNotInAValidRangeException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.UserService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
class IntentionPersistenceTests {

    DataSet dataSet = new DataSet();

    @Autowired
    IIntentionRepo intentionRepo;
    @Autowired
    IUserRepo userRepo;
    @Autowired
    ICryptocurrencyRepo cryptocurrencyRepo;

    @Autowired
    UserService userService;
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

    public User getUserDB() {
        return userService.saveToDataBase(dataSet.getUserRegister());
    }

    public User getUserDB2() {
        return userService.saveToDataBase(dataSet.getUserRegister2());
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
    public IntentionRegisterDTO getSomeIntentionRegister() { return new IntentionRegisterDTO(dataSet.getSomeTypeBUY(),
            getSomeCryptocurrencyDBId(), dataSet.getSomePriceInRangeDAI(), dataSet.getSomeUnit(), getSomeUserDBId());}
    public IntentionRegisterDTO getSomeIntentionRegister2() { return new IntentionRegisterDTO(dataSet.getSomeTypeBUY(),
            getSomeCryptocurrencyDB2Id(), dataSet.getSomePriceInRangeBITCOIN(), dataSet.getSomeUnit(), getSomeUserDBId());}
    public IntentionRegisterDTO getIntentionRegisterWithPrice335Units2() { return new IntentionRegisterDTO(dataSet.getSomeTypeBUY(),
            getSomeCryptocurrencyDBId(), 335.00, 2, getSomeUserDBId());}
    public IntentionRegisterDTO getIntentionRegisterWithUserWhoHas50Point5NumberOperations() { return new IntentionRegisterDTO(dataSet.getSomeTypeBUY(),
            getSomeCryptocurrencyDBId(), dataSet.getSomePriceInRangeDAI(), dataSet.getSomeUnit(), getUserWith50Point5NumberOperationsId());}
    public IntentionRegisterDTO getIntentionRegisterBUYType() { return new IntentionRegisterDTO(IntentionType.BUY,
            getSomeCryptocurrencyDBId(), dataSet.getSomePriceInRangeDAI(), dataSet.getSomeUnit(), getSomeUserDBId());}
    public IntentionRegisterDTO getIntentionRegisterSELLType() { return new IntentionRegisterDTO(IntentionType.SELL,
            getSomeCryptocurrencyDBId(), dataSet.getSomePriceInRangeDAI(), dataSet.getSomeUnit(), getSomeUserDBId());}

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
    void recoversPersistenceANewIntention() {
        User someUserDB = userRepo.save(dataSet.getUserTest());
        Cryptocurrency someCryptocurrencyDB = cryptocurrencyRepo.save(dataSet.getCryptocurrency2());
        Intention intentionDB = intentionRepo.save(new Intention(dataSet.getSomeTypeBUY(), someCryptocurrencyDB,
                dataSet.getSomePriceInRangeDAI(), dataSet.getSomeUnit(), someUserDB));
        int idSaved = intentionDB.getId();

        assertEquals(intentionRepo.findById(idSaved).get().getId(), idSaved);
    }

    @Test
    void getAIntentionCreated() throws ResourceNotFoundException, PriceNotInAValidRangeException {
        Intention intention = intentionService.create(getSomeIntentionRegister());

        assertEquals(intention.getId(), intentionService.findById(intention.getId()).getId());
    }

    @Test
    void getPriceOfAIntentionUpdated() throws ResourceNotFoundException, PriceNotInAValidRangeException {
        Intention intention = intentionService.create(getSomeIntentionRegister());
        intention.setPrice(2000.00);
        intentionService.update(intention);

        assertEquals(2000.00, intentionService.findById(intention.getId()).getPrice());
    }

    @Test
    void getResourceNotFoundWhenAskForIntentionIdThatDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> intentionService.findById(1));
    }

    @Test
    void getNoIntentionsWhenAskForIntentionsAfterDeleteAll() throws ResourceNotFoundException, PriceNotInAValidRangeException {
        intentionService.create(getSomeIntentionRegister());
        intentionService.deleteAll();

        assertTrue(intentionService.getAll().isEmpty());
    }

    @Test
    void get2IntentionsWhenAskForAllIntentions() throws ResourceNotFoundException, PriceNotInAValidRangeException {
        userRepo.save(dataSet.getUserTest());
        intentionService.create(getSomeIntentionRegister());
        intentionService.create(getSomeIntentionRegister2());
        assertEquals(2, intentionService.getAll().size());
    }

    @Test
    void getResourceNotFoundWhenAskForIntentionDeleted() throws ResourceNotFoundException, PriceNotInAValidRangeException {
        int intentionId = intentionService.create(getSomeIntentionRegister()).getId();
        intentionService.delete(intentionId);

        assertThrows(ResourceNotFoundException.class, () -> intentionService.findById(intentionId));
    }

    @Test
    void getAmountPriceInDollarsFromAnIntentionWithPrice335Units2AndActualQuotePrice320_38() throws ResourceNotFoundException, PriceNotInAValidRangeException {
        Intention intention = intentionService.create(getIntentionRegisterWithPrice335Units2());

        assertEquals(640.76 / 149.00, intention.actualAmountPriceInDollars(149.00));
    }

    @Test
    void getAmountPriceInPesosFromAnIntentionWithPrice335Units2() throws ResourceNotFoundException, PriceNotInAValidRangeException {
        Intention intention = intentionService.create(getIntentionRegisterWithPrice335Units2());

        assertEquals(670, intention.amountPriceInPesos());
    }

    @Test
    void getReputation10FromUserWhoPostTheIntentionWith50Points5NumbersOperations() throws ResourceNotFoundException, PriceNotInAValidRangeException {
        Intention intention = intentionService.create(getIntentionRegisterWithUserWhoHas50Point5NumberOperations());

        assertEquals(10, intention.getUser().getReputation());
    }

    @Test
    void getOperationNumber5FromUserWhoPostTheIntentionWith5NumbersOperations() throws ResourceNotFoundException, PriceNotInAValidRangeException {
        Intention intention = intentionService.create(getIntentionRegisterWithUserWhoHas50Point5NumberOperations());

        assertEquals(5, intention.getUser().getNumberOperations());
    }
}