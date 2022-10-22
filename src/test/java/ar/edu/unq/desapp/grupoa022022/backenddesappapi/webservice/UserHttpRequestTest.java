package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

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
    void gettingUsersShouldReturnAListThatIncludesOneWithNameMartin() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/users",
                String.class)).contains("Martin");
    }

    @Test
    void gettingUser1ShouldReturnAnUserWithLastnameGaudio() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/users/1",
                String.class)).contains("Gaudio");
    }

    @Test
    void postingAnUserWithNameRogerShouldReturnAListThatIncludesIt() throws Exception {

        UserRegister userRegister = new UserRegister("Roger","Federer","federer@gmail.com"
                , "Av Libertador 5000","1234","1236549877412589632145","Zs59f4lo");


        assertThat(this.restTemplate.postForEntity("http://localhost:" + port + "/users",
                userRegister, UserRegister.class)).toString().contains("federer@gmail.com");
    }
}
