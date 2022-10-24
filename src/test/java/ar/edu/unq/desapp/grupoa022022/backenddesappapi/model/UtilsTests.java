package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptoDetails;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.HelperDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionRegister;
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
public class UtilsTests {

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
                        , cryptocurrency.getId(),dataSet.getSomePriceInRangeDAI(), dataSet.getSomeUnit(), user.getId()));

        assertEquals(CryptoDetails.class, helperDTO.intentionToCryptoDetails(intention).getClass());
    }

    @Test
    void getStringCryptoDetailsWhenCallIntentionCryptoDetailsWithAnIntention() throws ResourceNotFound, PriceNotInAValidRange {
        Cryptocurrency cryptocurrency = cryptocurrencyService.create(dataSet.getCryptocurrencyRegisterDAI());
        User user = userService.saveToDataBase(dataSet.getUserRegister());
        Intention intention = intentionService.create(new IntentionRegister(dataSet.getSomeTypeBUY()
                , cryptocurrency.getId(),dataSet.getSomePriceInRangeDAI(), dataSet.getSomeUnit(), user.getId()));

        assertEquals("CryptoDetails(name=DAI, units=3, actualQuote=320.38, pesosAmount=915.0)",helperDTO.intentionToCryptoDetails(intention).toString());
    }
}