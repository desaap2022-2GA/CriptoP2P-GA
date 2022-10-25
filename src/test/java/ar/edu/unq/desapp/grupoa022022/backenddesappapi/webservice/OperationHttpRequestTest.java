package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.OperationState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

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
    void puttingOperationWithID1OperationStateCancelledUserID2() throws Exception {
        OperationModify operationModify = new OperationModify(1, OperationState.CANCELLED,2);

        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/operations/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(operationModify, createJsonHeader()),
                Void.class, 1)).toString().contains("CANCELLED");
    }

    @Test
    void postingAnOperationWithIntentionID2AndUserID1ShouldReturnAListThatIncludesIt() throws Exception {
        OperationRegister operationRegister = new OperationRegister(2,1);

        assertThat(this.restTemplate.postForEntity("http://localhost:" + port + "/operations",
                operationRegister, OperationView.class)).toString().contains("ACTIVE");
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}