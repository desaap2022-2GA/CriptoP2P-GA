package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OperationHttpRequestTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private OperationController controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
    @Test
    void gettingOperationsShouldReturnAListThatIncludesOneWithNameMartin() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/operations",
                String.class)).contains("Martin");
    }

    @Test
    void gettingOperation1ShouldReturnAnOperationWithUserWhoPostNameMartin() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/operations/1",
                String.class)).contains("Martin");
    }

    @Test
    void postingAnOperationWithPrice333_33ShouldReturnAListThatIncludesIt() throws Exception {

        IntentionRegister intentionRegister = new IntentionRegister(IntentionType.BUY,1,333.33,2,1);

        assertThat(this.restTemplate.postForEntity("http://localhost:" + port + "/operations",
                intentionRegister, IntentionRegister.class)).toString().contains("333.33");
    }
}
