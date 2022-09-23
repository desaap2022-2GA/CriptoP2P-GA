package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.modelo;


import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.IUserRepo;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ExceptionsUser;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class BackendDesappApiApplicationTests {

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private UserService userService;


    final private User prueUser = new User("Roger", "Federer", "federer@yahoo.com",
            "Av Libertador 5000, CABA", "1111", "6352879863528798635287",
            "Xwfui5ef");

    @Test
    void theNameOfAUserIsCorrect() throws ExceptionsUser {
        User user = new User();
        String name = "Graciela";
        user.setName(name);
        System.out.println("en elNombreDeUnUsuarioEsCorrecto");

        assertEquals(user.getName(), "Graciela");
    }

    @Test
    void theLastnameOfAUserIsCorrect() throws ExceptionsUser {
        User user = new User();
        String lastname = "Gonzalez";
        user.setLastname(lastname);

        assertEquals(user.getLastname(), "Gonzalez");
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

        assertEquals(user.getEmail(), "user@desp.com");
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
        String adress = "Roque Saenz Peña 352";
        user.setAdress(adress);

        assertEquals(user.getAdress(), "Roque Saenz Peña 352");
    }

    @Test
    void theAdressOfAUserDoesNotMeetTheCOnditionsThrowsAnExpection() {
        assertThrows(ExceptionsUser.class, () -> {
            User user = new User();
            String adress = "Roque 352";
            user.setAdress(adress);
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
        user.setCVUMercadoPago(CVUMercadoPago);

        assertEquals(user.getCVUMercadoPago(), "1234567890123456789012");
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
        user.setAdressWalletActiveCripto(adressWalletActiveCripto);

        assertEquals(user.getAdressWalletActiveCripto(), "12345678");
    }

    @Test
    void theAddressCryptoAssetWalletDoesNotMeetTheConditionsThrowsAnException() {
        assertThrows(ExceptionsUser.class, () -> {
            User user = new User();
            String adressWalletActiveCripto = "123456789";
            user.setAdressWalletActiveCripto(adressWalletActiveCripto);
        });
    }

    @Test
    void aUserHas10Points(){
        User user = new User();
        int point = 10;
        user.setPoints(point);

        assertEquals(user.getPoints(), 10);
    }

    @Test
    void aUserHas5Operations(){
        User user = new User();
        int operations = 5;
        user.setNumberOperations(operations);

        assertEquals(user.getNumberOperations(), 5);
    }

    @Test
    void aUserHas10PointsAnd5OperationsSoHasAReputationOf2(){
        User user = new User();
        int point = 10;
        int operations = 5;
        user.setPoints(point);
        user.setNumberOperations(operations);
        user.setReputation();

        assertEquals(user.getReputation(), 2);
    }

    @Test
    void aUserHas10PointsAnd0OperationsSoHasAReputationOf2(){
        User user = new User();
        int point = 10;
        int operations = 0;
        user.setPoints(point);
        user.setNumberOperations(operations);
        user.setReputation();

        assertEquals(user.getReputation(), 0);
    }

    @Test
    void aUserHas10PointsAnd3OperationsSoHasAReputationOf3(){
        User user = new User();
        int point = 10;
        int operations = 3;
        user.setPoints(point);
        user.setNumberOperations(operations);
        user.setReputation();

        assertEquals(user.getReputation(), 3);
    }

    @Test
    void aUserHas10PointsAnd4OperationsSoHasAReputationOf2(){
        User user = new User();
        int point = 10;
        int operations = 4;
        user.setPoints(point);
        user.setNumberOperations(operations);
        user.setReputation();

        assertEquals(user.getReputation(), 2);
    }

    @Test
    void aUserHas11PointsAnd4OperationsSoHasAReputationOf2(){
        User user = new User();
        int point = 11;
        int operations = 4;
        user.setPoints(point);
        user.setNumberOperations(operations);
        user.setReputation();

        assertEquals(user.getReputation(), 2);
    }

//**************** SERVICE - REPOSITORY ****************

    @Test
    void recoversPersistanceANewUser() {
        User saved = userRepo.save(prueUser);
        Integer idSaved = saved.getId();
        Optional<User> finded = userRepo.findById(idSaved);

        assertEquals(finded.get().getId(), idSaved);
    }

    @Test
    void modifyAnUserWithId1() throws ResourceNotFoundException, ExceptionsUser {
        User userRecov = userService.findById(1);
        userRecov.setEmail("rogerFederer@gmail.com");
        userRepo.save(userRecov);

        assertEquals(userRecov.getEmail(), "rogerFederer@gmail.com");
    }/*

    @Test
    void databaseHasTwoUsers(){
        List<User> users = userService.getAllUsers();

        assertEquals(users.toArray().length, 2);
    }

    @Test
    void theUserWithId2IsDeletedFromTheDatabaseSoThereIsOnlyOneUser() throws ResourceNotFoundException {
        userService.delete(2);
        List<User> users = userService.getAllUsers();

        assertEquals(users.toArray().length, 1);

    }*/

    @Test
    void givenTheIdOfAUserItIsRetrievedFromTheDB() throws ResourceNotFoundException {
        User newUser = userService.findById(1);

        assertEquals(prueUser.getName(), newUser.getName());
    }/*

    @Test
    void givenTheEmailOfAUserItIsRetrievedFromTheDB() throws ResourceNotFoundException {
        User newUser = userService.findByEmail("federer@yahoo.com");

        assertEquals(prueUser.getEmail(), newUser.getEmail());
    }

    @Test
    void checkIfAnEmailIsInTheDatabaseAndCanFindIt() throws ExceptionsUser, ResourceNotFoundException {
        User newUser = new User();
        newUser.setName("Rafael");
        newUser.setLastname("Nadal");
        newUser.setEmail("federer@yahoo.com");
        newUser.setAdress("Av Libertador 5001, CABA");
        newUser.setPassword("Damero22");
        newUser.setCVUMercadoPago("1234567890123456789012");
        newUser.setAdressWalletActiveCripto("12345678");
        newUser.setReputation();
        newUser.setPoints(5);
        newUser.setNumberOperations(5);

        assertThrows(ExceptionsUser.class, () -> {
            userService.checkNewUserEmail(newUser);
        });
    }

    @Test
    void checkIfAnEmailIsInTheDatabaseAndCanNotFindItCreatingTheUser() throws ExceptionsUser, ResourceNotFoundException{
        User newUser = new User();
        newUser.setName("Rafael");
        newUser.setLastname("Nadal");
        newUser.setEmail("nadalrafael1@gmail.com");
        newUser.setAdress("Av Libertador 5001, CABA");
        newUser.setPassword("Damero22");
        newUser.setCVUMercadoPago("1234567890123456789012");
        newUser.setAdressWalletActiveCripto("12345678");
        newUser.setReputation();
        newUser.setPoints(5);
        newUser.setNumberOperations(5);

        userService.checkNewUserEmail(newUser);

        List<User> users = userService.getAllUsers();

        assertEquals(users.toArray().length, 7);
    }*/
}