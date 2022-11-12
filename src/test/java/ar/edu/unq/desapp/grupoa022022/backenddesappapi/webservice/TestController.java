package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserRegister;
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
        return new HttpEntity<>(getBody(new UserRegister("javainuse", "javainuse", "javainuse@gmail.com",
                "javainuse 56", "javainuse", "javainusejavainusejava",
                "javainus")), getHeaders());
    }

    public HttpEntity<String> getAuthenticationEntity() throws JsonProcessingException {
        return new HttpEntity<>(getBody(new UserDTO("javainuse@gmail.com", "javainuse")), getHeaders());
    }
}