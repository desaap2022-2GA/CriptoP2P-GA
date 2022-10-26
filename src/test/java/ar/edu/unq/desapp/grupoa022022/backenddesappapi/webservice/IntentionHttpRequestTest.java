package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntentionHttpRequestTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private IntentionController controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void gettingIntentionsShouldReturnAListThatIncludesOneWith289_75Price() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/intentions",
                String.class)).contains("289.75");
    }

    @Test
    void gettingIntention1ShouldReturnAnIntentionWith5326807_85Price() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/intentions/2",
                String.class)).contains("5326807.85");
    }

    @Test
    void gettingActiveIntentionShouldReturnAnIntentionWith5326807_85Price() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/intentions/active",
                String.class)).contains("5326807.85");
    }

    @Test
    void postingAnIntentionWithPrice320_00ShouldReturnIt() throws Exception {
        IntentionRegister intentionRegister = new IntentionRegister(IntentionType.BUY, 1, 320.00, 2, 1);

        ResponseEntity<String> result = this.restTemplate.postForEntity("http://localhost:" + port + "/intentions",
                intentionRegister, String.class);

        assertTrue(result.getBody().contains("320.0"));
    }
}
