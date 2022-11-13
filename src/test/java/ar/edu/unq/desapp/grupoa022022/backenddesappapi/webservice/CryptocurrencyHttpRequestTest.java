package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

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

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    @Order(1)
    void gettingCryptocurrenciesShouldReturnAListWhit16Cryptocurrencies() {

        ResponseEntity<Cryptocurrency[]> result = restTemplate.getForEntity(TEST_HOSTNAME + port + "/cryptocurrencies",
                Cryptocurrency[].class);
        System.out.println(result);
        Assertions.assertEquals(16, Arrays.stream(Objects.requireNonNull(result.getBody())).toList().size());
    }

    @Test
    @Order(6)
    void postingACryptocurrencyNamedUSDTShouldIt() {

        CryptocurrencyRegister cryptocurrencyRegister = new CryptocurrencyRegister("USDT", 152.50);

        ResponseEntity<Cryptocurrency> result;
        try {
            result = restTemplate.exchange(TEST_HOSTNAME + port + "/cryptocurrencies",
                    HttpMethod.POST, new HttpEntity<>(testController.getBody(cryptocurrencyRegister), testController.getHeaders()), Cryptocurrency.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals("USDT", Objects.requireNonNull(Objects.requireNonNull(result.getBody())).getName());
    }
}