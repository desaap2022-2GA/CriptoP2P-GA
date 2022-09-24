package ar.edu.unq.desapp.GrupoA022022.backenddesappapi;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.ICryptocurrencyRepo;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.IIntentionRepo;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.IUserRepo;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.DateTimeInMilliseconds;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.IntentionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
public class IntentionTests {

    DataSetTest dataSetTest = new DataSetTest();

    @Autowired
    IIntentionRepo intentionRepo;
    @Autowired
    IUserRepo userRepo;
    @Autowired
    ICryptocurrencyRepo cryptocurrencyRepo;

    @Test
    void ObtainPriceInIntentionSettingWithAPriceOf1000() {
        Intention intention = new Intention();
        intention.setPrice(1000.00);
        assertEquals(1000.00, intention.getPrice());
    }

    @Test
    void ObtainTypeInIntentionSettingWithTypeBuy() {
        Intention intention = new Intention();
        intention.setType(IntentionType.BUY);
        assertEquals(IntentionType.BUY, intention.getType());
    }

    @Test
    void ObtainUnitsInIntentionSettingWithUnits4() {
        Intention intention = new Intention();
        intention.setUnits(4);
        assertEquals(4, intention.getUnits());
    }

    @Test
    void ObtainUserInIntentionSettingWithUserPrueUser() {
        Intention intention = new Intention();
        intention.setUser(dataSetTest.getUserTest());
        assertEquals(dataSetTest.getUserTest(), intention.getUser());
    }

    @Test
    void ObtainNullInIntentionSettingWithANullCryptocurrency() {
        Intention intention = new Intention();
        intention.setCryptocurrency(null);
        assertNull(intention.getCryptocurrency());
    }

    @Test
    void ObtainCryptocurrencyNameInIntentionSettingWithACryptocurrencyNamedDAI() {
        Intention intention = new Intention();
        intention.setCryptocurrency(dataSetTest.getCryptocurrency());
        assertEquals("DAI", intention.getCryptocurrency().getName());
    }

    @Test
    void ObtainDateTimeInIntentionSettingWithADateTimeOfCreation() {
        Intention intention = new Intention();
        Long currentTime = new DateTimeInMilliseconds().getCurrentTimeInMilliseconds();
        intention.setDateTime(currentTime);
        assertEquals(currentTime, intention.getDateTime());
    }

    @Test
    void IntentionExistInCryptocurrencyIntentionsWhenCreateAnIntentionWithThatCryptocurrency() {
        Intention intention = new Intention(dataSetTest.getSomeType(), dataSetTest.getCryptocurrency(),
                dataSetTest.getSomePrice(), dataSetTest.getSomeUnit(), dataSetTest.getUserTest());
        assertTrue(dataSetTest.getCryptocurrency().getIntentions().contains(intention));
    }

    @Test
    void IntentionExistInUserIntentionsWhenCreateAnIntentionWithThatUser() {
        Intention intention = new Intention(dataSetTest.getSomeType(), dataSetTest.getCryptocurrency(),
                dataSetTest.getSomePrice(), dataSetTest.getSomeUnit(), dataSetTest.getUserTest());
        assertTrue(dataSetTest.getUserTest().getIntentions().contains(intention));
    }


    //**************** SERVICE - REPOSITORY ****************

    @Test
    void recoversPersistanceANewIntention() {
        User someuserDB = userRepo.save(dataSetTest.getUserTest());
        Cryptocurrency somecryptocurrencyDB = cryptocurrencyRepo.save(dataSetTest.getCryptocurrency2());
        Intention saved = intentionRepo.save(new Intention(IntentionType.SELL, somecryptocurrencyDB, 1000.00
                , 1, someuserDB));
        int idSaved = saved.getId();
        assertEquals(intentionRepo.findById(idSaved).get().getId(), idSaved);
    }
}