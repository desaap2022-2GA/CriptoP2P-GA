package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ExceptionsUser;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceNotInAValidRange;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.CryptocurrencyService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.IntentionService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.OperationService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.UserService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DollarConvert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
class UtilsTests {

    @Autowired
    CryptocurrencyService cryptocurrencyService;

    @Autowired
    IntentionService intentionService;

    @Autowired
    UserService userService;

    @Autowired
    OperationService operationService;
    HelperDTO helperDTO = new HelperDTO();
    DataSet dataSet = new DataSet();

    @BeforeEach
    public void init() {
        //       LOG.info("startup");
        operationService.deleteAll();
        intentionService.deleteAll();
        cryptocurrencyService.deleteAll();
        userService.deleteAllUsers();
    }

    @Test
    void firstArgumentIsNullReturnFalse() {

        assertFalse(helperDTO.firstNotNullAndFirstAndSecondNotEquals(null, "0"));
    }

    @Test
    void firstArgumentNotNullButEqualsToSecondReturnFalse() {

        assertFalse(helperDTO.firstNotNullAndFirstAndSecondNotEquals("0", "0"));
    }

    @Test
    void firstArgumentNotNullAndNotEqualsToSecondReturnTrue() {

        assertTrue(helperDTO.firstNotNullAndFirstAndSecondNotEquals("0", "1"));
    }

    @Test
    void getAmountInDollars() {

        assertEquals(150, new DollarConvert().amountInDollars(30000.00));
    }

    //SERVICE

    @Test
    void getACryptoDetailsWhenCallIntentionCryptoDetailsWithAnIntention() throws ResourceNotFound, PriceNotInAValidRange {
        Cryptocurrency cryptocurrency = cryptocurrencyService.create(dataSet.getCryptocurrencyRegisterDAI());
        User user = userService.saveToDataBase(dataSet.getUserRegister());
        Intention intention = intentionService.create(new IntentionRegister(dataSet.getSomeTypeBUY()
                , cryptocurrency.getId(), dataSet.getSomePriceInRangeDAI(), dataSet.getSomeUnit(), user.getId()));

        assertEquals(CryptoDetails.class, helperDTO.intentionToCryptoDetails(intention).getClass());
    }

    @Test
    void getStringCryptoDetailsWhenCallIntentionCryptoDetailsWithAnIntention() throws ResourceNotFound, PriceNotInAValidRange {
        Cryptocurrency cryptocurrency = cryptocurrencyService.create(dataSet.getCryptocurrencyRegisterDAI());
        User user = userService.saveToDataBase(dataSet.getUserRegister());
        Intention intention = intentionService.create(new IntentionRegister(dataSet.getSomeTypeBUY()
                , cryptocurrency.getId(), dataSet.getSomePriceInRangeDAI(), dataSet.getSomeUnit(), user.getId()));

        assertEquals("CryptoDetails(name=DAI, units=3, actualQuote=320.38, pesosAmount=915.0)", helperDTO.intentionToCryptoDetails(intention).toString());
    }

    @Test
    void userStillWithPreviousDataIfNewParamsAreNull() throws ExceptionsUser {
        User user = new User("Mara", "Lopez", "Mara@gmail.com", "Luro 234", "1234"
                , "1234567899876543211236", "123654");

        UserModify userModify = new UserModify(null, null, null, null, null
                , null, null);

        User userReturned = helperDTO.userModifyToUser(userModify, user);

        assertNotEquals(dataString(userModify), dataString(userReturned));
    }

    @Test
    void userChangeDataIfNewParamsAreNotNull() throws ExceptionsUser {
        User user = new User("Mara", "Lopez", "Mara@gmail.com", "Luro 234", "1234"
                , "1234567899876543211236", "123654");

        UserModify userModify = new UserModify("Ana", "Gris", "m@gmail.com"
                , "Uriburu 52", "3155", "1232587416398521475648", "98765485");

        User userReturned = helperDTO.userModifyToUser(userModify, user);

        assertEquals(dataString(user), dataString(userReturned));
    }

    @Test
    void oneCryptoDetailsIsPresentOnSetWhenItIsAddedToIt(){
        TradedBetweenDates tradedBetweenDates = new TradedBetweenDates(200.00, 40000);
        tradedBetweenDates.addCryptoDetails(new CryptoDetails("X",2,1.00,2.00));

        assertEquals(1, tradedBetweenDates.getCryptoDetails().size());
    }

    public String dataString(User user) {
        return user.getName() + user.getLastname() + user.getEmail() + user.getAddress() + user.getPassword() + user.getMercadoPagoCVU() + user.getAddressWalletActiveCripto();
    }

    public String dataString(UserModify user) {
        return user.getName() + user.getLastname() + user.getEmail() + user.getAddress() + user.getPassword() + user.getMercadoPagoCVU() + user.getAddressWalletActiveCrypto();
    }
}