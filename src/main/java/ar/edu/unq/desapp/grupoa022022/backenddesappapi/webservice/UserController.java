package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserQuery;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserView;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.EmailAlreadyExists;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ExceptionsUser;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.TokenService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    TokenService tokenService;

    /***Agregado***/
    @Operation(summary = "Register User")
    @PostMapping
    public ResponseEntity<UserView> create(@Valid @RequestBody UserRegister dto) throws EmailAlreadyExists {
        UserView userView = userService.create(dto);
        return ResponseEntity.ok(userView);
    }

    @Operation(summary = "Login")
    @PostMapping(value = "/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UserDTO dto) {
        TokenDTO tokenDTO = userService.login(dto);
        if (tokenDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(tokenDTO);
    }

    /***Fin Agregado***/

    @Operation(summary = "Search for a user by mail")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/email/{email}")
    public ResponseEntity<UserView> getUserByEmail(@RequestHeader(value = "Authorization") String token, @PathVariable("email") String email) throws NoSuchElementException, ResourceNotFound {
        TokenDTO tokenDTO = tokenService.validate(token);
        if (tokenDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @Operation(summary = "Search for a user by id")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserView> getUserById(@RequestHeader(value = "Authorization") String token, @PathVariable("id") Integer id) throws ResourceNotFound {
        TokenDTO tokenDTO = tokenService.validate(token);
        if (tokenDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.findById(id));
    }

    @Operation(summary = "Search for a user by password")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/password/{password}")
    public ResponseEntity<UserView> getUserByPassword(@RequestHeader(value = "Authorization") String token, @PathVariable("password") String password) throws ResourceNotFound {
        TokenDTO tokenDTO = tokenService.validate(token);
        if (tokenDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.findByPassword(password));
    }

    @Operation(summary = "Operations between two dates")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/operations-between-dates/{id}/{firstdate}/{seconddate}")
    @ResponseBody
    public ResponseEntity<TradedBetweenDates> getOperationsBetweenDates(@RequestHeader(value = "Authorization") String token, @PathVariable int id, @PathVariable long firstdate, @PathVariable long seconddate) throws ResourceNotFound {
        TokenDTO tokenDTO = tokenService.validate(token);
        if (tokenDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.operationsBetweenDates(id, firstdate, seconddate));
    }

    @Operation(summary = "List the users of the query")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public ResponseEntity<List<UserQuery>> listUsers(@RequestHeader(value = "Authorization") String token) {
        TokenDTO tokenDTO = tokenService.validate(token);
        if (tokenDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.getListUsers());
    }

    @Operation(summary = "modify a user's data")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/{id},{field},{data}")
    public ResponseEntity<User> modifyAUser(@RequestHeader(value = "Authorization") String token, @PathVariable int id, @PathVariable String field, @PathVariable String data) throws ResourceNotFound, ExceptionsUser {
        TokenDTO tokenDTO = tokenService.validate(token);
        if (tokenDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.modifyUser(id, field, data));
    }
}