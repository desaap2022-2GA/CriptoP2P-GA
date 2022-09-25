package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.modelo;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.IUserRepo;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ExceptionsUser;

import java.util.Optional;

@SpringBootTest

class BackendDesappApiApplicationTests {

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

        assertEquals(user.getReputation(), 2);
    }

    @Test
    void aUserHas10PointsAnd0OperationsSoHasAReputationOf2(){
        User user = new User();
        int point = 10;
        int operations = 0;
        user.setPoints(point);
        user.setNumberOperations(operations);

        assertEquals(user.getReputation(), 0);
    }

    @Test
    void aUserHas10PointsAnd3OperationsSoHasAReputationOf3(){
        User user = new User();
        int point = 10;
        int operations = 3;
        user.setPoints(point);
        user.setNumberOperations(operations);

        assertEquals(user.getReputation(), 3);
    }

    @Test
    void aUserHas10PointsAnd4OperationsSoHasAReputationOf2(){
        User user = new User();
        int point = 10;
        int operations = 4;
        user.setPoints(point);
        user.setNumberOperations(operations);

        assertEquals(user.getReputation(), 2);
    }

    @Test
    void aUserHas11PointsAnd4OperationsSoHasAReputationOf2(){
        User user = new User();
        int point = 11;
        int operations = 4;
        user.setPoints(point);
        user.setNumberOperations(operations);

        assertEquals(user.getReputation(), 2);
    }


//**************** SERVICE - PERSISTANCE ****************

    @Test
    void recoversPersistanceANewUser() {
        User saved = userRepo.save(prueUser1);
        Integer idSaved = saved.getId();
        Optional<User> finded = userRepo.findById(idSaved);

        assertEquals(finded.get().getId(), idSaved);
    }
/*
    @Test
    void recoversPersistanceAnOtherUser() {
        User saved = userRepo.save(prueUser2);
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
    }


    @Test
    void databaseHasTwoUsers(){
        List<User> users = userService.getAllUsers();

        assertEquals(users.toArray().length, 4);

    }

    @Test
    void theUserWithId2IsDeletedFromTheDatabaseSoThereIsOnlyOneUser() throws ResourceNotFoundException {
        int cantUsers = userService.getAllUsers().toArray().length;
        userService.delete(2);
        List<User> users = userService.getAllUsers();


<<<<<<< HEAD
    }
/*
=======
        assertEquals(users.toArray().length, cantUsers-1);

    }

>>>>>>> 7fb21c4a64b944a20ccd7ffe33cb2463f857d7bc
    @Test
    void givenTheIdOfAUserItIsRetrievedFromTheDB() throws ResourceNotFoundException {
        User newUser = userService.findById(1);

<<<<<<< HEAD
        assertEquals(prueUser.getName(), newUser.getName());
    }
*//*
=======
        assertEquals(prueUser1.getName(), newUser.getName());
    }

>>>>>>> 7fb21c4a64b944a20ccd7ffe33cb2463f857d7bc
    @Test
    void givenTheEmailOfAUserItIsRetrievedFromTheDB() throws ResourceNotFoundException {
        User newUser = userService.findByEmail("rogerFederer@gmail.com");

        assertEquals(newUser.getName(), "Roger");
    }
*//*
    @Test
    void checkIfAnEmailIsInTheDatabaseAndCanFindIt() throws ExceptionsUser, ResourceNotFoundException {
        User newUser = prueUser1;

        assertThrows(ExceptionsUser.class, () -> {
            userService.checkNewUserEmail(newUser);
        });
    }

    @Test
    void checkIfAnEmailIsInTheDatabaseAndCanNotFindItCreatingTheUser() throws ExceptionsUser, ResourceNotFoundException{

        userService.checkNewUserEmail(prueUser3);

        List<User> users = userService.getAllUsers();

        assertEquals(users.toArray().length, 3);
    }*/
}

