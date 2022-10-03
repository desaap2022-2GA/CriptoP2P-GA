package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.EmailAlreadyExists;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ExceptionsUser;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IUserRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.HelperDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserModify;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private IUserRepo userRepo;

    private HelperDTO helper = new HelperDTO();

    public UserView create(UserRegister userRegister) throws EmailAlreadyExists {
        this.checkNewUserEmail(userRegister.getEmail());
        return helper.usertoUserView(userRepo.save(helper.userRegistertoUser(userRegister)));
    }

    public UserView modify(UserModify userModify) throws EmailAlreadyExists, ResourceNotFoundException, ExceptionsUser {
        User originalUser = userRepo.findById(userModify.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User not found with userId " + userModify.getId())
        );
        if (!Objects.equals(originalUser.getEmail(), userModify.getEmail())) {
            this.checkNewUserEmail(userModify.getEmail());
        }
        return helper.usertoUserView(userRepo.save(helper.userModifytoUser(userModify, originalUser)));
    }

    public List<UserView> getAllUsers() {
        return helper.userstoUsersView(userRepo.findAll());
    }

    public void delete(int id) throws ResourceNotFoundException {
        this.findById(id);
        userRepo.deleteById(id);
    }

    public UserView findById(Integer id) throws ResourceNotFoundException {
        return helper.usertoUserView(userRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with userId " + id)
        ));
    }

    public UserView findByEmail(String email) throws ResourceNotFoundException {
        return helper.usertoUserView(userRepo.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User not found with user email")
        ));
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public void checkNewUserEmail(String email) throws EmailAlreadyExists {

        if (!findUserByEmail(email).isEmpty()) {
            throw new EmailAlreadyExists("The email is already registered");
           }
    }


    public void deleteAllUsers() {
        userRepo.deleteAll();
    }
}

