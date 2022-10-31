package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IQuoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class QuoteServiceTests {

    DataSet dataSet = new DataSet();
/*

    @Mock
    private IQuoteRepo quoteRepo;

    @Mock
    private ICryptocurrencyService cryptocurrencyService;

*/
    @Autowired
    private IQuoteService quoteService;

    public Cryptocurrency mockCryptocurrency = Mockito.mock(Cryptocurrency.class);

    /*
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    */
    @BeforeEach
    public void init() throws ResourceNotFound {
        Mockito.when(mockCryptocurrency.getId()).thenReturn(1);
        Mockito.when(mockCryptocurrency.getName()).thenReturn("DAI");
        //       Mockito.when(quoteRepo.save(any())).thenReturn(any());
    }

    //**************** SERVICE ****************

    @Test
    void createANewQuote() throws ResourceNotFound {
        int quoteId = quoteService.create(mockCryptocurrency.getId(), dataSet.getSomePriceInRangeDAI()).getId();

        assertEquals(quoteId, quoteService.findById(quoteId).getId());
    }

    @Test
    void afterCreateANewQuoteWithCryptocurrencyXCheckDependencies() throws ResourceNotFound {
        Quote quote = quoteService.create(mockCryptocurrency.getId(), dataSet.getSomePriceInRangeDAI());

        assertEquals(mockCryptocurrency.getName(), quote.getCryptocurrency().getName());
    }

    @Test
    void updateQuotePriceCheckChange() throws ResourceNotFound {
        Quote quote = quoteService.create(mockCryptocurrency.getId(), dataSet.getSomePriceInRangeDAI());
        quote.setPrice(3.00);
        quoteService.update(quote);

        assertEquals(3, quoteService.findById(quote.getId()).getPrice());
    }

    @Test
    void deleteQuoteCheckDoesNotExist() throws ResourceNotFound {
        Quote quote = quoteService.create(mockCryptocurrency.getId(), dataSet.getSomePriceInRangeDAI());
        quoteService.delete(quote.getId());

        assertFalse(quoteService.getAll().contains(quote));
    }

    @Test
    void thereIsNotQuotesAfterDeleteAllQuotes() throws ResourceNotFound {
        quoteService.create(mockCryptocurrency.getId(), dataSet.getSomePriceInRangeDAI());
        quoteService.create(mockCryptocurrency.getId(), dataSet.getSomePriceInRangeDAI() + 10);
        quoteService.deleteAll();

        assertTrue(quoteService.getAll().isEmpty());
    }

    @Test
    void getQuoteById() throws ResourceNotFound {
        quoteService.create(mockCryptocurrency.getId(), dataSet.getSomePriceInRangeDAI());
        Quote quote2 = quoteService.create(mockCryptocurrency.getId(), dataSet.getSomePriceInRangeDAI() + 10);
        int quote2Id = quote2.getId();

        assertEquals(quote2Id, quoteService.findById(quote2Id).getId());
    }

    @Test
    void intentionPriceInARangeOfFivePercentRespectQuotePrice() throws ResourceNotFound {
        Quote quote = quoteService.create(mockCryptocurrency.getId(), dataSet.getSomePriceInRangeDAI());
        double intentionPrice = dataSet.getSomePriceInRangeDAI() * 0.95;

        assertTrue(quote.intentionPriceInARangeOfFiveUpAndDown(intentionPrice));
    }

    @Test
    void intentionPriceNotInARangeOfFivePercentRespectQuotePrice() throws ResourceNotFound {
        Quote quote = quoteService.create(mockCryptocurrency.getId(), dataSet.getSomePriceInRangeDAI());
        double intentionPrice = dataSet.getSomePriceInRangeDAI() * 1.06;

        assertFalse(quote.intentionPriceInARangeOfFiveUpAndDown(intentionPrice));
    }

    @Test
    void intentionPriceHigherThanQuotePrice() throws ResourceNotFound {
        Quote quote = quoteService.create(mockCryptocurrency.getId(), dataSet.getSomePriceInRangeDAI());
        double intentionPrice = dataSet.getSomePriceInRangeDAI() * 1.01;

        assertTrue(quote.intentionPriceHigherThanQuotePrice(intentionPrice));
    }

    @Test
    void intentionPriceNotHigherThanQuotePrice() throws ResourceNotFound {
        Quote quote = quoteService.create(mockCryptocurrency.getId(), dataSet.getSomePriceInRangeDAI());
        double intentionPrice = dataSet.getSomePriceInRangeDAI() * 1;

        assertFalse(quote.intentionPriceHigherThanQuotePrice(intentionPrice));
    }

    @Test
    void intentionPriceLowerThanQuotePrice() throws ResourceNotFound {
        Quote quote = quoteService.create(mockCryptocurrency.getId(), dataSet.getSomePriceInRangeDAI());
        double intentionPrice = dataSet.getSomePriceInRangeDAI() * 0.90;

        assertTrue(quote.intentionPriceLowerThanQuotePrice(intentionPrice));
    }

    @Test
    void intentionPriceNotLowerThanQuotePrice() throws ResourceNotFound {
        Quote quote = quoteService.create(mockCryptocurrency.getId(), dataSet.getSomePriceInRangeDAI());
        double intentionPrice = dataSet.getSomePriceInRangeDAI() * 1.90;

        assertFalse(quote.intentionPriceLowerThanQuotePrice(intentionPrice));
    }
}