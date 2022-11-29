package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyRegisterDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.TokenDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CryptocurrencyHttpRequestTest {

    @Value("${local.server.port}")
    private int port;
    @Value("${test.hostname}")
    private String TEST_HOSTNAME;
    @Autowired
    private CryptocurrencyController controller;
    @Autowired
    private TestRestTemplate restTemplate;
    private final TestController testController = new TestController();

    private HttpEntity<String> headersWithToken;

    @BeforeAll
    void init() {

        //SE CREA UN USUARIO Y SE OBTIENE UN TOKEN PARA REALIZAR LAS CONSULTAS
        HttpEntity<String> jwtEntity;
        try {
            jwtEntity = testController.getRegistrationEntityC();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ResponseEntity<String> registrationResponse = restTemplate.exchange(TEST_HOSTNAME + port + "/auth", HttpMethod.POST,
                jwtEntity, String.class);

        HttpEntity<String> authenticationEntity = null;
        if (registrationResponse.getStatusCode().equals(HttpStatus.OK)) {
            try {
                authenticationEntity = testController.getAuthenticationEntityC();
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
    void gettingCryptocurrenciesShouldReturnAListWhit16Cryptocurrencies() {

        ResponseEntity<Cryptocurrency[]> result = restTemplate.exchange(TEST_HOSTNAME + port + "/cryptocurrencies",
                HttpMethod.GET, headersWithToken, Cryptocurrency[].class);
        System.out.println(result);
        Assertions.assertEquals(16, Arrays.stream(Objects.requireNonNull(result.getBody())).toList().size());
    }

    @Test
    @Order(6)
    void postingACryptocurrencyNamedUSDTShouldIt() {

        CryptocurrencyRegisterDTO cryptocurrencyRegisterDTO = new CryptocurrencyRegisterDTO("USDT", 152.50);

        ResponseEntity<Cryptocurrency> result;
        try {
            result = restTemplate.exchange(TEST_HOSTNAME + port + "/cryptocurrencies",
                    HttpMethod.POST, new HttpEntity<>(testController.getBody(cryptocurrencyRegisterDTO), headersWithToken.getHeaders()), Cryptocurrency.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals("USDT", Objects.requireNonNull(Objects.requireNonNull(result.getBody())).getName());
    }
}