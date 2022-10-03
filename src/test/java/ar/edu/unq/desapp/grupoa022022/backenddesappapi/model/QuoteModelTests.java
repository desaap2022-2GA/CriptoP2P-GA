package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.ICryptocurrencyRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IQuoteRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DateTimeInMilliseconds;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class QuoteModelTests {

    DataSet dataSet = new DataSet();

    @Autowired
    IQuoteRepo quoteRepo;

    @Autowired
    ICryptocurrencyRepo cryptocurrencyRepo;

    @Test
    void ObtainCryptocurrencyNameFromNewQuoteOfDAICryptocurrency() {
        Quote quote = new Quote(new Cryptocurrency("DAI"), dataSet.getSomePrice());

        assertEquals("DAI", quote.getCryptocurrency().getName());
    }

    @Test
    void ObtainCryptocurrencyPriceFromNewQuoteWithPrice5000() {
        Quote quote = new Quote(dataSet.getCryptocurrency(), 5000.00);

        assertEquals(5000.00, quote.getPrice());
    }

    @Test
    void ObtainDateTimeFromNewQuoteBetweenARangeOfDates() throws InterruptedException {
        long beforeTime = new DateTimeInMilliseconds().getCurrentTimeInMilliseconds() - 1;
        Quote quote = new Quote(dataSet.getCryptocurrency(), dataSet.getSomePrice());
        long afterTime = new DateTimeInMilliseconds().getCurrentTimeInMilliseconds() + 1;
        long quoteTime = quote.getDateTime();

        assertTrue((beforeTime < quoteTime) && (quoteTime < afterTime));
    }

    @Test
    void ObtainLatestQuoteFromCryptocurrencyWhenCreateAQuoteForThatCryptocurrency() throws ResourceNotFoundException {
        Quote quote = new Quote(dataSet.getCryptocurrency(), dataSet.getSomePrice());

        assertEquals(quote, dataSet.getCryptocurrency().latestQuote());
    }
}