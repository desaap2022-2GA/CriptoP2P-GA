package ar.edu.unq.desapp.GrupoA022022.backenddesappapi;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.utils.IntentionType;

public class DataSetTest {

    private final User userTest = new User("Gaston", "Gaudio", "gaudio@yahoo.com",
            "Av Libertador 5000, CABA", "1111", "6352879863528798635287",
            "Xwf5u5ef");

    private final User userTest2 = new User("Martin", "Fierro", "fierro@gmail.com",
            "Av Cordoba 3000, CABA", "1111", "6352879863528798635287",
            "Xwf5u5ef");
    private final Cryptocurrency cryptocurrency = new Cryptocurrency("DAI");
    private final Cryptocurrency cryptocurrency2 = new Cryptocurrency("USDT");
    private final Cryptocurrency cryptocurrency3 = new Cryptocurrency("BITCOIN");

    private final Cryptocurrency cryptocurrency4 = new Cryptocurrency("ETHER");
    private final IntentionType someType = IntentionType.SELL;

    private final Double somePrice = 1000.00;

    private final int someUnit = 3;

    private final Quote someQuote = new Quote();

    private final Quote quote100 = new Quote(cryptocurrency, 100.00);

    private final Quote quote200 = new Quote(cryptocurrency, 200.00);

    public Quote getQuote100() {
        return quote100;
    }

    public Quote getQuote200() {
        return quote200;
    }

    public Quote getSomeQuote() {
        return someQuote;
    }

    public Intention getIntentionSell() {
        return intentionSell;
    }

    public Intention getIntentionBuy() {
        return intentionBuy;
    }

    private final Intention intentionSell = new Intention(IntentionType.SELL, cryptocurrency,
            somePrice, someUnit, userTest);

    private final Intention intentionBuy = new Intention(IntentionType.BUY, cryptocurrency,
            somePrice, someUnit, userTest);

    public User getUserTest() {
        return userTest;
    }

    public User getUserTest2() {
        return userTest2;
    }

    public Cryptocurrency getCryptocurrency() {
        return cryptocurrency;
    }

    public IntentionType getSomeType() {
        return someType;
    }

    public Double getSomePrice() {
        return somePrice;
    }

    public int getSomeUnit() {
        return someUnit;
    }

    public Cryptocurrency getCryptocurrency2() {
        return cryptocurrency2;
    }

    public Cryptocurrency getCryptocurrency3() {
        return cryptocurrency3;
    }
    public Cryptocurrency getCryptocurrency4() {
        return cryptocurrency4;
    }
}
