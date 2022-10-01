package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.DateTimeInMilliseconds;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.IntentionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class IntentionPersistenceTests {

    DataSet dataSet = new DataSet();

    @Autowired
    IIntentionRepo intentionRepo;
    @Autowired
    IUserRepo userRepo;
    @Autowired
    ICryptocurrencyRepo cryptocurrencyRepo;

    //**************** SERVICE - REPOSITORY ****************

    @Test
    void recoversPersistanceANewIntention() {
        User someuserDB = userRepo.save(dataSet.getUserTest());
        Cryptocurrency somecryptocurrencyDB = cryptocurrencyRepo.save(dataSet.getCryptocurrency2());
        Intention intentionDB = intentionRepo.save(new Intention(dataSet.getSomeType(), somecryptocurrencyDB,
                dataSet.getSomePrice(), dataSet.getSomeUnit(), someuserDB));
        int idSaved = intentionDB.getId();

        assertEquals(intentionRepo.findById(idSaved).get().getId(), idSaved);
    }
}