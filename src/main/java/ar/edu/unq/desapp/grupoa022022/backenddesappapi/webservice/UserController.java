package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserModify;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserView;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.EmailAlreadyExists;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ExceptionsUser;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public List<UserView> listAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(value = "/users")
    public UserView createUser(@RequestBody @Valid UserRegister userRegister) throws EmailAlreadyExists {
        return userService.create(userRegister);
    }

    @DeleteMapping(value = "/users/{id}")
    public void deleteUser(@PathVariable("id") int id) throws ResourceNotFound {
        userService.delete(id);
    }

    @PutMapping(value = "/users/{id}")
    public void modifyUser(@RequestBody @Valid UserModify userModify) throws EmailAlreadyExists, ExceptionsUser, ResourceNotFound {
        userService.modify(userModify);
    }

    @GetMapping(value = "/users/email/{email}")
    public UserView getUserByEmail(@PathVariable("email") String email) throws NoSuchElementException, ResourceNotFound {
        return userService.findByEmail(email);
    }

    @GetMapping(value = "/users/{id}")
    public UserView getUserById(@PathVariable("id") Integer id) throws ResourceNotFound {
        return userService.findById(id);
    }

    @GetMapping(value = "users/password/{password}")
    public UserView getUserByPassword(@PathVariable("password") String password) throws ResourceNotFound {
        return userService.findByPassword(password);
    }

    @GetMapping(value = "/users/login")
    @ResponseBody
    public Object login(@RequestParam String email, @RequestParam String password) throws ResourceNotFound{
        return userService.login(email, password);
    }

    @GetMapping(value = "/operations-between-dates/{id}/{firstdate}/{seconddate}")
    @ResponseBody
    public Object getOperationsBetweenDates(@PathVariable int id, @PathVariable long firstdate, @PathVariable long seconddate) throws ResourceNotFound{
        return userService.operationsBetweenDates(id, firstdate, seconddate);
    }


}