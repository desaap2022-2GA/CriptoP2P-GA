package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.QuoteRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

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

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }
/*
    @Test
    @Order(1)
    void gettingQuotesShouldReturnAListWithLength() {
        ResponseEntity<Quote[]> result = restTemplate.getForEntity(TEST_HOSTNAME + port + "/quotes",
                Quote[].class);
        System.out.println("quotesGetAll" + Arrays.stream(result.getBody()).map(Quote::getPrice).toList());
        Assertions.assertEquals(18, Objects.requireNonNull(result.getBody()).length);
    }*/

    @Test
    void gettingQuotes1ShouldReturnAQuoteWith32038Price() {
        ResponseEntity<Quote> result = restTemplate.getForEntity(TEST_HOSTNAME + port + "/quotes/1"
                , Quote.class);

        Assertions.assertEquals(320.38, Objects.requireNonNull(Objects.requireNonNull(result.getBody()).getPrice()));
    }

    @Test
    @Order(7)
    void postingAQuoteNamedBITCOINShouldReturnAListThatIncludesIt() {
        QuoteRegister quoteRegister = new QuoteRegister(1, 152.50);
        ResponseEntity<Quote> result;
        try {
            result = restTemplate.exchange(TEST_HOSTNAME + port + "/quotes",
                    HttpMethod.POST, new HttpEntity<>(testController.getBody(quoteRegister), testController.getHeaders()), Quote.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(152.50, Objects.requireNonNull(result.getBody()).getPrice());
    }
}