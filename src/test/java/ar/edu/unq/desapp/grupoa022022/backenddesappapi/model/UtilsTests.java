package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.UserValidationException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceNotInAValidRangeException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.CryptocurrencyService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.IntentionService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.OperationService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.UserService;
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
/*
    @Test
    void getAmountInDollars() {

        assertEquals(150, new DollarConvert().amountInDollars(30000.00));
    }
*/
    //SERVICE

    @Test
    void getACryptoDetailsWhenCallIntentionCryptoDetailsWithAnIntention() throws ResourceNotFoundException, PriceNotInAValidRangeException, UserValidationException {
        Cryptocurrency cryptocurrency = cryptocurrencyService.create(dataSet.getCryptocurrencyRegisterDAI());
        User user = userService.saveToDataBase(dataSet.getUserRegister());
        Intention intention = intentionService.create(new IntentionRegisterDTO(dataSet.getSomeTypeBUY()
                , cryptocurrency.getId(), dataSet.getSomePriceInRangeDAI(), dataSet.getSomeUnit(), user.getId()));

        assertEquals(CryptoDetailsDTO.class, helperDTO.intentionToCryptoDetails(intention).getClass());
    }

    @Test
    void getStringCryptoDetailsWhenCallIntentionCryptoDetailsWithAnIntention() throws ResourceNotFoundException, PriceNotInAValidRangeException, UserValidationException {
        Cryptocurrency cryptocurrency = cryptocurrencyService.create(dataSet.getCryptocurrencyRegisterDAI());
        User user = userService.saveToDataBase(dataSet.getUserRegister());
        Intention intention = intentionService.create(new IntentionRegisterDTO(dataSet.getSomeTypeBUY()
                , cryptocurrency.getId(), dataSet.getSomePriceInRangeDAI(), dataSet.getSomeUnit(), user.getId()));

        assertEquals("CryptoDetailsDTO(name=DAI, units=3, actualQuote=320.38, pesosAmount=915.0)", helperDTO.intentionToCryptoDetails(intention).toString());
    }

    @Test
    void userChangeDataIfNewParamsAreNotNull() throws UserValidationException {
        User user = new User("Mara", "Lopez", "Mara@gmail.com", "Luro 234", "1234"
                , "1234567899876543211236", "123654");

        User userReturned = helperDTO.userModify(user, "email", "m@gmail.com");

        assertEquals(dataString(user), dataString(userReturned));
    }

    @Test
    void oneCryptoDetailsIsPresentOnSetWhenItIsAddedToIt(){
        TradedBetweenDatesDTO tradedBetweenDatesDTO = new TradedBetweenDatesDTO(200.00, 40000);
        tradedBetweenDatesDTO.addCryptoDetails(new CryptoDetailsDTO("X",2,1.00,2.00));

        assertEquals(1, tradedBetweenDatesDTO.getCryptoDetailDTOS().size());
    }

    public String dataString(User user) {
        return user.getName() + user.getLastname() + user.getEmail() + user.getAddress() + user.getPassword() + user.getMercadoPagoCVU() + user.getAddressWalletActiveCrypto();
    }
}