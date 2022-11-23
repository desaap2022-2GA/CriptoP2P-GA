package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice.auth;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.EmailAlreadyExistsException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Login")
    @PostMapping(value = "/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UserDTO dto) throws ResourceNotFoundException {
        TokenDTO tokenDTO = (TokenDTO) userService.login(dto);
        return ResponseEntity.ok(tokenDTO);
    }

    @Operation(summary = "Register User")
    @PostMapping
    public ResponseEntity<UserView> create(@Valid @RequestBody UserRegister dto) throws EmailAlreadyExistsException {
        UserView userView = userService.create(dto);
        return ResponseEntity.ok(userView);
    }
}