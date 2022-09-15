package ar.edu.unq.desapp.GrupoA022022.backenddesappapi;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CryptocurrencyTests {

    private final Cryptocurrency cryptocurrency = new Cryptocurrency();
    private final Quote quote1 = new Quote(cryptocurrency,100.00);
    private final Quote quote2 = new Quote(cryptocurrency,200.00);


    @Test
    void ObtainNameOfCyptocurrencySettingWithNameBitcoin() {
        cryptocurrency.setName("Bitcoin");
        assertEquals("Bitcoin", cryptocurrency.getName());
    }

    @Test
    void ObtainNotQuotesInCyptocurrencySettingWithoutQuotes() {
        cryptocurrency.setQuotes(new HashSet<>());
        assertTrue(cryptocurrency.getQuotes().isEmpty());
    }

    @Test
    void ObtainNullIdInCryptocurrencyThanNotWasPersisted() {
        assertNull(cryptocurrency.getId());
    }

    @Test
    void ObtainNotIntentionsInCyptocurrencySettingWithoutIntentions() {
        cryptocurrency.setIntentions(new HashSet<>());
        assertTrue(cryptocurrency.getIntentions().isEmpty());
    }

    @Test
    void ThrowExceptionAskingForLastQuoteInCyptocurrencySettingWithoutQuotes() {
        assertThrows(ResourceNotFoundException.class, () -> {
            cryptocurrency.setQuotes(new HashSet<>());
            cryptocurrency.latestQuote();
        });
    }

    @Test
    void ObtainLastQuoteInCyptocurrencySettingWithoutAPairOfQuotesAnNotExpectingTheFirstOfThem() throws ResourceNotFoundException {
        quote2.setDateTime(quote1.getDateTime()+1);
        cryptocurrency.addNewQuote(quote1);
        cryptocurrency.addNewQuote(quote2);
        assertNotEquals(quote1, cryptocurrency.latestQuote());
    }

    @Test
    void ObtainLastQuoteInCyptocurrencySettingWithoutAPairOfQuotes() throws ResourceNotFoundException {
        quote2.setDateTime(quote1.getDateTime()+1);
        cryptocurrency.addNewQuote(quote1);
        cryptocurrency.addNewQuote(quote2);
        assertEquals(quote2, cryptocurrency.latestQuote());
    }
}
