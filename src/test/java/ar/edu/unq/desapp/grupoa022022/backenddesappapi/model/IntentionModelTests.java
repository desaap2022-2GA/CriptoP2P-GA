package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DateTimeInMilliseconds;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class IntentionModelTests {

    public Cryptocurrency mockCryptocurrency = Mockito.mock(Cryptocurrency.class);

    public User mockUser = Mockito.mock(User.class);

    public Operation mockOperation = Mockito.mock(Operation.class);

    public User mockUserWhoAccepts = Mockito.mock(User.class);

    public Quote mockQuote = Mockito.mock(Quote.class);

    public Double priceInRange = 190.00;

    public IntentionType buyType = IntentionType.BUY;

    public IntentionType sellType = IntentionType.SELL;

    public int units1 = 1;

    @BeforeEach
    public void init() throws ResourceNotFoundException {
        Mockito.when(mockCryptocurrency.getId()).thenReturn(1);
        Mockito.when(mockCryptocurrency.getName()).thenReturn("DAI");
        Mockito.when(mockCryptocurrency.latestQuote()).thenReturn(mockQuote);
        Mockito.when(mockQuote.getPrice()).thenReturn(200.00);
        Mockito.when(mockUserWhoAccepts.getAddressWalletActiveCrypto()).thenReturn("Xwf5u5ef");
        Mockito.when(mockUserWhoAccepts.getMercadoPagoCVU()).thenReturn("6352879863528798635287");
        Mockito.when(mockUser.getAddressWalletActiveCrypto()).thenReturn("Xwf5u5ef");
        Mockito.when(mockUser.getMercadoPagoCVU()).thenReturn("6352879863528798635287");
    }

    @Test
    void ObtainPriceFromNewIntentionWithAPriceOf1000() {
        Intention intention = new Intention(buyType, mockCryptocurrency, 1000.00, units1, mockUser);

        assertEquals(1000.00, intention.getPrice());
    }

    @Test
    void ObtainUnitsFromNewIntentionWithUnits5() {
        Intention intention = new Intention(buyType, mockCryptocurrency, priceInRange, 5, mockUser);

        assertEquals(5, intention.getUnits());
    }

    @Test
    void ObtainCryptocurrencyNameFromNewIntentionWithCryptocurrencyDAI() {
        Intention intention = new Intention(buyType, mockCryptocurrency, priceInRange, units1, mockUser);

        assertEquals("DAI", intention.getCryptocurrency().getName());
    }

    @Test
    void ObtainTypeFromNewIntentionWithBuyType() {
        Intention intention = new Intention(buyType, mockCryptocurrency, priceInRange, units1, mockUser);

        assertEquals(IntentionType.BUY, intention.getType());
    }

    @Test
    void ObtainUserFromNewIntentionWithUserSett() {
        Intention intention = new Intention(buyType, mockCryptocurrency, priceInRange, units1, mockUser);

        assertEquals(mockUser, intention.getUser());
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
        Intention intention = new Intention(buyType, mockCryptocurrency, priceInRange, units1, mockUser);
        Mockito.when(mockCryptocurrency.getIntentions()).thenReturn(Stream.of(intention).collect(Collectors.toSet()));

        assertTrue(mockCryptocurrency.getIntentions().contains(intention));
    }

    @Test
    void IntentionExistInUserIntentionsWhenCreateAnIntentionWithThatUser() {
        Intention intention = new Intention(buyType, mockCryptocurrency, priceInRange, units1, mockUser);
        Mockito.when(mockUser.getIntentions()).thenReturn(Stream.of(intention).collect(Collectors.toSet()));

        assertTrue(mockUser.getIntentions().contains(intention));
    }

    @Test
    void ObtainCVUMercadoPagoFromAnIntentionTypedAsSellAskingWithUserWhoAccepts() {
        Intention intention = new Intention(sellType, mockCryptocurrency, priceInRange, units1, mockUser);

        assertEquals("6352879863528798635287", intention.transactionInfoToShow(mockUserWhoAccepts));
    }

    @Test
    void ObtainAddressWalletCryptoFromAnIntentionTypedAsBuyAskingWithUserWhoAccepts() {
        Intention intention = new Intention(buyType, mockCryptocurrency, priceInRange, units1, mockUser);

        assertEquals("Xwf5u5ef", intention.transactionInfoToShow(mockUserWhoAccepts));
    }

    @Test
    void ObtainAddressWalletCryptoFromAnIntentionTypedAsSellAskingWithUserWhoPost() {
        Intention intention = new Intention(sellType, mockCryptocurrency, priceInRange, units1, mockUser);
        intention.setOperation(mockOperation);
        Mockito.when(mockOperation.getUserWhoAccepts()).thenReturn(mockUserWhoAccepts);

        assertEquals("Xwf5u5ef", intention.transactionInfoToShow(mockUser));
    }

    @Test
    void ObtainCVUMercadoPagoFromAnIntentionTypedAsBuyAskingWithUserWhoPost() {
        Intention intention = new Intention(buyType, mockCryptocurrency, priceInRange, units1, mockUser);
        intention.setOperation(mockOperation);
        Mockito.when(mockOperation.getUserWhoAccepts()).thenReturn(mockUserWhoAccepts);

        assertEquals("6352879863528798635287", intention.transactionInfoToShow(mockUser));
    }
}