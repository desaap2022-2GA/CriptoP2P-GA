package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserHttpRequestTest {

    @Value("${local.server.port}")
    private int port;
    @Value("${test.hostname}")
    private String TEST_HOSTNAME;
    @Autowired
    private UserController controller;
    @Autowired
    private TestRestTemplate restTemplate;
    private final TestController testController = new TestController();
    private HttpEntity<String> headersWithToken;

    @BeforeAll
    void init() {

        //SE CREA UN USUARIO Y SE OBTIENE UN TOKEN PARA REALIZAR LAS CONSULTAS
        HttpEntity<String> jwtEntity;
        try {
            jwtEntity = testController.getRegistrationEntity();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ResponseEntity<String> registrationResponse = restTemplate.exchange(TEST_HOSTNAME + port + "/auth", HttpMethod.POST,
                jwtEntity, String.class);
        HttpEntity<String> authenticationEntity = null;
        if (registrationResponse.getStatusCode().equals(HttpStatus.OK)) {
            try {
                authenticationEntity = testController.getAuthenticationEntity();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        ResponseEntity<TokenDTO> authenticationResponse = restTemplate.exchange(TEST_HOSTNAME + port + "/auth/login",
                HttpMethod.POST, authenticationEntity, TokenDTO.class);
        if (authenticationResponse.getStatusCode().equals(HttpStatus.OK)) {
            String token = "Bearer " + authenticationResponse.getBody().getToken();
            HttpHeaders headers = testController.getHeaders();
            headers.set("Authorization", token);
            headersWithToken = new HttpEntity<>(headers);
        }
    }

    @Test
    @Order(1)
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    @Order(2)
    void gettingUsersShouldReturnAListWithSizeGrater3() {
        ResponseEntity<UserView[]> result = restTemplate.exchange(TEST_HOSTNAME + port + "/users",
                HttpMethod.GET, headersWithToken, UserView[].class);

        System.out.println(Arrays.stream(result.getBody()).map(userView -> userView.getId()).toString());
        Assertions.assertTrue(2 < Objects.requireNonNull(result.getBody()).length);
    }

    @Test
    @Order(3)
    void gettingUser1ShouldReturnAnUserWithLastnameGaudio() {
        ResponseEntity<UserView> result = restTemplate.exchange(TEST_HOSTNAME + port + "/users/1",
                HttpMethod.GET, headersWithToken, UserView.class);

        Assertions.assertTrue(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(result.getBody())).getLastname()).contains("Gaudio"));
    }

    @Test
    @Order(4)
    void gettingUserWithEmailGaudioYahooShouldReturnAnUserWithLastnameGaudio() {
        ResponseEntity<UserView> result = restTemplate.exchange(TEST_HOSTNAME + port + "/users/email/gaudio@yahoo.com",
                HttpMethod.GET, headersWithToken, UserView.class);

        Assertions.assertTrue(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(result.getBody())).getLastname()).contains("Gaudio"));
    }
/*
    @Test
    @Order(1)
    void gettingUserOperationBetweenDatesReturnATradedBetweenDates() {
        ResponseEntity<TradedBetweenDates> result = restTemplate.exchange(TEST_HOSTNAME + port + "/users/operations-between-dates/1/10000000000/2000000000000",
                HttpMethod.GET, headersWithToken, TradedBetweenDates.class);
        System.out.println(result.getBody());
        Assertions.assertEquals(579.5, Objects.requireNonNull(result.getBody()).getPesosAmount());
    }*/

    @Test
    @Order(6)
    void postingAnUserWithEmail_federer_gmail_com_ShouldReturnIt() {
        UserRegister userRegister = new UserRegister("Roger", "Federer", "federer@gmail.com"
                , "Av Libertador 5000", "unoDo$", "1236549877412589632145", "Zs59f4lo");

        ResponseEntity<UserView> result;
        try {
            result = restTemplate.exchange(TEST_HOSTNAME + port + "/auth",
                    HttpMethod.POST, new HttpEntity<>(testController.getBody(userRegister), headersWithToken.getHeaders()), UserView.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals("federer@gmail.com", Objects.requireNonNull(Objects.requireNonNull(result.getBody())).getEmail());
    }

    @Test
    @Order(7)
    void puttingUser1WithAddressHusaresShouldReturnThatChange() {

        ResponseEntity<UserView> result = restTemplate.exchange(TEST_HOSTNAME + port + "/users//{id},{field},{data}",
                HttpMethod.PUT,
                new HttpEntity<>(null, headersWithToken.getHeaders()),
                UserView.class, 2,"address","Husares 5000");

        Assertions.assertEquals(200, result.getStatusCode().value());
        Assertions.assertEquals("Husares 5000", Objects.requireNonNull(Objects.requireNonNull(result.getBody())).getAddress());
    }
}