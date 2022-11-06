package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserModify;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserView;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.EmailAlreadyExists;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ExceptionsUser;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    UserView create(UserRegister userRegister) throws EmailAlreadyExists;

    UserView modify(UserModify userModify) throws EmailAlreadyExists, ResourceNotFound, ExceptionsUser;

    List<UserView> getAllUsers();

    void delete(int id) throws ResourceNotFound;

    UserView findById(Integer id) throws ResourceNotFound;

    UserView findByEmail(String email) throws ResourceNotFound;

    Optional<User> findUserByEmail(String email);

    void checkNewUserEmail(String email) throws EmailAlreadyExists;

    void deleteAllUsers();

    User saveToDataBase(UserRegister userRegister);

    User getFromDataBase(int userId) throws ResourceNotFound;

    UserView findByPassword(String password) throws ResourceNotFound;

    Object login(String email, String password) throws ResourceNotFound;
}
