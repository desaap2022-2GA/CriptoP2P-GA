package ar.edu.unq.desapp.GrupoA022022.backenddesappapi;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.ICryptocurrencyRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
class CryptocurrencyTests {

    DataSet dataSet = new DataSet();

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

    //**************** SERVICE - REPOSITORY ****************

    @Test
    void recoversPersistanceANewCryptocurrency() {
        Cryptocurrency saved = cryptocurrencyRepo.save(new Cryptocurrency("DAI"));
        int idSaved = saved.getId();
        Optional<Cryptocurrency> finded = cryptocurrencyRepo.findById(idSaved);

        assertEquals(finded.get().getId(), idSaved);
    }
}