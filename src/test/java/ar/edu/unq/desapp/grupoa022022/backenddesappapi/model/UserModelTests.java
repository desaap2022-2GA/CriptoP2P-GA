package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ExceptionsUser;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserModelTests {

    public Cryptocurrency mockCryptocurrency = Mockito.mock(Cryptocurrency.class);

    public Double priceInRange = 190.00;

    public IntentionType buyType = IntentionType.BUY;

    public int units1 = 1;

    @Test
    void theNameOfAUserIsCorrect() throws ExceptionsUser {
        User user = new User();
        String name = "Graciela";
        user.setName(name);

        assertEquals("Graciela", user.getName());
    }

    @Test
    void theLastnameOfAUserIsCorrect() throws ExceptionsUser {
        User user = new User();
        String lastname = "Gonzalez";
        user.setLastname(lastname);

        assertEquals("Gonzalez", user.getLastname());
    }

    @Test
    void theLastnameOfAUserDoesNotMeetTheConditionsThrowsAnException() {
        assertThrows(ExceptionsUser.class, () -> {
            User user = new User();
            String lastname = "";
            user.setLastname(lastname);
        });
    }

    @Test
    void theEmailOfAUserIsCorrect() throws ExceptionsUser {
        User user = new User();
        String email = "user@desp.com";
        user.setEmail(email);

        assertEquals("user@desp.com", user.getEmail());
    }

    @Test
    void theEmailOfAUserDoesNotMeetTheConditionsTrowsAnException() {
        assertThrows(ExceptionsUser.class, () -> {
            User user = new User();
            String lastname = "";
            user.setLastname(lastname);
        });
    }

    @Test
    void theAddressOfAUserIsCorrect() throws ExceptionsUser {
        User user = new User();
        String address = "Roque Saenz Peña 352";
        user.setAddress(address);

        assertEquals("Roque Saenz Peña 352", user.getAddress());
    }

    @Test
    void theAddressOfAUserDoesNotMeetTheCOnditionsThrowsAnExpection() {
        assertThrows(ExceptionsUser.class, () -> {
            User user = new User();
            String address = "Roque 352";
            user.setAddress(address);
        });
    }

    @Test
    void thePasswordOfAUserIsCorrect() throws ExceptionsUser {
        User user = new User();
        String password = "Desarrollo1!";
        user.setPassword(password);

        assertEquals("Desarrollo1!", user.getPassword());
    }

    @Test
    void thePasswordOfAUserDoesNotMeetTheConditionsThrowsAnException() {
        assertThrows(ExceptionsUser.class, () -> {
            User user = new User();
            String password = "Desa!";
            user.setPassword(password);
        });
    }

    @Test
    void theCVDDeMercadoLibreOfAUserIsCorrect() throws ExceptionsUser {
        User user = new User();
        String CVUMercadoPago = "1234567890123456789012";
        user.setMercadoPagoCVU(CVUMercadoPago);

        assertEquals("1234567890123456789012", user.getMercadoPagoCVU());
    }

    @Test
    void theCVDDeMarcadoLibreOfAUserDoesNotMeetTheConditionsThrowsAnException() {
        assertThrows(ExceptionsUser.class, () -> {
            User user = new User();
            String CVUMercadoPago = "123456789012345678901";
            user.setPassword(CVUMercadoPago);
        });
    }

    @Test
    void theActiveCryptoWalletAddressOfAUserIsCorrect() throws ExceptionsUser {
        User user = new User();
        String addressWalletActiveCrypto = "12345678";
        user.setaddressWalletActiveCrypto(addressWalletActiveCrypto);

        assertEquals("12345678", user.getaddressWalletActiveCrypto());
    }

    @Test
    void theAddressCryptoAssetWalletDoesNotMeetTheConditionsThrowsAnException() {
        assertThrows(ExceptionsUser.class, () -> {
            User user = new User();
            String addressWalletActiveCrypto = "123456789";
            user.setaddressWalletActiveCrypto(addressWalletActiveCrypto);
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
}