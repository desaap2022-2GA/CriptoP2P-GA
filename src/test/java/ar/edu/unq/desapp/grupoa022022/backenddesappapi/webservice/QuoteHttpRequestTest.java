package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.QuoteRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuoteHttpRequestTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private QuoteController controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void gettingQuotesShouldReturnAListThatIncludesOneWith560716615Price() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/quotes",
                String.class)).contains("5607166.15");
    }

    @Test
    public void gettingQuotes1ShouldReturnAQuoteWith32038Price() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/quotes/1",
                String.class)).contains("320.38");
    }

    @Test
    public void postingAQuoteNamedBITCOINShouldReturnAListThatIncludesIt() throws Exception {

        QuoteRegister quoteRegister = new QuoteRegister(1, 152.50);

        assertThat(this.restTemplate.postForEntity("http://localhost:" + port + "/quotes",
                quoteRegister, QuoteRegister.class)).toString().contains("152.50");
    }
}
