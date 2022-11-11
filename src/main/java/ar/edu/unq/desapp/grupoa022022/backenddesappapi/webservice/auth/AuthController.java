package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice.auth;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.EmailAlreadyExists;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    /***Agregado***/
    @Operation(summary = "Login")
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody UserDTO dto) {
        TokenDTO tokenDTO = userService.login(dto);
        if (tokenDTO == null) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp: ", new Date());
            body.put("status: ", HttpStatus.BAD_REQUEST.value());
            body.put("error message: ", "email or password invalid");
            return new ResponseEntity<>(body, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST);
        }
        System.out.println(tokenDTO.getToken());
        return ResponseEntity.ok(tokenDTO);
    }

    @Operation(summary = "Register User")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserRegister dto) throws EmailAlreadyExists {
        UserView userView = userService.create(dto);
        if (userView == null) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp: ", new Date());
            body.put("status: ", HttpStatus.BAD_REQUEST.value());
            body.put("error message: ", "the email is already register");
            return new ResponseEntity<>(body, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userView);
    }
}