package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.service;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto.HelperDTO;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto.UserModify;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto.UserRegister;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto.UserView;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private IUserRepo userRepo;

    private HelperDTO helper = new HelperDTO();

    public ResponseEntity<?> create(@Valid UserRegister userRegister) {
        try {
            return new ResponseEntity<>(helper.usertoUserView(userRepo.save(helper.userRegistertoUser(userRegister)))
                    , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> modify(@Valid UserModify userModify) {
        try {
            return new ResponseEntity<>(helper.usertoUserView(userRepo.save(helper.userModifytoUser(userModify)))
                    , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public List<UserView> getAllUsers() {
        return helper.userstoUsersView(userRepo.findAll());
    }

    public ResponseEntity<?> delete(int id) {
        try {
            userRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> findById(Integer id) {
        try {
            return new ResponseEntity<>(helper.usertoUserView(userRepo.findById(id).get()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage() + " User not found with userId " + id, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> findByEmail(String email) {
        try {
            return new ResponseEntity<>(helper.usertoUserView(userRepo.findByEmail(email).get()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage() + " User not found with userEmail " + email, HttpStatus.BAD_REQUEST);
        }
    }
/*
    public void checkNewUserEmail(User user) throws ExceptionsUser, ResourceNotFoundException {

        try {
            User newUser = helper.userDTOtoUser(findByEmail(user.getEmail()));

        } catch (ResourceNotFoundException e) {
            create(helper.usertoUserView(user));
        }
    }*/
}