package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CryptocurrencyPersistenceTests {

    DataSet dataSet = new DataSet();

    @Autowired
    ICryptocurrencyRepo cryptocurrencyRepo;

    //**************** SERVICE - REPOSITORY ****************

    @Test
    void recoversPersistenceANewCryptocurrency() {
        Cryptocurrency saved = cryptocurrencyRepo.save(new Cryptocurrency("DAI"));
        int idSaved = saved.getId();
        Optional<Cryptocurrency> finded = cryptocurrencyRepo.findById(idSaved);

        assertEquals(finded.get().getId(), idSaved);
    }
}