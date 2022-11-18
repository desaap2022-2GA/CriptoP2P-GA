package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserView;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.EmailAlreadyExists;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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


@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    public User mockUser = Mockito.mock(User.class);
    public UserRegister mockUserRegister = Mockito.mock(UserRegister.class);

    public UserRegister mockUserRegister1 = Mockito.mock(UserRegister.class);

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
    }

    @DisplayName("JUnit test create method in UserService")
    @Test
    void createAUserTest(){
        UserRegister userRegister = new UserRegister(mockUserRegister1.getName(), mockUserRegister1.getLastname(),
                mockUserRegister1.getEmail(), mockUserRegister1.getAddress(), mockUserRegister1.getPassword(),
                mockUserRegister1.getMercadoPagoCVU(), mockUserRegister1.getAddressWalletActiveCrypto());

        UserView userMock = userService.create(userRegister);

        assertEquals(userMock.getEmail(), "gaudioPaston@yahoo.com");
    }

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

    @DisplayName("JUnit test findByEmail method with exception in UserService")
    @Test
    void findUserByEmail_WithException_Test(){
        assertThrows(ResourceNotFound.class, () -> {
            userService.findByEmail("gauisio@yahoo.com");
        });
    }

    @DisplayName("JUnit test checkNewUserEmail method with exception in UserService")
    @Test
    void checkNewUserEmail_WithException_Test(){
        assertThrows(EmailAlreadyExists.class, () -> {
            userService.checkNewUserEmail("gaudio@yahoo.com");
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
