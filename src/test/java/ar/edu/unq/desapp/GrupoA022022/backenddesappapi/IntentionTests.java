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
class IntentionTests {

    DataSet dataSet = new DataSet();

    @Autowired
    IIntentionRepo intentionRepo;
    @Autowired
    IUserRepo userRepo;
    @Autowired
    ICryptocurrencyRepo cryptocurrencyRepo;

    @Test
    void ObtainPriceFromNewIntentionWithAPriceOf1000() {
        Intention intention = new Intention(dataSet.getSomeType(), dataSet.getCryptocurrency(), 1000.00,
                dataSet.getSomeUnit(), dataSet.getUserTest());

        assertEquals(1000.00, intention.getPrice());
    }

    @Test
    void ObtainUnitsFromNewIntentionWithUnits5() {
        Intention intention = new Intention(dataSet.getSomeType(), dataSet.getCryptocurrency(), dataSet.getSomePrice(),
                5, dataSet.getUserTest());

        assertEquals(5, intention.getUnits());
    }

    @Test
    void ObtainCryptocurrencyNameFromNewIntentionWithCryptocurrencyDAI() {
        Intention intention = new Intention(dataSet.getSomeType(), new Cryptocurrency("DAI"), dataSet.getSomePrice(),
                dataSet.getSomeUnit(), dataSet.getUserTest());

        assertEquals("DAI", intention.getCryptocurrency().getName());
    }

    @Test
    void ObtainTypeFromNewIntentionWithBuyType() {
        Intention intention = new Intention(IntentionType.BUY, dataSet.getCryptocurrency(), dataSet.getSomePrice(),
                dataSet.getSomeUnit(), dataSet.getUserTest());

        assertEquals(IntentionType.BUY, intention.getType());
    }

    @Test
    void ObtainEmailUserFromNewIntentionWithEmailUserFierro() {
        Intention intention = new Intention(dataSet.getSomeType(), dataSet.getCryptocurrency(), dataSet.getSomePrice(),
                dataSet.getSomeUnit(), dataSet.getUserTest2());

        assertEquals("fierro@gmail.com", intention.getUser().getEmail());
    }

    @Test
    void ObtainUnitsInIntentionSettingWithUnits4() {
        Intention intention = new Intention();
        intention.setUnits(4);

        assertEquals(4, intention.getUnits());
    }

    @Test
    void ObtainNullInIntentionSettingWithANullCryptocurrency() {
        Intention intention = new Intention();
        intention.setCryptocurrency(null);

        assertNull(intention.getCryptocurrency());
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
        Intention intention = new Intention(dataSet.getSomeType(), dataSet.getCryptocurrency(),
                dataSet.getSomePrice(), dataSet.getSomeUnit(), dataSet.getUserTest());

        assertTrue(dataSet.getCryptocurrency().getIntentions().contains(intention));
    }

    @Test
    void IntentionExistInUserIntentionsWhenCreateAnIntentionWithThatUser() {
        Intention intention = new Intention(dataSet.getSomeType(), dataSet.getCryptocurrency(),
                dataSet.getSomePrice(), dataSet.getSomeUnit(), dataSet.getUserTest());

        assertTrue(dataSet.getUserTest().getIntentions().contains(intention));
    }


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