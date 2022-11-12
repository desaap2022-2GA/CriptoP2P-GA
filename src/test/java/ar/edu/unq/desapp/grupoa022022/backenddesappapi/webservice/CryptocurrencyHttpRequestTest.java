package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CryptocurrencyHttpRequestTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private CryptocurrencyController controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void gettingCryptocurrenciesShouldReturnAListThatIncludesDAI() {

        ResponseEntity<Cryptocurrency[]> result = restTemplate.getForEntity("http://localhost:" + port + "/cryptocurrencies",
                Cryptocurrency[].class);
        System.out.println(result);
        assertTrue(Arrays.toString(Objects.requireNonNull(result.getBody())).contains("DAI"));
    }

    @Test
    void postingACryptocurrencyNamedBITCOINShouldReturnAListThatIncludesIt() {

        CryptocurrencyRegister cryptocurrencyRegister = new CryptocurrencyRegister("USDT", 152.50);

        assertThat(this.restTemplate.postForEntity("http://localhost:" + port + "/cryptocurrencies",
                cryptocurrencyRegister, CryptocurrencyRegister.class)).toString().contains("DAI");
    }

    @Test
    void postingAnCryptocurrencyWithNameBitcoinsShouldReturnIt() throws Exception {
        CryptocurrencyRegister cryptocurrencyRegister = new CryptocurrencyRegister("Bitcoins", 152.50);

        ResponseEntity<String> result = this.restTemplate.postForEntity("http://localhost:" + port + "/cryptocurrencies",
                cryptocurrencyRegister, String.class);

        System.out.println("result = " + result);

        assertTrue(result.getBody().contains("Bitcoins"));
    }
}