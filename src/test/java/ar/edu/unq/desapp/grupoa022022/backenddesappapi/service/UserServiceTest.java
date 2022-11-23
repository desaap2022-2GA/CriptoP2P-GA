package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserQuery;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserView;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.EmailAlreadyExists;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ExceptionsUser;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Order(0)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    public User mockUser = Mockito.mock(User.class);
    public UserRegister mockUserRegister = Mockito.mock(UserRegister.class);
    public UserRegister mockUserRegister1 = Mockito.mock(UserRegister.class);

    public UserRegister mockUserRegister2 = Mockito.mock(UserRegister.class);

    private Set<Intention> intentions = new HashSet<>();
    
    @BeforeEach
        public void init() throws ResourceNotFound {
            Mockito.when(mockUser.getId()).thenReturn(1);
            Mockito.when(mockUser.getEmail()).thenReturn("gaudio@yahoo.com");
            //Mockito.when(mockUser.operationsBetweenDates(2000000, 2000010)).thenReturn([1,2]);

            Mockito.when(mockUserRegister.getName()).thenReturn("Paston");
            Mockito.when(mockUserRegister.getLastname()).thenReturn("Gaudio");
            Mockito.when(mockUserRegister.getEmail()).thenReturn("gaudio@yahoo.com");
            Mockito.when(mockUserRegister.getAddress()).thenReturn("Av Libertador 5000, CABA");
            Mockito.when(mockUserRegister.getPassword()).thenReturn("1111");
            Mockito.when(mockUserRegister.getMercadoPagoCVU()).thenReturn("6352879863528798635287");
            Mockito.when(mockUserRegister.getAddressWalletActiveCrypto()).thenReturn("Xwf5u5ef");

            Mockito.when(mockUserRegister1.getName()).thenReturn("Paston");
            Mockito.when(mockUserRegister1.getLastname()).thenReturn("Gaudio");
            Mockito.when(mockUserRegister1.getEmail()).thenReturn("gaudioPaston@yahoo.com");
            Mockito.when(mockUserRegister1.getAddress()).thenReturn("Av Libertador 5000, CABA");
            Mockito.when(mockUserRegister1.getPassword()).thenReturn("1111");
            Mockito.when(mockUserRegister1.getMercadoPagoCVU()).thenReturn("6352879863528798635287");
            Mockito.when(mockUserRegister1.getAddressWalletActiveCrypto()).thenReturn("Xwf5u5ef");

            Mockito.when(mockUserRegister2.getEmail()).thenReturn("gaudio@gmail.com");
    }

    @DisplayName("JUnit test create method in UserService")
    @Test
    @Order(0)
    void createAUserTest(){
        UserRegister userRegister = new UserRegister("sarasa","sarasaaa",
                "sarasa@sarasa.com", "sasasasas 12", "Sasasa22",
                "1234567890123456789012", "12345678");
        System.out.println("userReg creado mail: " + userRegister.getEmail());
        UserView userMock = userService.create(userRegister);
        System.out.println("userMock creado mail: " + userMock.getEmail());

        assertEquals(userMock.getEmail(), "sarasa@sarasa.com");
    }



    @Order(1)
    @DisplayName("JUnit test getAllUsers method in UserService")
    @Test
    void getAllUsersTest() {
        assertEquals(userService.getAllUsers().size(), 2);
    }

    @DisplayName("JUnit test findById method in UserService")
    @Test
    void findUserByIdTest() throws ResourceNotFound {
        String emailMock = mockUser.getEmail();

        assertEquals(emailMock, userService.findById(1).getEmail());
    }

    @DisplayName("JUnit test findById method with exception in UserService")
    @Test
    void findUserById_WithException_Test(){
        assertThrows(ResourceNotFound.class, () -> {
            userService.findById(6);
        });
    }

    @DisplayName("JUnit test findByEmail method in UserService")
    @Test
    void findByEmailTest() throws ResourceNotFound {
        String emailMock = mockUser.getEmail();

        assertEquals(emailMock, userService.findByEmail("gaudio@yahoo.com").getEmail());
    }

    @DisplayName("JUnit test findByEmail method in UserService")
    @Test
    void findByEmail_With_Exception_Test() {
        assertThrows(ResourceNotFound.class, () -> {
            userService.findByEmail("g@yahoo.com");
        });
    }

    @DisplayName("JUnit test findUserByEmail method in UserService")
    @Test
    void findUserByEmail() throws ResourceNotFound {
        String emailMock = mockUser.getEmail();

        assertEquals(emailMock, userService.findByEmail("gaudio@yahoo.com").getEmail());
    }

    @DisplayName("JUnit test findByEmail method with exception in UserService")
    @Test
    void findByEmail_WithException_Test(){
        assertThrows(ResourceNotFound.class, () -> {
            userService.findByEmail("gauisio@yahoo.com");
        });
    }

    @DisplayName("JUnit test checkNewUserMail method in UserService")
    @Test
    void checkNewUserMailTest() throws ResourceNotFound {
        String emailMock = mockUser.getEmail();

        assertTrue(userService.findUserByEmail(emailMock).isPresent());
    }

    @DisplayName("JUnit test checkNewUserEmail method with exception in UserService")
    @Test
    void checkNewUserEmail_WithException_Test(){
        assertThrows(EmailAlreadyExists.class, () -> {
            userService.checkNewUserEmail("gaudio@yahoo.com");
        });
    }

    @DisplayName("JUnit saveToDataBase method in UserService")
    @Test
    void saveToDataBaseTest(){
        UserRegister userRegister = new UserRegister(mockUserRegister1.getName(), mockUserRegister1.getLastname(),
                mockUserRegister1.getEmail(), mockUserRegister1.getAddress(), mockUserRegister1.getPassword(),
                mockUserRegister1.getMercadoPagoCVU(), mockUserRegister1.getAddressWalletActiveCrypto());

        User user = userService.saveToDataBase(userRegister);

        assertEquals(user.getEmail(), "gaudioPaston@yahoo.com");
    }

    @DisplayName("JUnit getFromDataBase method in UserService")
    @Test
    void getFromDataBaseTest() throws ResourceNotFound {
        /*UserRegister userRegister = new UserRegister(mockUserRegister1.getName(), mockUserRegister1.getLastname(),
                mockUserRegister1.getEmail(), mockUserRegister1.getAddress(), mockUserRegister1.getPassword(),
                mockUserRegister1.getMercadoPagoCVU(), mockUserRegister1.getAddressWalletActiveCrypto());

        User user = userService.saveToDataBase(userRegister);

         */

        UserView user = userService.findById(1);

        assertEquals(userService.findUserByEmail(user.getEmail()).get().getEmail(), "gaudio@yahoo.com");
    }

    @DisplayName("JUnit getListUsers method in UserService")
    @Test
    void getListUsersTest(){
        List<UserQuery> users = userService.getListUsers();

        assertEquals(users.size(), 2);
    }

    @DisplayName("Junit modifyUser method in UserService")
    @Test
    void modifyUserTest() throws ResourceNotFound, ExceptionsUser {
        UserView user = userService.findById(mockUser.getId());
        User userModify = userService.modifyUser(user.getId(), "lastname", "Pastone");

        assertEquals(userModify.getLastname(), "Pastone");
    }

    @DisplayName("Junit modifyUser method with exception in UserService")
    @Test
    void modifyUser_With_Exception_Test() throws ResourceNotFound, ExceptionsUser {
        assertThrows(ResourceNotFound.class, () -> {
            UserView user = userService.findById(100);
            User userModify = userService.modifyUser(user.getId(), "lastname", "Pastone");
        });
    }




 /*   @DisplayName("JUnit test operationsBetweenDates")
    @Test
    public void operationsBetweenDatesTest(){
        User user = dataSet.getUserTest();
        long firstDate = 2000000;
        long secondDate = 2000010;

        Operation operation = new Operation();
        Set<Operation> operations = user.operationsBetweenDates(firstDate, secondDate);
    }
*/

}
