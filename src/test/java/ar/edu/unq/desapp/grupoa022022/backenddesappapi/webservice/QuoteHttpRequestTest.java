package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.QuoteRegister;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.AssertJUnit.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuoteHttpRequestTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private QuoteController controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void gettingQuotesShouldReturnAListThatIncludesOneWith560716615Price() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/quotes",
                String.class)).contains("5607166.15");
    }

    @Test
    void gettingQuotes1ShouldReturnAQuoteWith32038Price() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/quotes/1",
                String.class)).contains("320.38");
    }

    @Test
    void postingAQuoteNamedBITCOINShouldReturnAListThatIncludesIt() throws Exception {

        QuoteRegister quoteRegister = new QuoteRegister(1, 152.50);

        ResponseEntity<String> result = this.restTemplate.postForEntity("http://localhost:" + port + "/quotes",
                    quoteRegister, String.class);

            System.out.println("result = " + result);

            assertTrue(result.getBody().contains("152.5"));

    }
}
