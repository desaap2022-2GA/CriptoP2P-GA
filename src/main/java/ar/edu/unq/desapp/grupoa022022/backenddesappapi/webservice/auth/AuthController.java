package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice.auth;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    /***Agregado***/
    @Operation(summary = "Login")
    @PostMapping(value = "/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UserDTO dto) {
        TokenDTO tokenDTO = userService.login(dto);
        if (tokenDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        System.out.println(tokenDTO.getToken());
        return ResponseEntity.ok(tokenDTO);
    }

    @Operation(summary = "Register User")
    @PostMapping
    public ResponseEntity<UserView> create(@RequestBody UserDTO dto) {
        UserView userView = userService.create(dto);
        if (userView == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userView);
    }
}