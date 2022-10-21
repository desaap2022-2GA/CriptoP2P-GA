package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuoteHttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private QuoteController controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
/*
    @Test
    public void gettingCryptocurrenciesShouldReturnAListThatIncludesDAI() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/cryptocurrencies",
                String.class)).contains("DAI");
    }*/
}
