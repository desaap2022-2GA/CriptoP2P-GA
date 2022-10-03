package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.ICryptocurrencyRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CryptocurrencyModelTests {

    @Autowired
    ICryptocurrencyRepo cryptocurrencyRepo;

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
        assertThrows(ResourceNotFound.class, () -> {
            Cryptocurrency cryptocurrency = new Cryptocurrency("LUNA");
            cryptocurrency.latestQuote();
        });
    }

    @Test
    void ObtainLastQuoteFromCryptocurrencySettingWithAPairOfQuotesAnNotExpectingTheFirstOfThem() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = new Cryptocurrency("SOL");
        Quote quote1 = new Quote(cryptocurrency, 1000.00);
        Quote quote2 = new Quote(cryptocurrency, 5000.00);
        quote2.setDateTime(quote1.getDateTime() + 1);

        assertNotEquals(quote1, cryptocurrency.latestQuote());
    }

    @Test
    void ObtainLastQuoteFromCryptocurrencySettingWithAPairOfQuotes() throws ResourceNotFound {
        Cryptocurrency cryptocurrency = new Cryptocurrency("SOL");
        Quote quote1 = new Quote(cryptocurrency, 1000.00);
        Quote quote2 = new Quote(cryptocurrency, 5000.00);
        quote2.setDateTime(quote1.getDateTime() + 1);

        assertEquals(quote2, cryptocurrency.latestQuote());
    }
}