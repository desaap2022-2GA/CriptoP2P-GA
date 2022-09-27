package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto.UserModify;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto.UserRegister;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.EmailAlreadyExists;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ExceptionsUser;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public List<User> listAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(value = "/users")
    public User createUser(@RequestBody @Valid UserRegister userRegister) throws EmailAlreadyExists {
        return userService.create(userRegister);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable("id") int id) throws ResourceNotFoundException {
        userService.delete(id);
    }

    @PutMapping(value = "/users/update/{id}")
    public void modifyUser(@RequestBody @Valid UserModify userModify) throws EmailAlreadyExists, ExceptionsUser, ResourceNotFoundException {
        userService.modify(userModify);
    }

    @GetMapping(value = "/{email}")
    public User getUserByEmail(@PathVariable("email") String email) throws NoSuchElementException, ResourceNotFoundException {
        return userService.findByEmail(email);
    }

    @GetMapping(value = "/users/{id}")
    public User getUserById(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        return userService.findById(id);
    }
}