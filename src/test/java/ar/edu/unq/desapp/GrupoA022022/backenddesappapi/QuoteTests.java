package ar.edu.unq.desapp.GrupoA022022.backenddesappapi;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.DateTimeInMilliseconds;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class QuoteTests {

    final Cryptocurrency cryptocurrency = new Cryptocurrency("DAI");
    final Quote quote = new Quote();

    private Double somePrice = 1000.00;

    @Test
    void QuoteSettingWithNameDAIIsConfirmed() {
        quote.setCryptocurrency(cryptocurrency);
        assertEquals("DAI", quote.getCryptocurrency().getName());
    }

    @Test
    void QuoteSettingWithPrice5000IsConfirmed() {
        quote.setPrice(5000.00);
        assertEquals(5000.00, quote.getPrice());
    }

    @Test
    void QuoteSettingWithDateTimeIsConfirmedInARange() throws InterruptedException {
        Long beforTime = new DateTimeInMilliseconds().currentTimeInMilliseconds-1;
        Quote quote = new Quote(cryptocurrency,somePrice);
        Long afterTime = new DateTimeInMilliseconds().currentTimeInMilliseconds+1;
        Long quoteTime = quote.getDateTime();
        assertTrue((beforTime<quoteTime)&&(quoteTime<afterTime));
    }
}