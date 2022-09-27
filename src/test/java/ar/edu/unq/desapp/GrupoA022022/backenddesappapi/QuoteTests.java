package ar.edu.unq.desapp.GrupoA022022.backenddesappapi;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.ICryptocurrencyRepo;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.IQuoteRepo;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.DateTimeInMilliseconds;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
class QuoteTests {

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

    //**************** SERVICE - REPOSITORY ****************

    @Test
    void recoverANewQuotePersisted() {
        Cryptocurrency cryptocurrencyDB = cryptocurrencyRepo.save(dataSet.getCryptocurrency3());
        Quote quoteDB = quoteRepo.save(new Quote(cryptocurrencyDB, dataSet.getSomePrice()));
        int idSaved = quoteDB.getId();

        assertEquals(quoteRepo.findById(idSaved).get().getId(), idSaved);
    }
}