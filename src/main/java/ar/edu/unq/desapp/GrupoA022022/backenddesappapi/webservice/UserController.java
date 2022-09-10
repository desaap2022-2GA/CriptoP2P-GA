package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> listAllUsers(){
    return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        System.out.println(user+"c1");
        System.out.println(userService.create(user)+"c2");
        return userService.create(user);
    }

    @PutMapping
    public void modifyUser(@RequestBody User user){
        userService.modify(user);
    }

    @DeleteMapping(value = "/(id)")
    public void deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
    }
}
