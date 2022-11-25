package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.aspects.log_data.LogMethodData;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserQueryDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserViewDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.UserValidationException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @Operation(summary = "Search for a user by mail")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/email/{email}")
    public ResponseEntity<UserViewDTO> getUserByEmail(@RequestHeader(value = "Authorization") String token, @PathVariable("email") String email) throws NoSuchElementException, ResourceNotFoundException {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @Operation(summary = "Search for a user by id")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserViewDTO> getUserById(@RequestHeader(value = "Authorization") String token, @PathVariable("id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.findById(id));
    }
/*
    @Operation(summary = "Search for a user by password")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/password/{password}")
    public ResponseEntity<UserView> getUserByPassword(@RequestHeader(value = "Authorization") String token, @PathVariable("password") String password) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.findByPassword(password));
    }
*/
    @Operation(summary = "Operations between two dates")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/traded/{id}/{firstdate}/{seconddate}")
    @ResponseBody
    public ResponseEntity<TradedBetweenDatesDTO> getOperationsBetweenDates(@RequestHeader(value = "Authorization") String token, @PathVariable int id, @PathVariable String firstdate, @PathVariable String seconddate) throws ResourceNotFoundException {
        System.out.println("controller"+id+firstdate+seconddate);
        return ResponseEntity.ok(userService.operationsBetweenDates(id, Long.parseLong(firstdate), Long.parseLong(seconddate)));
    }

    @Operation(summary = "List the users of the query")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public ResponseEntity<List<UserQueryDTO>> listUsers(@RequestHeader(value = "Authorization") String token) {
        return ResponseEntity.ok(userService.getListUsers());
    }

    @LogMethodData
    @Operation(summary = "modify a user's data")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/{id},{field},{data}")
    public ResponseEntity<UserViewDTO> modifyAUser(@RequestHeader(value = "Authorization") String token, @PathVariable int id, @PathVariable String field, @PathVariable String data) throws ResourceNotFoundException, UserValidationException {
        return ResponseEntity.ok(userService.modifyUser(id, field, data));
    }
}