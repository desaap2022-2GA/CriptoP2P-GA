package ar.edu.unq.desapp.grupoa022022.backenddesappapi;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;

public class DataSet {

    private final User userTest = new User("Gaston", "Gaudio", "gaudio@yahoo.com",
            "Av Libertador 5000, CABA", "1111", "6352879863528798635287",
            "Xwf5u5ef");

    private final User userTest2 = new User("Martin", "Fierro", "fierro@gmail.com",
            "Av Cordoba 3000, CABA", "1111", "6352879863528798635287",
            "Xwf5u5ef");

    private final UserRegister userRegister = new UserRegister("Gaston", "Gaudio", "gaudio@yahoo.com",
            "Av Libertador 5000, CABA", "1111", "6352879863528798635287",
            "Xwf5u5ef");

    private final UserRegister userRegister2 = new UserRegister("Martin", "Fierro", "fierro@gmail.com",
            "Av Cordoba 3000, CABA", "1111", "6352879863528798635287",
            "Xwf5u5ef");
    private final Cryptocurrency cryptocurrency = new Cryptocurrency("DAI");

    private final Cryptocurrency cryptocurrency2 = new Cryptocurrency("USDT");

    private final IntentionType someTypeBUY = IntentionType.BUY;

    private final Double somePriceInRangeDAI = 305.00;

    private final Double somePriceInRangeBITCOIN = 6132838.92;

    private final int someUnit = 3;

    public Intention getIntentionSell() {
        return intentionSell;
    }

    public Intention getIntentionBuy() {
        return intentionBuy;
    }

    private final Intention intentionSell = new Intention(IntentionType.SELL, cryptocurrency,
            somePriceInRangeDAI, someUnit, userTest);

    private final Intention intentionBuy = new Intention(IntentionType.BUY, cryptocurrency,
            somePriceInRangeDAI, someUnit, userTest);

    public User getUserTest() {
        return userTest;
    }

    public User getUserTest2() {
        return userTest2;
    }

    public UserRegister getUserRegister() {
        return userRegister;
    }

    public UserRegister getUserRegister2() {
        return userRegister2;
    }

    public Cryptocurrency getCryptocurrency() {
        new Quote(cryptocurrency, 305.00);
        return cryptocurrency;
    }

    public IntentionType getSomeTypeBUY() {
        return someTypeBUY;
    }

    public Double getSomePriceInRangeDAI() {
        return somePriceInRangeDAI;
    }

    public Double getSomePriceInRangeBITCOIN() {
        return somePriceInRangeBITCOIN;
    }

    public int getSomeUnit() {
        return someUnit;
    }

    public Cryptocurrency getCryptocurrency2() {
        new Quote(cryptocurrency2, 5607166.15);
        return cryptocurrency2;
    }
}
