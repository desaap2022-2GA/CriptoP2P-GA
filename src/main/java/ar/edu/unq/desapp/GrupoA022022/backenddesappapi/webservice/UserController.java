package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto.UserDTO;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> listAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
    }

    @PutMapping
    public UserDTO modifyUser(@RequestBody UserDTO userDTO) {
        return userService.modify(userDTO);
    }

    @GetMapping(value = "/email/{email}")
    public UserDTO getUserByEmail(@PathVariable("email") String email) throws NoSuchElementException, ResourceNotFoundException {
        return userService.findByEmail(email);
    }

    @GetMapping(value = "/{id}")
    public UserDTO getUserById(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        return userService.findById(id);
    }
}