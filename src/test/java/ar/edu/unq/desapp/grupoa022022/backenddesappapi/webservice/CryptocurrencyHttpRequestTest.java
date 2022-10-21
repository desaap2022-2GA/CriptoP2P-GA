package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyRegister;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CryptocurrencyHttpRequestTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private CryptocurrencyController controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void gettingCryptocurrenciesShouldReturnAListThatIncludesDAI() throws Exception {

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/cryptocurrencies",
                String.class)).contains("DAI");
    }

    @Test
    public void postingACryptocurrencyNamedBITCOINShouldReturnAListThatIncludesIt() throws Exception {

        CryptocurrencyRegister cryptocurrencyRegister = new CryptocurrencyRegister("USDT", 152.50);

        assertThat(this.restTemplate.postForEntity("http://localhost:" + port + "/cryptocurrencies",
                cryptocurrencyRegister, CryptocurrencyRegister.class)).toString().contains("DAI");
    }
}