package ar.edu.unq.desapp.GrupoA022022.backenddesappapi;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.ICryptocurrencyRepo;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.IIntentionRepo;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.IUserRepo;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.DateTimeInMilliseconds;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.IntentionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class IntentionTests {

    @Autowired
    private IUserRepo userRepo;
    @Autowired
    private IIntentionRepo intentionRepo;
    @Autowired
    private ICryptocurrencyRepo cryptocurrencyRepo;

    private User prueUser = new User("Gaston", "Gaudio", "gaudio@yahoo.com",
            "Av Libertador 5000, CABA", "1111", "6352879863528798635287",
            "Xwf5u5ef");

    @Test
    void IntentionSetWithAPriceOf1000RespondsOk () {
        Intention intention = new Intention();
        intention.setPrice(1000.00);
        assertEquals(1000.00, intention.getPrice());
    }

    @Test
    void IntentionSetWithTypeBuyRespondsOk () {
        Intention intention = new Intention();
        intention.setType(IntentionType.BUY);
        assertEquals(IntentionType.BUY, intention.getType());
    }

    @Test
    void IntentionSetWithUnit4RespondsOk () {
        Intention intention = new Intention();
        intention.setUnits(4);  ;
        assertEquals(4, intention.getUnits());
    }

    @Test
    void IntentionSetWithUserPrueUserRespondsOk () {
        Intention intention = new Intention();
        intention.setUser(prueUser); ;
        assertEquals(prueUser, intention.getUser());
    }

    @Test
    void IntentionSetWithANullCryptocurrencyRespondsOk () {
        Intention intention = new Intention();
        intention.setCryptocurrency(null);
        assertEquals(null,intention.getCryptocurrency());
    }

    @Test
    void IntentionSetWithADateTimeOfCreationRespondsOk () {
        Intention intention = new Intention();
        Long currentTime = new DateTimeInMilliseconds().currentTimeInMilliseconds;
        intention.setDateTime(currentTime);
        assertEquals(currentTime,intention.getDateTime());
    }

}
