package ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.ICryptocurrencyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuotePersistenceTests {

    DataSet dataSet = new DataSet();

    @Autowired
    private IQuoteRepo quoteRepo;

    @Autowired
    private ICryptocurrencyService cryptocurrencyService;


    //**************** REPOSITORY ****************

    @Test
    void recoverANewQuotePersisted() throws NoSuchElementException {
        Cryptocurrency cryptocurrency = cryptocurrencyService.getAll().stream().findAny().orElseThrow();

        Quote quoteDB = quoteRepo.save(new Quote(cryptocurrency, dataSet.getSomePriceInRangeDAI()));
        int idSaved = quoteDB.getId();

        assertEquals(idSaved, quoteRepo.findById(idSaved).orElseThrow().getId());
    }
}