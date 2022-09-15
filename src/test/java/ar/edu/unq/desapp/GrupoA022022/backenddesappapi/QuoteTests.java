package ar.edu.unq.desapp.GrupoA022022.backenddesappapi;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.DateTimeInMilliseconds;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class QuoteTests {

    private final Cryptocurrency cryptocurrency = new Cryptocurrency("DAI");
    private final Quote quote = new Quote();
    private final Double somePrice = 1000.00;

    @Test
    void ObtainCryptocurrencyNameInQuoteSettingWithNameDAI() {
        quote.setCryptocurrency(cryptocurrency);
        assertEquals("DAI", quote.getCryptocurrency().getName());
    }

    @Test
    void ObtainCryptocurrencyPriceInQuoteSettingWithPrice5000() {
        quote.setPrice(5000.00);
        assertEquals(5000.00, quote.getPrice());
    }

    @Test
    void ObtainDateTimeOnRangeInQuoteSettingWithDateTime() throws InterruptedException {
        long beforeTime = new DateTimeInMilliseconds().currentTimeInMilliseconds-1;
        Quote quote = new Quote(cryptocurrency,somePrice);
        long afterTime = new DateTimeInMilliseconds().currentTimeInMilliseconds+1;
        long quoteTime = quote.getDateTime();
        assertTrue((beforeTime<quoteTime)&&(quoteTime<afterTime));
    }

    @Test
    void ObtainQuoteFromCryptocurrencyWhenCreateAQuoteWhitThatCryptocurrency() throws ResourceNotFoundException {
        Quote quote = new Quote(cryptocurrency, somePrice);
        assertEquals(quote, cryptocurrency.latestQuote());

    }
}