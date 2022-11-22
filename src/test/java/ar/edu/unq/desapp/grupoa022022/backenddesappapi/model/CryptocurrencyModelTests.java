package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CryptocurrencyModelTests {

    public User mockUser = Mockito.mock(User.class);

    public Double priceInRange = 190.00;

    public IntentionType buyType = IntentionType.BUY;

    public int units1 = 1;

    @Test
    void ObtainCryptocurrencyNameFromNewCryptocurrency() {
        Cryptocurrency cryptocurrency = new Cryptocurrency("Bitcoin");

        assertEquals("Bitcoin", cryptocurrency.getName());
    }

    @Test
    void ObtainNotQuotesFromNewCryptocurrency() {
        Cryptocurrency cryptocurrency = new Cryptocurrency("DAI");

        assertTrue(cryptocurrency.getQuotes().isEmpty());
    }

    @Test
    void Obtain0IdFromCryptocurrencyThatNotWasPersisted() {
        Cryptocurrency cryptocurrency = new Cryptocurrency();

        assertEquals(0, cryptocurrency.getId());
    }

    @Test
    void ObtainNotIntentionsFromNewCryptocurrency() {
        Cryptocurrency cryptocurrency = new Cryptocurrency("ADA");

        assertTrue(cryptocurrency.getIntentions().isEmpty());
    }

    @Test
    void ThrowExceptionAskingForLastQuoteFromNewCryptocurrency() {
        assertThrows(ResourceNotFoundException.class, () -> {
            Cryptocurrency cryptocurrency = new Cryptocurrency("LUNA");
            cryptocurrency.latestQuote();
        });
    }

    @Test
    void ObtainLastQuoteFromCryptocurrencySettingWithAPairOfQuotesAnNotExpectingTheFirstOfThem() throws ResourceNotFoundException {
        Cryptocurrency cryptocurrency = new Cryptocurrency("SOL");
        Quote quote1 = new Quote(cryptocurrency, 1000.00);
        Quote quote2 = new Quote(cryptocurrency, 5000.00);
        quote2.setDateTime(quote1.getDateTime() + 1);

        assertNotEquals(quote1, cryptocurrency.latestQuote());
    }

    @Test
    void ObtainLastQuoteFromCryptocurrencySettingWithAPairOfQuotes() throws ResourceNotFoundException {
        Cryptocurrency cryptocurrency = new Cryptocurrency("SOL");
        Quote quote1 = new Quote(cryptocurrency, 1000.00);
        Quote quote2 = new Quote(cryptocurrency, 5000.00);
        quote2.setDateTime(quote1.getDateTime() + 1);

        assertEquals(quote2, cryptocurrency.latestQuote());
    }

    @Test
    void IntentionExistInCryptocurrencyIntentionsWhenCreateAnIntentionWithThatCryptocurrency() {
        Cryptocurrency cryptocurrency = new Cryptocurrency("SOL");
        Intention intention = new Intention(buyType, cryptocurrency, priceInRange, units1, mockUser);

        assertTrue(cryptocurrency.getIntentions().contains(intention));
    }
}