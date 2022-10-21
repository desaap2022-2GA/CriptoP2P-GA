package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntentionHttpRequestTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private IntentionController controller;

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