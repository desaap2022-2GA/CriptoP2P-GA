package ar.edu.unq.desapp.GrupoA022022.backenddesappapi;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.ICryptocurrencyRepo;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.IIntentionRepo;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.IUserRepo;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.DateTimeInMilliseconds;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.IntentionType;
import net.bytebuddy.pool.TypePool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CryptocurrencyTests {

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
    void CyptocurrencySetWithNameBitcoinRespondsOk () {
        Cryptocurrency cryptocurrency = new Cryptocurrency();
        cryptocurrency.setName("Bitcoin");
        assertEquals("Bitcoin", cryptocurrency.getName());
    }

    @Test
    void CyptocurrencySetWithoutQuoteRespondsOk () {
        Cryptocurrency cryptocurrency = new Cryptocurrency();
        cryptocurrency.setQuotes(new HashSet<>());
        assertEquals(true, cryptocurrency.getQuotes().isEmpty());
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
}
