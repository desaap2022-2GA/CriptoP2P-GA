package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.service;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ExceptionsUser;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private IUserRepo userRepo;

    public User create(@Valid User user) {
        return userRepo.save(user);
    }

    public User modify(User user) {
        return userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public void delete(int id) {
        userRepo.deleteById(id);
    }

    public User findById(Integer id) throws ResourceNotFoundException {
        return userRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with userId " + id)
        );
    }

    public User findByEmail(String email) throws ResourceNotFoundException {
        return userRepo.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User not found with user email")
        );
    }

    public void checkNewUserEmail(User user) throws ExceptionsUser, ResourceNotFoundException {

        try {
            User newUser = findByEmail(user.getEmail());

        } catch (ResourceNotFoundException e) {
            create(user);
        }
    }
}