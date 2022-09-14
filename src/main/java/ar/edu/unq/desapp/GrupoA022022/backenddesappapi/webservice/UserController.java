package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public MappingJacksonValue listAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public MappingJacksonValue registerUser(@Valid @RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping(value = "/(id)")
    public MappingJacksonValue getUser(@PathVariable("id") int id) throws NoSuchElementException {
        return userService.getUser(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) throws ResourceNotFoundException {
        userService.delete(id);
    }

    @PutMapping
    public MappingJacksonValue modifyUser(@Valid @RequestBody User user){
        return userService.modify(user);
    }
}
