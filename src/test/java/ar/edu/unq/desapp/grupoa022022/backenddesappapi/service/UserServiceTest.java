package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserView;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ExceptionsUser;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IUserRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@RunWith(SpringRunner.class)
//@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest {

    DataSet dataSet = new DataSet();

    @Mock
    private IUserRepo userRepo;
    
    //@InjectMocks
    @Autowired
    private UserService userService;
    
    //private User user;
    public User mockUser = Mockito.mock(User.class);
    public UserRegister mockUserRegister = Mockito.mock(UserRegister.class);

    //private UserRegister regUser;

    private Set<Intention> intentions = new HashSet<>();
    
    @BeforeEach
        public void init() throws ResourceNotFound {
            Mockito.when(mockUser.getId()).thenReturn(1);
            Mockito.when(mockUser.getEmail()).thenReturn("gaudio@yahoo.com");
            Mockito.when(mockUserRegister.getName()).thenReturn("Paston");
            Mockito.when(mockUserRegister.getLastname()).thenReturn("Gaudio");
            Mockito.when(mockUserRegister.getEmail()).thenReturn("gaudio@yahoo.com");
            Mockito.when(mockUserRegister.getAddress()).thenReturn("Av Libertador 5000, CABA");
            Mockito.when(mockUserRegister.getPassword()).thenReturn("1111");
            Mockito.when(mockUserRegister.getMercadoPagoCVU()).thenReturn("6352879863528798635287");
            Mockito.when(mockUserRegister.getAddressWalletActiveCrypto()).thenReturn("Xwf5u5ef");


    }

    @DisplayName("JUnit test create method")
    @Test
    public void createAUserTest(){
        System.out.println("------------------- aca pasé 1");
        UserRegister userRegister = new UserRegister(mockUserRegister.getName(), mockUserRegister.getLastname(),
                mockUserRegister.getEmail(), mockUserRegister.getAddress(), mockUserRegister.getPassword(),
                mockUserRegister.getMercadoPagoCVU(), mockUserRegister.getAddressWalletActiveCrypto());
        UserView userMock = userService.create(userRegister);
        System.out.println("------------------- aca pasé 2");

        String userEmail = userMock.getEmail();
        System.out.println("------------------- aca pasé 3");

        assertEquals(userEmail, userService.create(dataSet.getUserRegister()).getEmail());
    }

    @DisplayName("JUnit test getAllUsers method")
    @Test
    public void getAllUsersTest() {
        List users = new ArrayList<>();
        users.add(dataSet.getUserRegister());
        users.add(dataSet.getUserRegister2());

        assertEquals(userService.getAllUsers().size(), 2);
    }

    @DisplayName("JUnit test findById method")
    @Test
    public void getUserByIdTest() throws ResourceNotFound {
        String emailMock = mockUser.getEmail();

        assertEquals(emailMock, userService.findById(1).getEmail());
    }

    @DisplayName("JUnit test findById method with exception")
    @Test
    public void getUserById_WithException_Test(){
        assertThrows(ExceptionsUser.class, () -> {
            userService.findById(6);
        });
    }

    @DisplayName("JUnit test findByEmail method")
    @Test
    public void findByEmailTest() throws ResourceNotFound {
        String emailMock = mockUser.getEmail();

        assertEquals(emailMock, userService.findByEmail("gaudio@yahoo.com").getEmail());
    }

    @DisplayName("JUnit test findByPassword")
    @Test
    public void findByPasswordTest() throws ResourceNotFound {
        String emailMock = mockUser.getEmail();

        assertEquals(emailMock, userService.findByPassword("1111").getEmail());
    }

















    /*public void setup(){

      UserRegister userR = UserRegister.builder()
                .password("Fierro11")
                .name("Martin")
                .lastname("Fierro")
                .address("Libertad 111")
                .email("martin@firrro.com")
                .addressWalletActiveCrypto("1254XXX")
                .mercadoPagoCVU("1234567890123456789012")
                .password("Fierro11")
                .build();

        UserRegister regUser = UserRegister.builder()
                .password("Fierro111")
                .build();


        UserRegister regUser = new UserRegister("Martin", "Fierro", "martin@fierro.com",
                "Libertad 111", "Fierro111", "1234567890123456789012",
                "1254XXX");




        user = User.builder()
                .id(1)
                .name("Martin")
                .lastname("Fierro")
                .address("Libertad 111")
                .email("martin@firrro.com")
                .addressWalletActiveCrypto("1254XXX")
                .mercadoPagoCVU("1234567890123456789012")
                .password("Fierro11")
                .points(3)
                .numberOperations(1)
                .intentions(intentions)
                .build();
    }

    
    @DisplayName("JUnit test for create a user")
    @Test
    public void createAnUser(){
        given(userRepo.findByEmail(regUser.getEmail())).willReturn(Optional.empty());

        given(userRepo.save(user)).willReturn(user);

        UserView userCreate = userService.create(regUser);

        assertThat(userCreate).isNotNull();
    }

       */



/*

    @Autowired
    private IUserService userService;

    public User mockUser = Mockito.mock(User.class);

    @BeforeEach
    public void init() throws ResourceNotFound {
        Mockito.when(mockUser.getId()).thenReturn(1);
        Mockito.when(mockUser.getName()).thenReturn("Martin");
        Mockito.when(mockUser.getLastname()).thenReturn("Fierro");
        Mockito.when(mockUser.getAddress()).thenReturn("Libertador 500");
        Mockito.when(mockUser.getEmail()).thenReturn("martin@fierro.com");
        Mockito.when(mockUser.getMercadoPagoCVU()).thenReturn("12345678");
        Mockito.when(mockUser.getaddressWalletActiveCrypto()).thenReturn("111111");
    }



    @Test
    public void testGetAllUsers(){
        UserView user = getUser();
        List<UserView> users = new ArrayList<>();
        users.add(user);
        List<UserView> result = userService.getAllUsers();

        assertEquals(result.size(), 2);
    }

    @Test
    public void testFindUserById() throws ResourceNotFound {
        UserView user = getUser();
        List<UserView> users = new ArrayList<>();
        users.add(user);
        int id = mockUser.getId();
        userService.findById(id);

        assertEquals(userService.findById(mockUser.getId()), 1);

        /*assertThrows(ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound.class, () -> {
            UserView user = getUser();
            List<UserView> users = new ArrayList<>();
            users.add(user);
            userService.delete(mockUser.getId());

            userService.findById(mockUser.getId());
        });*/
 /*   }

    private UserView getUser() {
        UserView user = new UserView(1, "Martin", "Fierro", "fierro@gmail.com",
                "Av Cordoba 3000, CABA", "1111", "6352879863528798635287",
                10, 1, 1);
        return user;
    }

*/
}
