package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.OperationState;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OperationHttpRequestTest {

    @Value("${local.server.port}")
    private int port;
    @Value("${test.hostname}")
    private String TEST_HOSTNAME;
    @Autowired
    private OperationController controller;
    @Autowired
    private TestRestTemplate restTemplate;
    private final TestController testController = new TestController();

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    @Order(1)
    void askingForOperation2WithUser1ShouldReturnAnOperationWithUserWhoPostNamePastonGaudio() {
        ResponseEntity<OperationView> result = restTemplate.getForEntity(TEST_HOSTNAME + port + "/operations/2/1",
                OperationView.class);

        Assertions.assertEquals("Paston Gaudio", Objects.requireNonNull(result.getBody()).getUserWhoPostCompleteName());
    }

    @Test
    @Order(2)
    void puttingOperationWithID1OperationStateCancelledUserID2() {
        OperationModify operationModify = new OperationModify(1, OperationState.CANCELLED,2);

        ResponseEntity<Void> result = restTemplate.exchange(TEST_HOSTNAME + port + "/operations/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(operationModify, testController.getHeaders()),
                Void.class, 1);

        Assertions.assertEquals(200, result.getStatusCode().value());
    }

    @Test
    @Order(3)
    void postingAnOperationWithIntentionThatExceedConditionsPriceObtainBadRequest() throws Exception {
        OperationRegister operationRegister = new OperationRegister(2,1);

        ResponseEntity<OperationView> result = restTemplate.exchange(TEST_HOSTNAME + port + "/operations",
                HttpMethod.POST, new HttpEntity<>(testController.getBody(operationRegister), testController.getHeaders())
                , OperationView.class);

        System.out.println("result = " + result);

        Assertions.assertEquals(400, result.getStatusCode().value());
    }

    @Test
    @Order(4)
    void postingAnOperationWithIntentionID2AndUserID1ShouldReturnIt() throws Exception {

        OperationRegister operationRegister = new OperationRegister(3,1);

        ResponseEntity<OperationView> result = restTemplate.exchange(TEST_HOSTNAME + port + "/operations",
                HttpMethod.POST, new HttpEntity<>(testController.getBody(operationRegister), testController.getHeaders())
                , OperationView.class);

        System.out.println("result = " + result);

        Assertions.assertEquals("BITCOIN", Objects.requireNonNull(result.getBody()).getCryptocurrency());
    }
}