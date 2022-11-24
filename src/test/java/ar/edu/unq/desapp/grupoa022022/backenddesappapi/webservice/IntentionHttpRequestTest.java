package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionViewDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.TokenDTO;
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
class IntentionHttpRequestTest {

    @Value("${local.server.port}")
    private int port;
    @Value("${test.hostname}")
    private String TEST_HOSTNAME;
    @Autowired
    private IntentionController controller;
    @Autowired
    private TestRestTemplate restTemplate;
    private final TestController testController = new TestController();

    private HttpEntity<String> headersWithToken;

    @BeforeAll
    void init() {

        //SE CREA UN USUARIO Y SE OBTIENE UN TOKEN PARA REALIZAR LAS CONSULTAS
        HttpEntity<String> jwtEntity;
        try {
            jwtEntity = testController.getRegistrationEntityI();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ResponseEntity<String> registrationResponse = restTemplate.exchange(TEST_HOSTNAME + port + "/auth", HttpMethod.POST,
                jwtEntity, String.class);

        HttpEntity<String> authenticationEntity = null;
        if (registrationResponse.getStatusCode().equals(HttpStatus.OK)) {
            try {
                authenticationEntity = testController.getAuthenticationEntityI();
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
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    @Order(1)
    void gettingIntentionsShouldReturnAListWith3Intentions() {
        ResponseEntity<IntentionViewDTO[]> result = restTemplate.exchange(TEST_HOSTNAME + port + "/intentions",
                HttpMethod.GET, headersWithToken, IntentionViewDTO[].class);

        Assertions.assertEquals(3, Arrays.stream(Objects.requireNonNull(result.getBody())).toList().size());
    }

    @Test
    void gettingIntention1ShouldReturnAnIntentionWith5326807_85Price() {
        ResponseEntity<IntentionViewDTO> result = restTemplate.exchange(TEST_HOSTNAME + port + "/intentions/2",
                HttpMethod.GET, headersWithToken, IntentionViewDTO.class);

        Assertions.assertEquals("5326807.85", Objects.requireNonNull(result.getBody()).getPrice());
    }

    @Test
    @Order(2)
    void gettingActiveIntentionShouldReturn2() {
        ResponseEntity<IntentionViewDTO[]> result = restTemplate.exchange(TEST_HOSTNAME + port + "/intentions/active",
                HttpMethod.GET, headersWithToken, IntentionViewDTO[].class);

        Assertions.assertEquals(2, Objects.requireNonNull(Objects.requireNonNull(result.getBody())).length);
    }
/*
    @Test
    @Order(9)
    void postingAnIntentionWithPrice320_00ShouldReturnIt() {
        IntentionRegister intentionRegister = new IntentionRegister(IntentionType.BUY, 1, 320.00, 2, 1);
        ResponseEntity<IntentionView> result;
        try {
            result = restTemplate.exchange(TEST_HOSTNAME + port + "/intentions",
                    HttpMethod.POST, new HttpEntity<>(testController.getBody(intentionRegister), headersWithToken.getHeaders())
                    , IntentionView.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(320.0, Objects.requireNonNull(result.getBody()).getPrice());
    }*/
}