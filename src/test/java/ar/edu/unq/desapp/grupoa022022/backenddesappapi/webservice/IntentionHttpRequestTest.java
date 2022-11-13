package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionView;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
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
class IntentionHttpRequestTest {

    @Value("${local.server.port}")
    private int port;
    @Value("${test.hostname}")
    private String TEST_HOSTNAME;
    @Autowired
    private IntentionController controller;
    @Autowired
    private TestRestTemplate restTemplate;
    private final TestController testController = new TestController();

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    @Order(1)
    void gettingIntentionsShouldReturnAListWith3Intentions() {
        ResponseEntity<IntentionView[]> result = restTemplate.getForEntity(TEST_HOSTNAME + port + "/intentions",
                IntentionView[].class);

        Assertions.assertEquals(3, Arrays.stream(Objects.requireNonNull(result.getBody())).toList().size());
    }

    @Test
    void gettingIntention1ShouldReturnAnIntentionWith5326807_85Price() {
        ResponseEntity<IntentionView> result = restTemplate.getForEntity(TEST_HOSTNAME + port + "/intentions/2",
                IntentionView.class);

        Assertions.assertEquals(5326807.85, Objects.requireNonNull(result.getBody()).getPrice());
    }

    @Test
    @Order(2)
    void gettingActiveIntentionShouldReturn2() {
        ResponseEntity<IntentionView[]> result = restTemplate.getForEntity(TEST_HOSTNAME + port + "/intentions/active",
                IntentionView[].class);

        Assertions.assertEquals(2, Objects.requireNonNull(Objects.requireNonNull(result.getBody())).length);
    }
/*
    @Test
    @Order(9)
    void postingAnIntentionWithPrice320_00ShouldReturnIt() {
        IntentionRegister intentionRegister = new IntentionRegister(IntentionType.BUY, 1, 320.00, 2, 1);
        ResponseEntity<IntentionView> result;
        try {
            result = restTemplate.exchange(TEST_HOSTNAME + port + "/intentions",
                    HttpMethod.POST, new HttpEntity<>(testController.getBody(intentionRegister), testController.getHeaders())
                    , IntentionView.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(320.0, Objects.requireNonNull(result.getBody()).getPrice());
    }*/
}