package ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserView;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.EmailAlreadyExists;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ExceptionsUser;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserPersistenceTests {

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

//**************** SERVICE - PERSISTANCE ****************

    //SAVE
    @Test
    void recoversPersistanceANewUser() throws ResourceNotFoundException {
        User saved = userRepo.save(prueUser1);
        Integer idSaved = saved.getId();
        User finded = userRepo.findById(idSaved).orElseThrow(() -> new ResourceNotFoundException("nonexistent user"));

        assertEquals(idSaved, finded.getId());
    }

    //GET ALL
    @Test
    void databaseHasTwoUsers() {
        // userRepo.deleteAll();
        userRepo.save(prueUser1);
        userRepo.save(prueUser2);

        List<UserView> users = userService.getAllUsers();

        assertEquals(2, users.toArray().length);

    }

    //PUT
    @Test
    void modifyAnUserWithId1() throws ResourceNotFoundException, ExceptionsUser {
        //    userRepo.deleteAll();
        int idUSer1 = userRepo.save(prueUser1).getId();
        userRepo.save(prueUser2);
        userRepo.save(prueUser3);
        User userRecov = userRepo.findById(idUSer1).get();
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
        List<UserView> users = userService.getAllUsers();

        assertEquals(cantUsers - 1, users.toArray().length);
    }

    //GET EMAIL ******
    @Test
    void givenTheEmailOfAUserItIsRetrievedFromTheDB() throws ResourceNotFoundException {
        //   userRepo.deleteAll();
        userRepo.save(prueUser1);
        userRepo.save(prueUser2);
        userRepo.save(prueUser3);

        UserView newUser = userService.findByEmail("federer@yahoo.com");

        assertEquals("Roger", newUser.getName());
    }

    //EXISTS EMAIL EXCEPTION *********
    @Test
    void checkIfAnEmailIsInTheDatabaseAndCanNotFindIt() throws ExceptionsUser, ResourceNotFoundException {
        // userRepo.deleteAll();
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
        //   userRepo.deleteAll();
        userRepo.save(prueUser1);
        userRepo.save(prueUser2);

        userService.checkNewUserEmail(prueUser3.getEmail());

        List<UserView> users = userService.getAllUsers();

        assertEquals(2, users.toArray().length);
    }
}