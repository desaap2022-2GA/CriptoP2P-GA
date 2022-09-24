package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto.UserModify;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto.UserRegister;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto.UserView;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserView> listAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRegister userRegister) {
        return userService.create(userRegister);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
        return userService.delete(id);
    }

    @PutMapping
    public ResponseEntity<?> modifyUser(@RequestBody UserModify userModify) {
        return userService.modify(userModify);
    }

    @GetMapping(value = "/email/{email}")
    public ResponseEntity<?>  getUserByEmail(@PathVariable("email") String email) {
        return userService.findByEmail(email);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?>  getUserById(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }
}