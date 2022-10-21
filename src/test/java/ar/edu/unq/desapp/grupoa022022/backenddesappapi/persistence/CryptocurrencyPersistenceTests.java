package ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.ICryptocurrencyService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IIntentionService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IOperationService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IQuoteService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.UserService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DateTimeInMilliseconds;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
class CryptocurrencyPersistenceTests {

    DataSet dataSet = new DataSet();

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

    public CryptocurrencyRegister cryptocurrencyRegisterDAI = new CryptocurrencyRegister("DAI", 320.38);
    public CryptocurrencyRegister cryptocurrencyRegisterBITCOIN = new CryptocurrencyRegister("BITCOIN", 5840798.98);
    public CryptocurrencyRegister cryptocurrencyRegisterLUNA = new CryptocurrencyRegister("LUNA", 399.08);
    public CryptocurrencyRegister cryptocurrencyRegisterUSDT = new CryptocurrencyRegister("USDT", 152.50);
    public Cryptocurrency getCryptocurrencyDB(){ return cryptocurrencyService.create(cryptocurrencyRegisterDAI);}
    public Cryptocurrency getCryptocurrencyDB2(){ return cryptocurrencyService.create(cryptocurrencyRegisterBITCOIN);}
    public Cryptocurrency getCryptocurrencyDB3(){ return cryptocurrencyService.create(cryptocurrencyRegisterLUNA);}
    public Cryptocurrency getCryptocurrencyDB4(){ return cryptocurrencyService.create(cryptocurrencyRegisterUSDT);}

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
    void recoversPersistenceANewCryptocurrency() throws ResourceNotFound {
        Cryptocurrency saved = cryptocurrencyRepo.save(new Cryptocurrency("DAI"));
        int idSaved = saved.getId();
        Cryptocurrency found = cryptocurrencyRepo.findById(idSaved).orElseThrow(() -> new ResourceNotFound
                ("nonexistent cryptocurrency"));

        assertEquals(found.getId(), idSaved);
    }

    @Test
    void createACryptocurrencyCheckId() throws ResourceNotFound {
        int cryptocurrencyId = cryptocurrencyService.create(cryptocurrencyRegisterDAI).getId();

        assertEquals(cryptocurrencyId, cryptocurrencyService.findById(cryptocurrencyId).getId());
    }

    @Test
    void throwsResourceNotFoundWhenTryToGetADeleteACryptocurrency() {
        int cryptocurrencyId = getCryptocurrencyDB().getId();
        cryptocurrencyService.delete(cryptocurrencyId);

        assertThrows(ResourceNotFound.class, () -> cryptocurrencyService.findById(cryptocurrencyId));
    }

    @Test
    void updateACryptocurrencyCheckFieldModified() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = getCryptocurrencyDB();
        cryptocurrency.setName("LUNA");
        cryptocurrencyService.update(cryptocurrency);

        assertEquals("LUNA", cryptocurrencyService.findById(cryptocurrency.getId()).getName());
    }

    @Test
    void getAnEmptySetWhenAllCryptocurrenciesWasDeleted() {
        getCryptocurrencyDB();
        getCryptocurrencyDB2();
        getCryptocurrencyDB3();
        cryptocurrencyService.deleteAll();

        assertTrue(cryptocurrencyService.getAll().isEmpty());
    }

    @Test
    void getTheLatestQuoteFromCryptocurrency() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = getCryptocurrencyDB();
        quoteService.create(cryptocurrency, dataSet.getSomePriceInRangeDAI());
        int quote2Id = quoteService.create(cryptocurrency, dataSet.getSomePriceInRangeDAI() + 2000).getId();

        assertEquals(quote2Id, cryptocurrencyService.getLatestQuote(cryptocurrencyService.findById(cryptocurrency.getId())).getId());
    }

    @Test
    void obtain5WhenGetTheLast24HoursQuotesFromCryptocurrency() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = getCryptocurrencyDB();
        quoteService.create(cryptocurrency, dataSet.getSomePriceInRangeDAI());
        quoteService.create(cryptocurrency, dataSet.getSomePriceInRangeDAI());
        quoteService.create(cryptocurrency, dataSet.getSomePriceInRangeDAI());
        quoteService.create(cryptocurrency, dataSet.getSomePriceInRangeDAI());
        Quote oldQuote = quoteService.create(cryptocurrency, dataSet.getSomePriceInRangeDAI());
        oldQuote.setDateTime(new DateTimeInMilliseconds().getCurrentTimeMinusOneDayInMilliseconds());
        quoteService.update(oldQuote);

        assertEquals(5, cryptocurrencyService.last24hoursQuotes(cryptocurrencyService.findById(cryptocurrency.getId())).size());
    }

    @Test
    void obtain4WhenGetCryptocurrenciesWhenAskForAllCryptocurrencies() {
        getCryptocurrencyDB();
        getCryptocurrencyDB2();
        getCryptocurrencyDB3();
        getCryptocurrencyDB4();

        assertEquals(4, cryptocurrencyService.getAll().size());
    }

    @Test
    void get4QuotesFromACryptocurrency() {
        Cryptocurrency cryptocurrency = getCryptocurrencyDB();
        quoteService.create(cryptocurrency, dataSet.getSomePriceInRangeDAI());
        quoteService.create(cryptocurrency, dataSet.getSomePriceInRangeDAI());
        quoteService.create(cryptocurrency, dataSet.getSomePriceInRangeDAI());

        assertEquals(4, cryptocurrencyService.getQuotes(cryptocurrency).size());
    }

    @Test
    void getEmptyIntentionsFromACryptocurrencyWithoutIntentions() {
        Cryptocurrency cryptocurrency = getCryptocurrencyDB();

        assertTrue(cryptocurrencyService.getReferencedIntentions(cryptocurrency).isEmpty());
    }
}
