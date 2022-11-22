package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.QuoteRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.TokenDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
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
class QuoteHttpRequestTest {

    @Value("${local.server.port}")
    private int port;
    @Value("${test.hostname}")
    private String TEST_HOSTNAME;
    @Autowired
    private QuoteController controller;
    @Autowired
    private TestRestTemplate restTemplate;
    private final TestController testController = new TestController();

    private HttpEntity<String> headersWithToken;

    @BeforeAll
    void init() {

        //SE CREA UN USUARIO Y SE OBTIENE UN TOKEN PARA REALIZAR LAS CONSULTAS
        HttpEntity<String> jwtEntity;
        try {
            jwtEntity = testController.getRegistrationEntityQ();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ResponseEntity<String> registrationResponse = restTemplate.exchange(TEST_HOSTNAME + port + "/auth", HttpMethod.POST,
                jwtEntity, String.class);

        HttpEntity<String> authenticationEntity = null;
        if (registrationResponse.getStatusCode().equals(HttpStatus.OK)) {
            try {
                authenticationEntity = testController.getAuthenticationEntityQ();
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
/*
    @Test
    @Order(1)
    void gettingQuotesShouldReturnAListWithLength() {
        ResponseEntity<Quote[]> result = restTemplate.exchange(TEST_HOSTNAME + port + "/quotes",
                HttpMethod.GET, headersWithToken, Quote[].class);
        System.out.println("quotesGetAll" + Arrays.stream(result.getBody()).map(Quote::getPrice).toList());
        Assertions.assertEquals(18, Objects.requireNonNull(result.getBody()).length);
    }
*/
    @Test
    void gettingQuotes1ShouldReturnAQuoteWith32038Price() {
        ResponseEntity<Quote> result = restTemplate.exchange(TEST_HOSTNAME + port + "/quotes/1",
                HttpMethod.GET, headersWithToken,  Quote.class);

        Assertions.assertEquals(320.38, Objects.requireNonNull(Objects.requireNonNull(result.getBody()).getPrice()));
    }

    @Test
    @Order(7)
    void postingAQuoteNamedBITCOINShouldReturnAListThatIncludesIt() {
        QuoteRegister quoteRegister = new QuoteRegister(1, 152.50);
        ResponseEntity<Quote> result;
        try {
            result = restTemplate.exchange(TEST_HOSTNAME + port + "/quotes",
                    HttpMethod.POST, new HttpEntity<>(testController.getBody(quoteRegister), headersWithToken.getHeaders()), Quote.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(152.50, Objects.requireNonNull(result.getBody()).getPrice());
    }
}