package ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.ICryptocurrencyService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IQuoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class QuotePersistenceTests {

    DataSet dataSet = new DataSet();

    @Autowired
    IQuoteRepo quoteRepo;
    @Autowired
    IQuoteService quoteService;
    @Autowired
    ICryptocurrencyRepo cryptocurrencyRepo;
    @Autowired
    ICryptocurrencyService cryptocurrencyService;

    public Cryptocurrency getCryptocurrencyDB(){ return cryptocurrencyService.create("DAI");}

    //**************** SERVICE - REPOSITORY ****************

    @Test
    void recoverANewQuotePersisted() throws NoSuchElementException {
        Quote quoteDB = quoteRepo.save(new Quote(getCryptocurrencyDB(), dataSet.getSomePrice()));
        int idSaved = quoteDB.getId();

        assertEquals(idSaved, quoteRepo.findById(idSaved).get().getId());
    }

    @Test
    void createANewQuote() throws ResourceNotFound {
        int quoteId = quoteService.create(getCryptocurrencyDB(), dataSet.getSomePrice()).getId();

        assertEquals(quoteId, quoteService.findById(quoteId).getId());

    }

    @Test
    void afterCreateANewQuoteWithCryptocurrencyXCheckDependencies() {
        Cryptocurrency cryptocurrency = getCryptocurrencyDB();
        Quote quote = quoteService.create(cryptocurrency, dataSet.getSomePrice());

        assertEquals(cryptocurrency, quote.getCryptocurrency());
        assertTrue(cryptocurrency.getQuotes().contains(quote));
    }

    @Test
    void updateQuotePriceCheckChange() throws ResourceNotFound {
        Quote quote = quoteService.create(getCryptocurrencyDB(), dataSet.getSomePrice());
        quote.setPrice(3.00);
        quoteService.update(quote);

        assertEquals(3, quoteService.findById(quote.getId()).getPrice());
    }

    @Test
    void deleteQuoteCheckDoesNotExist() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = getCryptocurrencyDB();
        Quote quote = quoteService.create(cryptocurrency, dataSet.getSomePrice());
        quoteService.delete(quote.getId());
        Cryptocurrency updatedCryptocurrency = cryptocurrencyService.findById(cryptocurrency.getId());

        assertTrue(updatedCryptocurrency.getQuotes().isEmpty());
    }

    @Test
    void deleteAllQuotes() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = getCryptocurrencyDB();
        quoteService.create(cryptocurrency, dataSet.getSomePrice());
        quoteService.create(cryptocurrency, dataSet.getSomePrice() + 10);
        quoteService.deleteAll();
        Cryptocurrency updatedCryptocurrency = cryptocurrencyService.findById(cryptocurrency.getId());

        assertTrue(updatedCryptocurrency.getQuotes().isEmpty());
    }

    @Test
    void getQuoteById() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = getCryptocurrencyDB();
        quoteService.create(cryptocurrency, dataSet.getSomePrice());
        Quote quote2 = quoteService.create(cryptocurrency, dataSet.getSomePrice() + 10);
        int quote2Id = quote2.getId();

        assertEquals(quote2Id, quoteService.findById(quote2Id).getId());
    }

    @Test
    void intentionPriceInARangeOfFivePercentRespectQuotePrice() {
        Quote quote = quoteService.create(getCryptocurrencyDB(), dataSet.getSomePrice());
        double intentionPrice = dataSet.getSomePrice() * 0.95;

        assertTrue(quoteService.intentionPriceInARangeOfFiveUpAndDownRespectToQuotePrice(intentionPrice, quote));
    }

    @Test
    void intentionPriceNotInARangeOfFivePercentRespectQuotePrice() {
        Quote quote = quoteService.create(getCryptocurrencyDB(), dataSet.getSomePrice());
        double intentionPrice = dataSet.getSomePrice() * 1.06;

        assertFalse(quoteService.intentionPriceInARangeOfFiveUpAndDownRespectToQuotePrice(intentionPrice, quote));
    }

    @Test
    void intentionPriceHigherThanQuotePrice() {
        Quote quote = quoteService.create(getCryptocurrencyDB(), dataSet.getSomePrice());
        double intentionPrice = dataSet.getSomePrice() * 1.01;

        assertTrue(quoteService.intentionPriceHigherThanQuotePrice(intentionPrice, quote));
    }

    @Test
    void intentionPriceNotHigherThanQuotePrice() {
        Quote quote = quoteService.create(getCryptocurrencyDB(), dataSet.getSomePrice());
        double intentionPrice = dataSet.getSomePrice() * 1;

        assertFalse(quoteService.intentionPriceHigherThanQuotePrice(intentionPrice, quote));
    }

    @Test
    void intentionPriceLowerThanQuotePrice() {
        Quote quote = quoteService.create(getCryptocurrencyDB(), dataSet.getSomePrice());
        double intentionPrice = dataSet.getSomePrice() * 0.90;

        assertTrue(quoteService.intentionPriceLowerThanQuotePrice(intentionPrice, quote));
    }

    @Test
    void intentionPriceNotLowerThanQuotePrice() {
        Quote quote = quoteService.create(getCryptocurrencyDB(), dataSet.getSomePrice());
        double intentionPrice = dataSet.getSomePrice() * 1.90;

        assertFalse(quoteService.intentionPriceLowerThanQuotePrice(intentionPrice, quote));
    }
}