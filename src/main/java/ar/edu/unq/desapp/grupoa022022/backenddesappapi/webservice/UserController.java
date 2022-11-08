package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.EmailAlreadyExists;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ExceptionsUser;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.UserService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Modify a user")
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> modifyUser(@RequestHeader(value = "Authorization") String token, @RequestBody @Valid UserModify userModify) throws EmailAlreadyExists, ExceptionsUser, ResourceNotFound {
        userService.modify(userModify);
        return ResponseEntity.ok("");

    }

    @Operation(summary = "Search for a user by mail")
    @GetMapping(value = "/email/{email}")
    public ResponseEntity<UserView> getUserByEmail(@RequestHeader(value = "Authorization") String token, @PathVariable("email") String email) throws NoSuchElementException, ResourceNotFound {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @Operation(summary = "Search for a user by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserView> getUserById(@RequestHeader(value = "Authorization") String token, @PathVariable("id") Integer id) throws ResourceNotFound {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Operation(summary = "Search for a user by password")
    @GetMapping(value = "/password/{password}")
    public ResponseEntity<UserView> getUserByPassword(@RequestHeader(value = "Authorization") String token, @PathVariable("password") String password) throws ResourceNotFound {
        return ResponseEntity.ok(userService.findByPassword(password));
    }

    @Operation(summary = "Operations between two dates")
    @GetMapping(value = "/operations-between-dates/{id}/{firstdate}/{seconddate}")
    @ResponseBody
    public ResponseEntity<Object> getOperationsBetweenDates(@RequestHeader(value = "Authorization") String token, @PathVariable int id, @PathVariable long firstdate, @PathVariable long seconddate) throws ResourceNotFound {
        return ResponseEntity.ok(userService.operationsBetweenDates(id, firstdate, seconddate));
    }

    @Operation(summary = "List the users of the query")
    @GetMapping
    public ResponseEntity<List<UserQuery>> listUsers(@RequestHeader(value = "Authorization") String token) throws ExceptionsUser {
        return ResponseEntity.ok(userService.getListUsers());
    }
}