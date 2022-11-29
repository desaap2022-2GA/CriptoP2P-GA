package ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserViewDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.EmailAlreadyExistsException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.UserValidationException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.ICryptocurrencyService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IIntentionService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IOperationService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IQuoteService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
class UserPersistenceTests {

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    IQuoteService quoteService;
    @Autowired
    ICryptocurrencyService cryptocurrencyService;
    @Autowired
    IIntentionService intentionService;
    @Autowired
    IOperationService operationService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    final private User prueUser1 = new User("Roger", "Federer", "federer@yahoo.com",
            "Av Libertador 5000, CABA", "", "5469875465852365478952",
            "pup3oi5e");

    final private User prueUser2 = new User("Rafael", "Nadal", "nadal@yahoo.com",
            "Av Libertador 5000, CABA", "", "5469875465852365478952",
            "pup3oi5e");

    final private User prueUser3 = new User("Juan", "Delpo", "delpo@yahoo.com",
            "Av Libertador 5000, CABA", "", "5469875465852365478952",
            "pup3oi5e");

    @BeforeEach
    public void init() {
        //       LOG.info("startup");
        operationService.deleteAll();
        intentionService.deleteAll();
        cryptocurrencyService.deleteAll();
        userService.deleteAllUsers();
    }

//**************** SERVICE - PERSISTANCE ****************

    //SAVE
    @Test
    void recoversPersistanceANewUser() throws ResourceNotFoundException, UserValidationException {
        prueUser1.setPassword(encoder.encode("3546DelpoWinner"));
        User saved = userRepo.save(prueUser1);
        Integer idSaved = saved.getId();
        User finded = userRepo.findById(idSaved).orElseThrow(() -> new ResourceNotFoundException("nonexistent user"));

        assertEquals(idSaved, finded.getId());
    }

    //GET ALL
    @Test
    void databaseHasTwoUsers() throws UserValidationException {
        // userRepo.deleteAll();
        prueUser1.setPassword(encoder.encode("3546DelpoWinner"));
        prueUser2.setPassword(encoder.encode("123NadalChampion"));
        userRepo.save(prueUser1);
        userRepo.save(prueUser2);

        List<UserViewDTO> users = userService.getAllUsers();

        assertEquals(2, users.toArray().length);

    }

    //PUT
    @Test
    void modifyAnUserWithId1() throws UserValidationException {
        //    userRepo.deleteAll();

        prueUser1.setPassword(encoder.encode("3546DelpoWinner"));
        prueUser2.setPassword(encoder.encode("123NadalChampion"));
        prueUser3.setPassword(encoder.encode("321Martin"));

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
    void theUserWithId2IsDeletedFromTheDatabaseSoThereIsOnlyOneUser() throws ResourceNotFoundException, UserValidationException {
        prueUser1.setPassword(encoder.encode("3546DelpoWinner"));
        prueUser2.setPassword(encoder.encode("123NadalChampion"));
        prueUser3.setPassword(encoder.encode("321Martin"));
        userRepo.save(prueUser1);
        int id2 = userRepo.save(prueUser2).getId();
        userRepo.save(prueUser3);

        int cantUsers = userService.getAllUsers().toArray().length;
        userService.delete(id2);
        List<UserViewDTO> users = userService.getAllUsers();

        assertEquals(cantUsers - 1, users.toArray().length);
    }

    //GET EMAIL ******
    @Test
    void givenTheEmailOfAUserItIsRetrievedFromTheDB() throws ResourceNotFoundException, UserValidationException {
        //   userRepo.deleteAll();
        prueUser1.setPassword(encoder.encode("3546DelpoWinner"));
        prueUser2.setPassword(encoder.encode("123NadalChampion"));
        prueUser3.setPassword(encoder.encode("321Martin"));
        userRepo.save(prueUser1);
        userRepo.save(prueUser2);
        userRepo.save(prueUser3);

        UserViewDTO newUser = userService.findByEmail("federer@yahoo.com");

        assertEquals("Roger", newUser.getName());
    }

    //EXISTS EMAIL EXCEPTION *********
    @Test
    void checkIfAnEmailIsInTheDatabaseAndCanNotFindIt() throws UserValidationException {
        // userRepo.deleteAll();
        prueUser1.setPassword(encoder.encode("3546DelpoWinner"));
        userRepo.save(prueUser1);
        User newUser = new User();
        newUser.setEmail("milonina@gmail.com");

        assertThrows(ResourceNotFoundException.class, () -> userService.findByEmail(newUser.getEmail()));
    }

    //POST  -  ADD NEW USER ************
    @Test
    void checkIfAnEmailIsInTheDatabaseAndCanNotFindItCreatingTheUser() throws EmailAlreadyExistsException, UserValidationException {
        //   userRepo.deleteAll();
        prueUser1.setPassword(encoder.encode("3546DelpoWinner"));
        prueUser2.setPassword(encoder.encode("123NadalChampion"));
        userRepo.save(prueUser1);
        userRepo.save(prueUser2);

        userService.checkNewUserEmail(prueUser3.getEmail());

        List<UserViewDTO> users = userService.getAllUsers();

        assertEquals(2, users.toArray().length);
    }

    @Test
    void checkIfAnUserIsInTheDatabaseAndCanNotFindIt() {

        assertThrows(ResourceNotFoundException.class, () -> userService.getFromDataBase(1));
    }

    @Test
    void numberOfUserAreZeroWhenGetAllUsersFromATableWhereWasAllDeleted() {
        userService.deleteAllUsers();

        assertEquals(0, userService.getAllUsers().size());
    }

    @Test
    void updateAnUserAnCheckPersistence() throws UserValidationException, ResourceNotFoundException {
        prueUser1.setPassword(encoder.encode("3546DelpoWinner"));
        int idUSer1 = userRepo.save(prueUser1).getId();
        User userRecov = userRepo.findById(idUSer1).get();
        userRecov.setEmail("rogerFederer@gmail.com");
        userService.update(userRecov);

        assertEquals("rogerFederer@gmail.com", userService.findById(idUSer1).getEmail());
    }

    @Test
    void checkIfAnEmailIsInTheDatabaseAndThrowsAException() throws UserValidationException {
        prueUser1.setPassword(encoder.encode("3546DelpoWinner"));
        userRepo.save(prueUser1);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.checkNewUserEmail(prueUser1.getEmail()));
    }
}