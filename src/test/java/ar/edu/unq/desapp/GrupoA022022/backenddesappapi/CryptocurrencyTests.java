package ar.edu.unq.desapp.GrupoA022022.backenddesappapi;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CryptocurrencyTests {

    DataSetTest dataSetTest = new DataSetTest();

    @Test
    void ObtainNameOfCyptocurrencySettingWithNameBitcoin() {
        dataSetTest.getCryptocurrency().setName("Bitcoin");
        assertEquals("Bitcoin", dataSetTest.getCryptocurrency().getName());
    }

    @Test
    void ObtainNotQuotesInCyptocurrencySettingWithoutQuotes() {
        dataSetTest.getCryptocurrency().setQuotes(new HashSet<>());
        assertTrue(dataSetTest.getCryptocurrency().getQuotes().isEmpty());
    }

    @Test
    void ObtainNullIdInCryptocurrencyThanNotWasPersisted() {
        assertNull(dataSetTest.getCryptocurrency().getId());
    }

    @Test
    void ObtainNotIntentionsInCyptocurrencySettingWithoutIntentions() {
        dataSetTest.getCryptocurrency().setIntentions(new HashSet<>());
        assertTrue(dataSetTest.getCryptocurrency().getIntentions().isEmpty());
    }

    @Test
    void ThrowExceptionAskingForLastQuoteInCyptocurrencySettingWithoutQuotes() {
        assertThrows(ResourceNotFoundException.class, () -> {
            dataSetTest.getCryptocurrency().setQuotes(new HashSet<>());
            dataSetTest.getCryptocurrency().latestQuote();
        });
    }

    @Test
    void ObtainLastQuoteInCyptocurrencySettingWithoutAPairOfQuotesAnNotExpectingTheFirstOfThem() throws ResourceNotFoundException {
        dataSetTest.getQuote200().setDateTime(dataSetTest.getQuote100().getDateTime() + 1);
        dataSetTest.getCryptocurrency().addNewQuote(dataSetTest.getQuote100());
        dataSetTest.getCryptocurrency().addNewQuote(dataSetTest.getQuote200());
        assertNotEquals(dataSetTest.getQuote100(), dataSetTest.getCryptocurrency().latestQuote());
    }

    @Test
    void ObtainLastQuoteInCyptocurrencySettingWithoutAPairOfQuotes() throws ResourceNotFoundException {
        dataSetTest.getQuote200().setDateTime(dataSetTest.getQuote100().getDateTime() + 1);
        dataSetTest.getCryptocurrency().addNewQuote(dataSetTest.getQuote100());
        dataSetTest.getCryptocurrency().addNewQuote(dataSetTest.getQuote200());
        assertEquals(dataSetTest.getQuote200(), dataSetTest.getCryptocurrency().latestQuote());
    }
}