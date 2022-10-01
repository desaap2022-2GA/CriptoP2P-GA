package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.DateTimeInMilliseconds;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class QuotePersistenceTests {

    DataSet dataSet = new DataSet();

    @Autowired
    IQuoteRepo quoteRepo;

    @Autowired
    ICryptocurrencyRepo cryptocurrencyRepo;

    //**************** SERVICE - REPOSITORY ****************

    @Test
    void recoverANewQuotePersisted() {
        Cryptocurrency cryptocurrencyDB = cryptocurrencyRepo.save(dataSet.getCryptocurrency3());
        Quote quoteDB = quoteRepo.save(new Quote(cryptocurrencyDB, dataSet.getSomePrice()));
        int idSaved = quoteDB.getId();

        assertEquals(quoteRepo.findById(idSaved).get().getId(), idSaved);
    }
}