package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.OperationState;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OperationHttpRequestTest {

    @Value("${local.server.port}")
    private int port;
    @Value("${test.hostname}")
    private String TEST_HOSTNAME;
    @Autowired
    private OperationController controller;
    @Autowired
    private TestRestTemplate restTemplate;
    private final TestController testController = new TestController();

    private HttpEntity<String> headersWithToken;

    @BeforeAll
    void init() {

        //SE CREA UN USUARIO Y SE OBTIENE UN TOKEN PARA REALIZAR LAS CONSULTAS
        HttpEntity<String> jwtEntity;
        try {
            jwtEntity = testController.getRegistrationEntityO();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ResponseEntity<String> registrationResponse = restTemplate.exchange(TEST_HOSTNAME + port + "/users", HttpMethod.POST,
                jwtEntity, String.class);

        HttpEntity<String> authenticationEntity = null;
        if (registrationResponse.getStatusCode().equals(HttpStatus.OK)) {
            try {
                authenticationEntity = testController.getAuthenticationEntityO();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        ResponseEntity<TokenDTO> authenticationResponse = restTemplate.exchange(TEST_HOSTNAME + port + "/users/login",
                HttpMethod.POST, authenticationEntity, TokenDTO.class);

        if (authenticationResponse.getStatusCode().equals(HttpStatus.OK)) {
            String token = "Bearer " + authenticationResponse.getBody().getToken();
            HttpHeaders headers = testController.getHeaders();
            headers.set("Authorization", token);
            headersWithToken = new HttpEntity<>(headers);
        }
    }

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    @Order(1)
    void askingForOperation2WithUser1ShouldReturnAnOperationWithUserWhoPostNamePastonGaudio() {
        ResponseEntity<OperationView> result = restTemplate.exchange(TEST_HOSTNAME + port + "/operations/2/1",
                HttpMethod.GET, headersWithToken, OperationView.class);

        Assertions.assertEquals("Paston Gaudio", Objects.requireNonNull(result.getBody()).getUserWhoPostCompleteName());
    }

    @Test
    @Order(2)
    void puttingOperationWithID1OperationStateCancelledUserID2() {
        OperationModify operationModify = new OperationModify(1, OperationState.CANCELLED,2);

        ResponseEntity<Void> result = restTemplate.exchange(TEST_HOSTNAME + port + "/operations/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(operationModify, headersWithToken.getHeaders()),
                Void.class, 1);

        Assertions.assertEquals(200, result.getStatusCode().value());
    }

    @Test
    @Order(3)
    void postingAnOperationWithIntentionThatExceedConditionsPriceObtainBadRequest() throws Exception {
        OperationRegister operationRegister = new OperationRegister(2,1);

        ResponseEntity<OperationView> result = restTemplate.exchange(TEST_HOSTNAME + port + "/operations",
                HttpMethod.POST, new HttpEntity<>(testController.getBody(operationRegister), headersWithToken.getHeaders())
                , OperationView.class);

        System.out.println("result = " + result);

        Assertions.assertEquals(400, result.getStatusCode().value());
    }

    @Test
    @Order(4)
    void postingAnOperationWithIntentionID2AndUserID1ShouldReturnIt() throws Exception {

        OperationRegister operationRegister = new OperationRegister(3,1);

        ResponseEntity<OperationView> result = restTemplate.exchange(TEST_HOSTNAME + port + "/operations",
                HttpMethod.POST, new HttpEntity<>(testController.getBody(operationRegister), headersWithToken.getHeaders())
                , OperationView.class);

        System.out.println("result = " + result);

        Assertions.assertEquals("BITCOIN", Objects.requireNonNull(result.getBody()).getCryptocurrency());
    }
}