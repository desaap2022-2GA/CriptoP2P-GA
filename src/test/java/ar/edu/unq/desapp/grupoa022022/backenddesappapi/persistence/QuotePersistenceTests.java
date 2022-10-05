package ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.ICryptocurrencyService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.IQuoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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


    //**************** SERVICE - REPOSITORY ****************

    @Test
    void recoverANewQuotePersisted() {
        Cryptocurrency cryptocurrencyDB = cryptocurrencyRepo.save(dataSet.getCryptocurrency3());
        Quote quoteDB = quoteRepo.save(new Quote(cryptocurrencyDB, dataSet.getSomePrice()));
        int idSaved = quoteDB.getId();

        assertEquals(quoteRepo.findById(idSaved).get().getId(), idSaved);
    }

    @Test
    void createANewQuote() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("DAI");
        int quoteId = quoteService.create(cryptocurrency, dataSet.getSomePrice()).getId();

        assertEquals(quoteId, quoteService.findById(quoteId).getId());
    }


    @Test
    void afterCreateANewQuoteWithCryptocurrencyXCheckDependencies() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("X");
        Quote quote = quoteService.create(cryptocurrency, dataSet.getSomePrice());

        assertEquals(cryptocurrency, quote.getCryptocurrency());
        assertTrue(cryptocurrency.getQuotes().contains(quote));
    }

    @Test
    void updateQuotePriceCheckChange() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("X");
        Quote quote = quoteService.create(cryptocurrency, dataSet.getSomePrice());
        quote.setPrice(3.00);
        quoteService.update(quote);

        assertEquals(3, quoteService.findById(quote.getId()).getPrice());
    }
/*
    @Test
    void deleteQuoteCheckDoesNotExist() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("X");
        Quote quote = quoteService.create(cryptocurrency, dataSet.getSomePrice());
        quoteService.delete(quote.getId());
        Cryptocurrency updatedCryptocurrency = cryptocurrencyService.findById(cryptocurrency.getId());

        assertTrue(updatedCryptocurrency.getQuotes().isEmpty());
    }
*/

    @Test
    void deleteAllQuotes() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = cryptocurrencyService.create("X");
        quoteService.create(cryptocurrency, dataSet.getSomePrice());
        quoteService.create(cryptocurrency, dataSet.getSomePrice()+10);
        quoteService.deleteAll();
        Cryptocurrency updatedCryptocurrency = cryptocurrencyService.findById(cryptocurrency.getId());

        assertTrue(updatedCryptocurrency.getQuotes().isEmpty());
    }

/*
    void deleteAll();

    List<Quote> getAll();

    Quote findById(int id) throws ResourceNotFound;

    boolean intentionPriceInARangeOfFiveUpAndDownRespectToQuotePrice(double intentionPrice, Quote quote);

    boolean intentionPriceMoreThanQuotePrice(double intentionPrice, Quote quote);

    boolean intentionPriceLessThanQuotePrice(double intentionPrice, Quote quote);*/
}