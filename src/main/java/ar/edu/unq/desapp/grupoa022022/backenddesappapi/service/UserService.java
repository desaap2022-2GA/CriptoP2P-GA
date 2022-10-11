package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.EmailAlreadyExists;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ExceptionsUser;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
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
public class UserService implements IUserService{
    @Autowired
    private IUserRepo userRepo;

    private HelperDTO helper = new HelperDTO();

    @Override
    public UserView create(UserRegister userRegister) throws EmailAlreadyExists {
        this.checkNewUserEmail(userRegister.getEmail());
        return helper.usertoUserView(saveToDataBase(userRegister));
    }

    @Override
    public UserView modify(UserModify userModify) throws EmailAlreadyExists, ResourceNotFound, ExceptionsUser {
        User originalUser = userRepo.findById(userModify.getId()).orElseThrow(
                () -> new ResourceNotFound("User not found with userId " + userModify.getId())
        );
        if (!Objects.equals(originalUser.getEmail(), userModify.getEmail())) {
            this.checkNewUserEmail(userModify.getEmail());
        }
        return helper.usertoUserView(userRepo.save(helper.userModifytoUser(userModify, originalUser)));
    }

    @Override
    public List<UserView> getAllUsers() {
        return helper.userstoUsersView(userRepo.findAll());
    }

    @Override
    public void delete(int id) throws ResourceNotFound {
        this.findById(id);
        userRepo.deleteById(id);
    }

    @Override
    public UserView findById(Integer id) throws ResourceNotFound {
        return helper.usertoUserView(getFromDataBase(id));
    }

    @Override
    public UserView findByEmail(String email) throws ResourceNotFound {
        return helper.usertoUserView(userRepo.findByEmail(email).orElseThrow(
                () -> new ResourceNotFound("User not found with user email")
        ));
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public void checkNewUserEmail(String email) throws EmailAlreadyExists {
        if (!findUserByEmail(email).isEmpty()) {
            throw new EmailAlreadyExists("The email is already registered");
        }
    }
    @Override
    public UserView findByPassword(String password) throws ResourceNotFound {
        return helper.usertoUserView(userRepo.findByPassword(password).orElseThrow(
                () -> new ResourceNotFound("User not found with user password")
        ));
    }

    @Override
    public Object login(String email, String password) throws ResourceNotFound {
        UserView user = findByPassword(password);

        return (user.getEmail().equals(email))? user: new ResourceNotFound("Incorrect email or password");
    }

    public void deleteAllUsers() {
        userRepo.deleteAll();
    }

    public User saveToDataBase(UserRegister userRegister) {
        return userRepo.save(helper.userRegistertoUser(userRegister));
    }

    public User getFromDataBase(int userId) throws ResourceNotFound {
        return userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFound("User not found with userId " + userId)
        );
    }
}

