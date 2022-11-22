package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DateTimeInMilliseconds;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
class QuoteModelTests {

    public Cryptocurrency mockCryptocurrency = Mockito.mock(Cryptocurrency.class);

    public Double price = 200.00;

    @BeforeEach
    public void init() throws ResourceNotFoundException {
        Mockito.when(mockCryptocurrency.getName()).thenReturn("DAI");
    }

    @Test
    void ObtainCryptocurrencyNameFromNewQuoteOfDAICryptocurrency() {
        Quote quote = new Quote(mockCryptocurrency, price);

        assertEquals("DAI", quote.getCryptocurrency().getName());
    }

    @Test
    void ObtainCryptocurrencyPriceFromNewQuoteWithPrice5000() {
        Quote quote = new Quote(mockCryptocurrency, 5000.00);

        assertEquals(5000.00, quote.getPrice());
    }

    @Test
    void ObtainDateTimeFromNewQuoteBetweenARangeOfDates() {
        long beforeTime = new DateTimeInMilliseconds().getCurrentTimeInMilliseconds() - 1;
        Quote quote = new Quote(mockCryptocurrency, price);
        long afterTime = new DateTimeInMilliseconds().getCurrentTimeInMilliseconds() + 1;
        long quoteTime = quote.getDateTime();

        assertTrue((beforeTime < quoteTime) && (quoteTime < afterTime));
    }

    @Test
    void ObtainLatestQuoteFromCryptocurrencyWhenCreateAQuoteForThatCryptocurrency() throws ResourceNotFoundException {
        Quote quote = new Quote(mockCryptocurrency, price);
        Mockito.when(mockCryptocurrency.latestQuote()).thenReturn(quote);

        assertEquals(quote.getId(), mockCryptocurrency.latestQuote().getId());
    }
}