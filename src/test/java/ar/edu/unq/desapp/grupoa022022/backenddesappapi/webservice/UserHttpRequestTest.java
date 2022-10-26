package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserHttpRequestTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private UserController controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void gettingUsersShouldReturnAListWithSizeEquals2() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/users",
                UserView[].class)).hasSize(2);
    }

    @Test
    void gettingUser1ShouldReturnAnUserWithLastnameGaudio() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/users/1",
                UserView.class)).toString().contains("Gaudio");
    }

    @Test
    void gettingUserWithEmailGaudioYahooShouldReturnAnUserWithLastnameGaudio() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/users/qemail/gaudio@yahoo.com",
                UserView.class)).toString().contains("Gaudio");
    }

    @Test
    void gettingUserOperationBetweenDatesReturnATradedBetweenDates() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/operations-between-dates/1/10000000000/20000000000",
                TradedBetweenDates.class)).toString().contains("pesos");
    }

    @Test
    void postingAnUserWithNameRogerShouldReturnAListThatIncludesIt() throws Exception {

        UserRegister userRegister = new UserRegister("Roger", "Federer", "federer@gmail.com"
                , "Av Libertador 5000", "1234", "1236549877412589632145", "Zs59f4lo");

        ResponseEntity<UserView> result = this.restTemplate.postForEntity("http://localhost:" + port + "/users",
                userRegister, UserView.class);

        assertEquals(result.getBody().getEmail(), "federer@gmail.com");
    }

    @Test
    void puttingUser1WithAddressHusaresShouldReturnThatChange() throws Exception {

        UserModify userModify = new UserModify("Roger", "Federer", "federer@gmail.com"
                , "Husares 5000", "1234", "1236549877412589632145", "Zs59f4lo");

        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/users/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(userModify, createJsonHeader()),
                Void.class, 2)).isNotNull();
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
