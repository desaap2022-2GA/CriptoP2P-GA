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
public class QuoteTests {

    DataSetTest dataSetTest = new DataSetTest();

    @Autowired
    IQuoteRepo quoteRepo;

    @Autowired
    ICryptocurrencyRepo cryptocurrencyRepo;
    @Test
    void ObtainCryptocurrencyNameInQuoteSettingWithNameDAI() {
        dataSetTest.getSomeQuote().setCryptocurrency(dataSetTest.getCryptocurrency());
        assertEquals("DAI", dataSetTest.getSomeQuote().getCryptocurrency().getName());
    }

    @Test
    void ObtainCryptocurrencyPriceInQuoteSettingWithPrice5000() {
        dataSetTest.getSomeQuote().setPrice(5000.00);
        assertEquals(5000.00, dataSetTest.getSomeQuote().getPrice());
    }

    @Test
    void ObtainDateTimeOnRangeInQuoteSettingWithDateTime() throws InterruptedException {
        long beforeTime = new DateTimeInMilliseconds().getCurrentTimeInMilliseconds() - 1;
        Quote quote = new Quote(dataSetTest.getCryptocurrency(), dataSetTest.getSomePrice());
        long afterTime = new DateTimeInMilliseconds().getCurrentTimeInMilliseconds() + 1;
        long quoteTime = quote.getDateTime();
        assertTrue((beforeTime < quoteTime) && (quoteTime < afterTime));
    }

    @Test
    void ObtainQuoteFromCryptocurrencyWhenCreateAQuoteWhitThatCryptocurrency() throws ResourceNotFoundException {
        Quote quote = new Quote(dataSetTest.getCryptocurrency(), dataSetTest.getSomePrice());
        assertEquals(quote, dataSetTest.getCryptocurrency().latestQuote());
    }

    //**************** SERVICE - REPOSITORY ****************

    @Test
    void recoversPersistanceANewQuote() {
        Cryptocurrency cryptocurrency = cryptocurrencyRepo.save(dataSetTest.getCryptocurrency3());
        Quote quote = new Quote(cryptocurrency,dataSetTest.getSomePrice());
        Quote saved = quoteRepo.save(quote);
        int idSaved = saved.getId();

        assertEquals(quoteRepo.findById(idSaved).get().getId(), idSaved);
    }
}