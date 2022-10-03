package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ExceptionsUser;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserModelTests {

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
    void theAdressOfAUserIsCorrect() throws ExceptionsUser {
        User user = new User();
        String address = "Roque Saenz Peña 352";
        user.setAddress(address);

        assertEquals(user.getAddress(), "Roque Saenz Peña 352");
    }

    @Test
    void theAdressOfAUserDoesNotMeetTheCOnditionsThrowsAnExpection() {
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

        assertEquals(user.getPassword(), "Desarrollo1!");
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
        String adressWalletActiveCripto = "12345678";
        user.setAddressWalletActiveCripto(adressWalletActiveCripto);

        assertEquals("12345678", user.getAddressWalletActiveCripto());
    }

    @Test
    void theAddressCryptoAssetWalletDoesNotMeetTheConditionsThrowsAnException() {
        assertThrows(ExceptionsUser.class, () -> {
            User user = new User();
            String adressWalletActiveCripto = "123456789";
            user.setAddressWalletActiveCripto(adressWalletActiveCripto);
        });
    }

    @Test
    void aUserHas10Points() {
        User user = new User();
        int point = 10;
        user.setPoints(point);

        assertEquals(user.getPoints(), 10);
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
}