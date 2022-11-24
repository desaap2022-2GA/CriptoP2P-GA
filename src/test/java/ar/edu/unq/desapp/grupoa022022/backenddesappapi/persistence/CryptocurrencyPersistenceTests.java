package ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyRegisterDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.ICryptocurrencyService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IIntentionService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IOperationService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IQuoteService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.UserService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DateTimeInMilliseconds;
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

    public CryptocurrencyRegisterDTO cryptocurrencyRegisterDTODAI = new CryptocurrencyRegisterDTO("DAI", 320.38);
    public CryptocurrencyRegisterDTO cryptocurrencyRegisterDTOBITCOIN = new CryptocurrencyRegisterDTO("BITCOIN", 5840798.98);
    public CryptocurrencyRegisterDTO cryptocurrencyRegisterDTOLUNA = new CryptocurrencyRegisterDTO("LUNA", 399.08);
    public CryptocurrencyRegisterDTO cryptocurrencyRegisterDTOUSDT = new CryptocurrencyRegisterDTO("USDT", 152.50);
    public Cryptocurrency getCryptocurrencyDB(){ return cryptocurrencyService.create(cryptocurrencyRegisterDTODAI);}
    public Cryptocurrency getCryptocurrencyDB2(){ return cryptocurrencyService.create(cryptocurrencyRegisterDTOBITCOIN);}
    public Cryptocurrency getCryptocurrencyDB3(){ return cryptocurrencyService.create(cryptocurrencyRegisterDTOLUNA);}
    public Cryptocurrency getCryptocurrencyDB4(){ return cryptocurrencyService.create(cryptocurrencyRegisterDTOUSDT);}

    @BeforeEach

    public void init() {
//      LOG.info("startup");
        operationService.deleteAll();
        intentionService.deleteAll();
        cryptocurrencyService.deleteAll();
        userService.deleteAllUsers();
    }

    //**************** SERVICE - REPOSITORY ****************

    @Test
    void recoversPersistenceANewCryptocurrency() throws ResourceNotFoundException {
        Cryptocurrency saved = cryptocurrencyRepo.save(new Cryptocurrency("DAI"));
        int idSaved = saved.getId();
        Cryptocurrency found = cryptocurrencyRepo.findById(idSaved).orElseThrow(() -> new ResourceNotFoundException
                ("nonexistent cryptocurrency"));

        assertEquals(found.getId(), idSaved);
    }

    @Test
    void createACryptocurrencyCheckId() throws ResourceNotFoundException {
        int cryptocurrencyId = cryptocurrencyService.create(cryptocurrencyRegisterDTODAI).getId();

        assertEquals(cryptocurrencyId, cryptocurrencyService.findById(cryptocurrencyId).getId());
    }

    @Test
    void throwsResourceNotFoundWhenTryToGetADeleteACryptocurrency() {
        int cryptocurrencyId = getCryptocurrencyDB().getId();
        cryptocurrencyService.delete(cryptocurrencyId);

        assertThrows(ResourceNotFoundException.class, () -> cryptocurrencyService.findById(cryptocurrencyId));
    }

    @Test
    void updateACryptocurrencyCheckFieldModified() throws ResourceNotFoundException {
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
    void getTheLatestQuoteFromCryptocurrency() throws ResourceNotFoundException {
        Cryptocurrency cryptocurrency = getCryptocurrencyDB();
        quoteService.create(cryptocurrency.getId(), dataSet.getSomePriceInRangeDAI());
        int quote2Id = quoteService.create(cryptocurrency.getId(), dataSet.getSomePriceInRangeDAI() + 2000).getId();

        assertEquals(quote2Id, cryptocurrencyService.findById(cryptocurrency.getId()).latestQuote().getId());
    }

    @Test
    void obtain5WhenGetTheLast24HoursQuotesFromCryptocurrency() throws ResourceNotFoundException {
        Cryptocurrency cryptocurrency = getCryptocurrencyDB();
        quoteService.create(cryptocurrency.getId(), dataSet.getSomePriceInRangeDAI());
        quoteService.create(cryptocurrency.getId(), dataSet.getSomePriceInRangeDAI());
        quoteService.create(cryptocurrency.getId(), dataSet.getSomePriceInRangeDAI());
        quoteService.create(cryptocurrency.getId(), dataSet.getSomePriceInRangeDAI());
        Quote oldQuote = quoteService.create(cryptocurrency.getId(), dataSet.getSomePriceInRangeDAI());
        oldQuote.setDateTime(new DateTimeInMilliseconds().getCurrentTimeMinusOneDayInMilliseconds());
        quoteService.update(oldQuote);

        assertEquals(5, cryptocurrencyService.findById(cryptocurrency.getId()).last24HoursQuotes().size());
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
    void get4QuotesFromACryptocurrency() throws ResourceNotFoundException {
        int cryptocurrencyId = getCryptocurrencyDB().getId();
        quoteService.create(cryptocurrencyId, dataSet.getSomePriceInRangeDAI());
        quoteService.create(cryptocurrencyId, dataSet.getSomePriceInRangeDAI());
        quoteService.create(cryptocurrencyId, dataSet.getSomePriceInRangeDAI());

        assertEquals(4, cryptocurrencyService.findById(cryptocurrencyId).getQuotes().size());
    }

    @Test
    void getEmptyIntentionsFromACryptocurrencyWithoutIntentions() {
        Cryptocurrency cryptocurrency = getCryptocurrencyDB();

        assertTrue(cryptocurrency.getIntentions().isEmpty());
    }

    @Test
    void whenDeleteQuoteCheckDoesNotExistInCryptocurrency() throws ResourceNotFoundException {
        int cryptocurrencyId = getCryptocurrencyDB().getId();
        Quote quote = quoteService.create(cryptocurrencyId, dataSet.getSomePriceInRangeDAI());
        quoteService.delete(quote.getId());
        Cryptocurrency updatedCryptocurrency = cryptocurrencyService.findById(cryptocurrencyId);

        assertFalse(updatedCryptocurrency.getQuotes().contains(quote));
    }

    @Test
    void afterCreateANewQuoteWithCryptocurrencyXCheckDependencies() throws ResourceNotFoundException {
        int cryptocurrencyId = getCryptocurrencyDB().getId();
        Quote quote = quoteService.create(cryptocurrencyId, dataSet.getSomePriceInRangeDAI());

        assertTrue(cryptocurrencyService.findById(cryptocurrencyId).getQuotes().stream().anyMatch(q-> q.getDateTime() == quote.getDateTime()));
    }
}
