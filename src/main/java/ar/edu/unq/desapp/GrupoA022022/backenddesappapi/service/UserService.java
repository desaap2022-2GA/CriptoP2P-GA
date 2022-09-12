package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.service;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto.UserRequest;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private IUserRepo userRepo;

    public User create(@Valid UserRequest userRequest) {
        User user = User.
                build(0, userRequest.getName(), userRequest.getLastname(), userRequest.getEmail(),
                        userRequest.getAdress(), userRequest.getPassword(), userRequest.getCVUMercadoPago(),
                        userRequest.getAdressWalletActiveCripto(), 0, 0, new HashSet<>());
        return userRepo.save(user);
    }

    public User modify(User user) {
        System.out.println(user + "s1");
        return userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public void delete(int id) {
        userRepo.deleteById(id);
    }

    public User getUser(Integer id) throws ResourceNotFoundException {
        User user = userRepo.findById(id).get();
        if (user != null) {
            return user;
        } else {
            throw new ResourceNotFoundException("user not found with id : " + id);
        }
    }

/*
    public User getUser(Integer id) throws ResourceNotFoundException {
        return userRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with userId " + id)
        );
    }
*/
}
