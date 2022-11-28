package ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResponseEntityBadRequest {
    Map<String, Object> body = new LinkedHashMap<>();

    public ResponseEntityBadRequest(String message) {
        body.put("timestamp: ", new Date());
        body.put("status: ", HttpStatus.BAD_REQUEST.value());
        body.put("error message: ", message);
    }

    public ResponseEntityBadRequest(List<String> message) {
        body.put("timestamp: ", new Date());
        body.put("status: ", HttpStatus.BAD_REQUEST.value());
        body.put("error message: ", message);
    }

    public ResponseEntity getResponseEntity(){
        return new ResponseEntity<>(body, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST);
    }
}
