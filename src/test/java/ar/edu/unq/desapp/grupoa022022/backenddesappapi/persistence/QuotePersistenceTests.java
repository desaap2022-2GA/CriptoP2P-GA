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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
class QuotePersistenceTests {

    DataSet dataSet = new DataSet();

    @Autowired
    IQuoteRepo quoteRepo;
    @Autowired
    IQuoteService quoteService;
    @Autowired
    UserService userService;
    @Autowired
    ICryptocurrencyRepo cryptocurrencyRepo;
    @Autowired
    ICryptocurrencyService cryptocurrencyService;
    @Autowired
    IIntentionService intentionService;
    @Autowired
    IOperationService operationService;

    public CryptocurrencyRegister cryptocurrencyRegister = new CryptocurrencyRegister("DAI", 200.00);
    public Cryptocurrency getCryptocurrencyDB(){ return cryptocurrencyService.create(cryptocurrencyRegister);}

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
    void recoverANewQuotePersisted() throws NoSuchElementException {
        Quote quoteDB = quoteRepo.save(new Quote(getCryptocurrencyDB(), dataSet.getSomePriceInRangeDAI()));
        int idSaved = quoteDB.getId();

        assertEquals(idSaved, quoteRepo.findById(idSaved).get().getId());
    }

    @Test
    void createANewQuote() throws ResourceNotFound {
        int quoteId = quoteService.create(getCryptocurrencyDB().getId(), dataSet.getSomePriceInRangeDAI()).getId();

        assertEquals(quoteId, quoteService.findById(quoteId).getId());

    }

    @Test
    void afterCreateANewQuoteWithCryptocurrencyXCheckDependencies() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = getCryptocurrencyDB();
        Quote quote = quoteService.create(cryptocurrency.getId(), dataSet.getSomePriceInRangeDAI());

        assertEquals(cryptocurrency.getName(), quote.getCryptocurrency().getName());
        assertTrue(cryptocurrencyService.findById(cryptocurrency.getId()).getQuotes().stream().anyMatch(q -> q.getDateTime() == quote.getDateTime()));
    }

    @Test
    void updateQuotePriceCheckChange() throws ResourceNotFound {
        Quote quote = quoteService.create(getCryptocurrencyDB().getId(), dataSet.getSomePriceInRangeDAI());
        quote.setPrice(3.00);
        quoteService.update(quote);

        assertEquals(3, quoteService.findById(quote.getId()).getPrice());
    }

    @Test
    void deleteQuoteCheckDoesNotExist() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = getCryptocurrencyDB();
        Quote quote = quoteService.create(cryptocurrency.getId(), dataSet.getSomePriceInRangeDAI());
        quoteService.delete(quote.getId());
        Cryptocurrency updatedCryptocurrency = cryptocurrencyService.findById(cryptocurrency.getId());

        assertFalse(updatedCryptocurrency.getQuotes().contains(quote));
    }

    @Test
    void deleteAllQuotes() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = getCryptocurrencyDB();
        quoteService.create(cryptocurrency.getId(), dataSet.getSomePriceInRangeDAI());
        quoteService.create(cryptocurrency.getId(), dataSet.getSomePriceInRangeDAI() + 10);
        quoteService.deleteAll();
        Cryptocurrency updatedCryptocurrency = cryptocurrencyService.findById(cryptocurrency.getId());

        assertTrue(updatedCryptocurrency.getQuotes().isEmpty());
    }

    @Test
    void getQuoteById() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = getCryptocurrencyDB();
        quoteService.create(cryptocurrency.getId(), dataSet.getSomePriceInRangeDAI());
        Quote quote2 = quoteService.create(cryptocurrency.getId(), dataSet.getSomePriceInRangeDAI() + 10);
        int quote2Id = quote2.getId();

        assertEquals(quote2Id, quoteService.findById(quote2Id).getId());
    }

    @Test
    void intentionPriceInARangeOfFivePercentRespectQuotePrice() throws ResourceNotFound {
        Quote quote = quoteService.create(getCryptocurrencyDB().getId(), dataSet.getSomePriceInRangeDAI());
        double intentionPrice = dataSet.getSomePriceInRangeDAI() * 0.95;

        assertTrue(quote.intentionPriceInARangeOfFiveUpAndDown(intentionPrice));
    }

    @Test
    void intentionPriceNotInARangeOfFivePercentRespectQuotePrice() throws ResourceNotFound {
        Quote quote = quoteService.create(getCryptocurrencyDB().getId(), dataSet.getSomePriceInRangeDAI());
        double intentionPrice = dataSet.getSomePriceInRangeDAI() * 1.06;

        assertFalse(quote.intentionPriceInARangeOfFiveUpAndDown(intentionPrice));
    }

    @Test
    void intentionPriceHigherThanQuotePrice() throws ResourceNotFound {
        Quote quote = quoteService.create(getCryptocurrencyDB().getId(), dataSet.getSomePriceInRangeDAI());
        double intentionPrice = dataSet.getSomePriceInRangeDAI() * 1.01;

        assertTrue(quote.intentionPriceHigherThanQuotePrice(intentionPrice));
    }

    @Test
    void intentionPriceNotHigherThanQuotePrice() throws ResourceNotFound {
        Quote quote = quoteService.create(getCryptocurrencyDB().getId(), dataSet.getSomePriceInRangeDAI());
        double intentionPrice = dataSet.getSomePriceInRangeDAI() * 1;

        assertFalse(quote.intentionPriceHigherThanQuotePrice(intentionPrice));
    }

    @Test
    void intentionPriceLowerThanQuotePrice() throws ResourceNotFound {
        Quote quote = quoteService.create(getCryptocurrencyDB().getId(), dataSet.getSomePriceInRangeDAI());
        double intentionPrice = dataSet.getSomePriceInRangeDAI() * 0.90;

        assertTrue(quote.intentionPriceLowerThanQuotePrice(intentionPrice));
    }

    @Test
    void intentionPriceNotLowerThanQuotePrice() throws ResourceNotFound {
        Quote quote = quoteService.create(getCryptocurrencyDB().getId(), dataSet.getSomePriceInRangeDAI());
        double intentionPrice = dataSet.getSomePriceInRangeDAI() * 1.90;

        assertFalse(quote.intentionPriceLowerThanQuotePrice(intentionPrice));
    }
}