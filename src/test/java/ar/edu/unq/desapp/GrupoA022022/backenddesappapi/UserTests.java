package ar.edu.unq.desapp.GrupoA022022.backenddesappapi;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.EmailAlreadyExists;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.IUserRepo;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ExceptionsUser;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase
class UserTests {

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private UserService userService;


    final private User prueUser1 = new User("Roger", "Federer", "federer@yahoo.com",
            "Av Libertador 5000, CABA", "3546DelpoWinner", "5469875465852365478952",
            "pup3oi5e");

    final private User prueUser2 = new User("Rafael", "Nadal", "nadal@yahoo.com",
            "Av Libertador 5000, CABA", "123NadalChampion", "5469875465852365478952",
            "pup3oi5e");

    final private User prueUser3 = new User("Juan", "Delpo", "delpo@yahoo.com",
            "Av Libertador 5000, CABA", "321Martin", "5469875465852365478952",
            "pup3oi5e");


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


//**************** SERVICE - PERSISTANCE ****************

    //SAVE
    @Test
    void recoversPersistanceANewUser() {
        User saved = userRepo.save(prueUser1);
        Integer idSaved = saved.getId();
        Optional<User> finded = userRepo.findById(idSaved);

        assertEquals(idSaved, finded.get().getId());
    }

    //GET ALL
    @Test
    void databaseHasTwoUsers(){
        userRepo.deleteAll();
        userRepo.save(prueUser1);
        userRepo.save(prueUser2);

        List<User> users = userService.getAllUsers();

        assertEquals(2, users.toArray().length);

    }

    //PUT
    @Test
    void modifyAnUserWithId1() throws ResourceNotFoundException, ExceptionsUser {
        userRepo.deleteAll();
        userRepo.save(prueUser1);
        userRepo.save(prueUser2);
        userRepo.save(prueUser3);
        User userRecov = userService.findById(1);
        userRecov.setEmail("rogerFederer@gmail.com");
        userRepo.save(userRecov);

        assertEquals("rogerFederer@gmail.com", userRecov.getEmail());
    }




    //DELETTE BY ID
    @Test
    void theUserWithId2IsDeletedFromTheDatabaseSoThereIsOnlyOneUser() throws ResourceNotFoundException {
        userRepo.save(prueUser1);
        userRepo.save(prueUser2);
        userRepo.save(prueUser3);

        int cantUsers = userService.getAllUsers().toArray().length;
        userService.delete(2);
        List<User> users = userService.getAllUsers();

        assertEquals(cantUsers-1, users.toArray().length);
    }

    //GET EMAIL ******
    @Test
    void givenTheEmailOfAUserItIsRetrievedFromTheDB() throws ResourceNotFoundException {
        userRepo.deleteAll();
        userRepo.save(prueUser1);
        userRepo.save(prueUser2);
        userRepo.save(prueUser3);

        User newUser = userService.findByEmail("federer@yahoo.com");

        assertEquals("Roger", newUser.getName());
    }

    //EXISTS EMAIL EXCEPTION *********
    @Test
    void checkIfAnEmailIsInTheDatabaseAndCanNotFindIt() throws ExceptionsUser, ResourceNotFoundException {
        userRepo.deleteAll();
        userRepo.save(prueUser1);
        User newUser = new User();
        newUser.setEmail("milonina@gmail.com");

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.findByEmail(newUser.getEmail());
        });
    }

    //POST  -  ADD NEW USER ************
    @Test
    void checkIfAnEmailIsInTheDatabaseAndCanNotFindItCreatingTheUser() throws EmailAlreadyExists {
        userRepo.deleteAll();
        userRepo.save(prueUser1);
        userRepo.save(prueUser2);

        userService.checkNewUserEmail(prueUser3.getEmail());

        List<User> users = userService.getAllUsers();

        assertEquals(2, users.toArray().length);
    }

}