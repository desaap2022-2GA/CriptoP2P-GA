package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserRegisterDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestController {

    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    public String getBody(final Object user) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(user);
    }

    public HttpEntity<String> getRegistrationEntity() throws JsonProcessingException {
        return new HttpEntity<>(getBody(new UserRegisterDTO("userU", "userU", "userU@gmail.com",
                "userU 56", "userU@", "userUserUserUserUserUs",
                "userUser")), getHeaders());
    }
    public HttpEntity<String> getRegistrationEntityC() throws JsonProcessingException {
        return new HttpEntity<>(getBody(new UserRegisterDTO("userU", "userU", "userC@gmail.com",
                "userU 56", "userU@", "userUserUserUserUserUs",
                "userUser")), getHeaders());
    }
    public HttpEntity<String> getRegistrationEntityI() throws JsonProcessingException {
        return new HttpEntity<>(getBody(new UserRegisterDTO("userU", "userU", "userI@gmail.com",
                "userU 56", "userU@", "userUserUserUserUserUs",
                "userUser")), getHeaders());
    }
    public HttpEntity<String> getRegistrationEntityQ() throws JsonProcessingException {
        return new HttpEntity<>(getBody(new UserRegisterDTO("userU", "userU", "userQ@gmail.com",
                "userU 56", "userU@", "userUserUserUserUserUs",
                "userUser")), getHeaders());
    }
    public HttpEntity<String> getRegistrationEntityO() throws JsonProcessingException {
        return new HttpEntity<>(getBody(new UserRegisterDTO("userU", "userU", "userO@gmail.com",
                "userU 56", "userU@", "userUserUserUserUserUs",
                "userUser")), getHeaders());
    }

    public HttpEntity<String> getAuthenticationEntity() throws JsonProcessingException {
        return new HttpEntity<>(getBody(new UserDTO("userU@gmail.com", "userU@")), getHeaders());
    }
    public HttpEntity<String> getAuthenticationEntityC() throws JsonProcessingException {
        return new HttpEntity<>(getBody(new UserDTO("userC@gmail.com", "userU@")), getHeaders());
    }
    public HttpEntity<String> getAuthenticationEntityI() throws JsonProcessingException {
        return new HttpEntity<>(getBody(new UserDTO("userI@gmail.com", "userU@")), getHeaders());
    }
    public HttpEntity<String> getAuthenticationEntityQ() throws JsonProcessingException {
        return new HttpEntity<>(getBody(new UserDTO("userQ@gmail.com", "userU@")), getHeaders());
    }
    public HttpEntity<String> getAuthenticationEntityO() throws JsonProcessingException {
        return new HttpEntity<>(getBody(new UserDTO("userO@gmail.com", "userU@")), getHeaders());
    }
}