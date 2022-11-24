package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
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
    private String testHostname;
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

        ResponseEntity<String> registrationResponse = restTemplate.exchange(testHostname + port + "/auth", HttpMethod.POST,
                jwtEntity, String.class);
        HttpEntity<String> authenticationEntity = null;
        if (registrationResponse.getStatusCode().equals(HttpStatus.OK)) {
            try {
                authenticationEntity = testController.getAuthenticationEntity();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        ResponseEntity<TokenDTO> authenticationResponse = restTemplate.exchange(testHostname + port + "/auth/login",
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
        ResponseEntity<UserViewDTO[]> result = restTemplate.exchange(testHostname + port + "/users",
                HttpMethod.GET, headersWithToken, UserViewDTO[].class);

        System.out.println(Arrays.stream(result.getBody()).map(userView -> userView.getId()).toString());
        Assertions.assertTrue(2 < Objects.requireNonNull(result.getBody()).length);
    }

    @Test
    @Order(3)
    void gettingUser1ShouldReturnAnUserWithLastnameGaudio() {
        ResponseEntity<UserViewDTO> result = restTemplate.exchange(testHostname + port + "/users/1",
                HttpMethod.GET, headersWithToken, UserViewDTO.class);

        Assertions.assertTrue(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(result.getBody())).getLastname()).contains("Gaudio"));
    }

    @Test
    @Order(4)
    void gettingUserWithEmailGaudioYahooShouldReturnAnUserWithLastnameGaudio() {
        ResponseEntity<UserViewDTO> result = restTemplate.exchange(testHostname + port + "/users/email/gaudio@yahoo.com",
                HttpMethod.GET, headersWithToken, UserViewDTO.class);

        Assertions.assertTrue(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(result.getBody())).getLastname()).contains("Gaudio"));
    }
/*
    @Test
    @Order(8)
    void gettingUserOperationBetweenDatesReturnATradedBetweenDates() {
        ResponseEntity<TradedBetweenDates> result = restTemplate.exchange("http://localhost:" + port + "/users/traded/{id}/{firstdate}/{seconddate}",
                HttpMethod.GET, headersWithToken, TradedBetweenDates.class,1,"1569217259171","1769217259171");
        System.out.println(result.getBody().toString());
        Assertions.assertEquals(579.5, Objects.requireNonNull(result.getBody()).getPesosAmount());
    }
*/
    @Test
    @Order(6)
    void postingAnUserWithEmail_federer_gmail_com_ShouldReturnIt() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("Roger", "Federer", "federer@gmail.com"
                , "Av Libertador 5000", "unoDo$", "1236549877412589632145", "Zs59f4lo");

        ResponseEntity<UserViewDTO> result;
        try {
            result = restTemplate.exchange(testHostname + port + "/auth",
                    HttpMethod.POST, new HttpEntity<>(testController.getBody(userRegisterDTO), headersWithToken.getHeaders()), UserViewDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals("federer@gmail.com", Objects.requireNonNull(Objects.requireNonNull(result.getBody())).getEmail());
    }

    @Test
    @Order(7)
    void puttingUser1WithAddressHusaresShouldReturnThatChange() {

        ResponseEntity<UserViewDTO> result = restTemplate.exchange(testHostname + port + "/users/{id},{field},{data}",
                HttpMethod.PUT,
                new HttpEntity<>(null, headersWithToken.getHeaders()),
                UserViewDTO.class, 2,"address","Husares 5000");

        Assertions.assertEquals(200, result.getStatusCode().value());
        Assertions.assertEquals("Husares 5000", Objects.requireNonNull(Objects.requireNonNull(result.getBody())).getAddress());
    }
}