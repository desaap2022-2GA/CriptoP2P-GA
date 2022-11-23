package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.UserValidationException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DateTimeInMilliseconds;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.OperationState;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserModelTests {

    public Cryptocurrency mockCryptocurrency = Mockito.mock(Cryptocurrency.class);

    public Operation mockOperation = Mockito.mock(Operation.class);

    public Intention mockIntention = Mockito.mock(Intention.class);

    public Double priceInRange = 190.00;

    public IntentionType buyType = IntentionType.BUY;

    public int units1 = 1;

    @Test
    void theNameOfAUserIsCorrect() throws UserValidationException {
        User user = new User();
        String name = "Graciela";
        user.setName(name);

        assertEquals("Graciela", user.getName());
    }

    @Test
    void theLastnameOfAUserIsCorrect() throws UserValidationException {
        User user = new User();
        String lastname = "Gonzalez";
        user.setLastname(lastname);

        assertEquals("Gonzalez", user.getLastname());
    }

    @Test
    void theLastnameOfAUserDoesNotMeetTheConditionsThrowsAnException() {
        assertThrows(UserValidationException.class, () -> {
            User user = new User();
            String lastname = "";
            user.setLastname(lastname);
        });
    }

    @Test
    void theEmailOfAUserIsCorrect() throws UserValidationException {
        User user = new User();
        String email = "user@desp.com";
        user.setEmail(email);

        assertEquals("user@desp.com", user.getEmail());
    }

    @Test
    void theEmailOfAUserDoesNotMeetTheConditionsTrowsAnException() {
        assertThrows(UserValidationException.class, () -> {
            User user = new User();
            String lastname = "";
            user.setLastname(lastname);
        });
    }

    @Test
    void theAddressOfAUserIsCorrect() throws UserValidationException {
        User user = new User();
        String address = "Roque Saenz Peña 352";
        user.setAddress(address);

        assertEquals("Roque Saenz Peña 352", user.getAddress());
    }

    @Test
    void theAddressOfAUserDoesNotMeetTheCOnditionsThrowsAnExpection() {
        assertThrows(UserValidationException.class, () -> {
            User user = new User();
            String address = "Roque 352";
            user.setAddress(address);
        });
    }

    @Test
    void thePasswordOfAUserIsCorrect() throws UserValidationException {
        User user = new User();
        String password = "Desarrollo1!";
        user.setPassword(password);

        assertEquals("Desarrollo1!", user.getPassword());
    }

    @Test
    void thePasswordOfAUserDoesNotMeetTheConditionsThrowsAnException() {
        assertThrows(UserValidationException.class, () -> {
            User user = new User();
            String password = "Desa!";
            user.setPassword(password);
        });
    }

    @Test
    void theCVDDeMercadoLibreOfAUserIsCorrect() throws UserValidationException {
        User user = new User();
        String CVUMercadoPago = "1234567890123456789012";
        user.setMercadoPagoCVU(CVUMercadoPago);

        assertEquals("1234567890123456789012", user.getMercadoPagoCVU());
    }

    @Test
    void theCVDDeMarcadoLibreOfAUserDoesNotMeetTheConditionsThrowsAnException() {
        assertThrows(UserValidationException.class, () -> {
            User user = new User();
            String CVUMercadoPago = "123456789012345678901";
            user.setPassword(CVUMercadoPago);
        });
    }

    @Test
    void theActiveCryptoWalletAddressOfAUserIsCorrect() throws UserValidationException {
        User user = new User();
        String addressWalletActiveCrypto = "12345678";
        user.setAddressWalletActiveCrypto(addressWalletActiveCrypto);

        assertEquals("12345678", user.getAddressWalletActiveCrypto());
    }

    @Test
    void theAddressCryptoAssetWalletDoesNotMeetTheConditionsThrowsAnException() {
        assertThrows(UserValidationException.class, () -> {
            User user = new User();
            String addressWalletActiveCrypto = "123456789";
            user.setAddressWalletActiveCrypto(addressWalletActiveCrypto);
        });
    }

    @Test
    void aUserHas10Points() {
        User user = new User();
        int point = 10;
        user.setPoints(point);

        assertEquals(10, user.getPoints());
    }

    @Test
    void aUserHas5Operations() {
        User user = new User();
        int operations = 5;
        user.setNumberOperations(operations);

        assertEquals(5, user.getNumberOperations());
    }

    @Test
    void aUserHas10PointsAnd5OperationsSoHasAReputationOf2() {
        User user = new User();
        int point = 10;
        int operations = 5;
        user.setPoints(point);
        user.setNumberOperations(operations);

        assertEquals(2, user.getReputation());
    }

    @Test
    void aUserHas10PointsAnd0OperationsSoHasAReputationOf2() {
        User user = new User();
        int point = 10;
        int operations = 0;
        user.setPoints(point);
        user.setNumberOperations(operations);

        assertEquals(0, user.getReputation());
    }

    @Test
    void aUserHas10PointsAnd3OperationsSoHasAReputationOf3() {
        User user = new User();
        int point = 10;
        int operations = 3;
        user.setPoints(point);
        user.setNumberOperations(operations);

        assertEquals(3, user.getReputation());
    }

    @Test
    void aUserHas10PointsAnd4OperationsSoHasAReputationOf2() {
        User user = new User();
        int point = 10;
        int operations = 4;
        user.setPoints(point);
        user.setNumberOperations(operations);

        assertEquals(2, user.getReputation());
    }

    @Test
    void aUserHas11PointsAnd4OperationsSoHasAReputationOf2() {
        User user = new User();
        int point = 11;
        int operations = 4;
        user.setPoints(point);
        user.setNumberOperations(operations);

        assertEquals(2, user.getReputation());
    }

    @Test
    void IntentionExistInUserIntentionsWhenCreateAnIntentionWithThatUser() {
        User user = new User();
        Intention intention = new Intention(buyType, mockCryptocurrency, priceInRange, units1, user);

        assertTrue(user.getIntentions().contains(intention));
    }

    @Test
    void getEmptySetWheAskForOperationsBetweenDatesFromAUserWithoutOperations(){
        User user = new User();

        assertEquals(Collections.emptySet(), user.operationsBetweenDates(new DateTimeInMilliseconds().getCurrentTimeInMilliseconds(),
                new DateTimeInMilliseconds().getCurrentTimeInMilliseconds()));
    }

    @Test
    void getEmptySetWheAskForOperationsBetweenDatesFromAUserWithoutOperationsOnCryptoSentState(){
        User user = new User();
        Set<Operation> operations = new HashSet<>();
        Mockito.when(mockOperation.getDateTime()).thenReturn(new DateTimeInMilliseconds().getCurrentTimeMinus30MinutesInMilliseconds());
        Mockito.when(mockOperation.getState()).thenReturn(OperationState.ACTIVE);
        operations.add(mockOperation);
        user.setOperations(operations);

        assertEquals(Collections.emptySet(), user.operationsBetweenDates(new DateTimeInMilliseconds().getCurrentTimeMinusOneDayInMilliseconds(),
                new DateTimeInMilliseconds().getCurrentTimeInMilliseconds()));
    }
    @Test
    void getOneWhenAskForOperationsBetweenDatesFromUserWhoHasAnOperationOnRangeDateAndCryptosentState(){
        User user = new User();
        Set<Operation> operations = new HashSet<>();
        Mockito.when(mockOperation.getDateTime()).thenReturn(new DateTimeInMilliseconds().getCurrentTimeMinus30MinutesInMilliseconds());
        Mockito.when(mockOperation.getState()).thenReturn(OperationState.CRYPTOSENT);
        operations.add(mockOperation);
        user.setOperations(operations);

        assertEquals(1, user.operationsBetweenDates(new DateTimeInMilliseconds().getCurrentTimeMinusOneDayInMilliseconds(),
                new DateTimeInMilliseconds().getCurrentTimeInMilliseconds()).size());
    }

    @Test
    void getEmptySetWhenAskForOperationsBetweenDatesFromUserWhoHasAnOperationNotOnRangeDateAndCryptosentState(){
        User user = new User();
        Set<Operation> operations = new HashSet<>();
        Mockito.when(mockOperation.getDateTime()).thenReturn(new DateTimeInMilliseconds().getCurrentTimeMinusOneDayInMilliseconds());
        Mockito.when(mockOperation.getState()).thenReturn(OperationState.CRYPTOSENT);
        operations.add(mockOperation);
        user.setOperations(operations);

        assertEquals(Collections.emptySet(), user.operationsBetweenDates(new DateTimeInMilliseconds().getCurrentTimeMinus30MinutesInMilliseconds(),
                new DateTimeInMilliseconds().getCurrentTimeInMilliseconds()));
    }

    @Test
    void get500whenAskForVolumeTradedWhenGivenASetOperationThatHasAnOperationWithAmount500(){
        User user = new User();
        Set<Operation> operations = new HashSet<>();
        Mockito.when(mockOperation.getIntention()).thenReturn(mockIntention);
        Mockito.when(mockIntention.amountPriceInPesos()).thenReturn(500.00);
        operations.add(mockOperation);

        assertEquals(500.00, user.volumeTraded(operations));

    }
}