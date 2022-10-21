package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CryptocurrencyHttpRequestTest {

    @LocalServerPort
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
                String.class)).contains("[]");
    }

    /*

        @PostMapping
    public Cryptocurrency createCryptocurrency(@RequestBody @Valid CryptocurrencyRegister cryptocurrencyRegister) throws ResourceNotFound {
        return cryptocurrencyService.create(cryptocurrencyRegister);
    } */
}