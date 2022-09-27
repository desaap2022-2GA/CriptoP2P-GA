package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.service;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto.HelperDTO;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto.UserModify;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto.UserRegister;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.EmailAlreadyExists;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ExceptionsUser;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    private IUserRepo userRepo;

    private HelperDTO helper = new HelperDTO();

    public User create(UserRegister userRegister) throws EmailAlreadyExists {
        this.checkNewUserEmail(userRegister.getEmail());
        return userRepo.save(helper.userRegistertoUser(userRegister));
    }

    public User modify(UserModify userModify) throws EmailAlreadyExists {
        this.checkNewUserEmail(userModify.getEmail());
        try {
            return userRepo.save(helper.userModifytoUser(userModify, this.findById(userModify.getId())));
        } catch (ExceptionsUser | ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public void delete(int id) throws ResourceNotFoundException {
        this.findById(id);
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

    public Optional<User> findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public void checkNewUserEmail(String email) throws EmailAlreadyExists {
/*        try {
            User newUser = findByEmail(user.getEmail());*/
        if (!findUserByEmail(email).isEmpty()) {
            throw new EmailAlreadyExists("The email is already registered");
/*            }
        } catch (ResourceNotFoundException e) {
            userRepo.save(user);*/
        }
    }
}