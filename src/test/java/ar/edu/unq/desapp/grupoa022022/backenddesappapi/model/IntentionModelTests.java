package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.ICryptocurrencyRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IIntentionRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IUserRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DateTimeInMilliseconds;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IntentionModelTests {

    DataSet dataSet = new DataSet();

    @Autowired
    IIntentionRepo intentionRepo;
    @Autowired
    IUserRepo userRepo;
    @Autowired
    ICryptocurrencyRepo cryptocurrencyRepo;

    @Test
    void ObtainPriceFromNewIntentionWithAPriceOf1000() {
        Intention intention = new Intention(dataSet.getSomeTypeBUY(), dataSet.getCryptocurrency(), 1000.00,
                dataSet.getSomeUnit(), dataSet.getUserTest());

        assertEquals(1000.00, intention.getPrice());
    }

    @Test
    void ObtainUnitsFromNewIntentionWithUnits5() {
        Intention intention = new Intention(dataSet.getSomeTypeBUY(), dataSet.getCryptocurrency(), dataSet.getSomePriceInRangeDAI(),
                5, dataSet.getUserTest());

        assertEquals(5, intention.getUnits());
    }

    @Test
    void ObtainCryptocurrencyNameFromNewIntentionWithCryptocurrencyDAI() {
        Intention intention = new Intention(dataSet.getSomeTypeBUY(), new Cryptocurrency("DAI"), dataSet.getSomePriceInRangeDAI(),
                dataSet.getSomeUnit(), dataSet.getUserTest());

        assertEquals("DAI", intention.getCryptocurrency().getName());
    }

    @Test
    void ObtainTypeFromNewIntentionWithBuyType() {
        Intention intention = new Intention(IntentionType.BUY, dataSet.getCryptocurrency(), dataSet.getSomePriceInRangeDAI(),
                dataSet.getSomeUnit(), dataSet.getUserTest());

        assertEquals(IntentionType.BUY, intention.getType());
    }

    @Test
    void ObtainEmailUserFromNewIntentionWithEmailUserFierro() {
        Intention intention = new Intention(dataSet.getSomeTypeBUY(), dataSet.getCryptocurrency(), dataSet.getSomePriceInRangeDAI(),
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
        Intention intention = new Intention(dataSet.getSomeTypeBUY(), dataSet.getCryptocurrency(),
                dataSet.getSomePriceInRangeDAI(), dataSet.getSomeUnit(), dataSet.getUserTest());

        assertTrue(dataSet.getCryptocurrency().getIntentions().contains(intention));
    }

    @Test
    void IntentionExistInUserIntentionsWhenCreateAnIntentionWithThatUser() {
        Intention intention = new Intention(dataSet.getSomeTypeBUY(), dataSet.getCryptocurrency(),
                dataSet.getSomePriceInRangeDAI(), dataSet.getSomeUnit(), dataSet.getUserTest());

        assertTrue(dataSet.getUserTest().getIntentions().contains(intention));
    }
}