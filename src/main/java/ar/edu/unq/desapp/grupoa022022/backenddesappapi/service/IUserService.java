package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service;

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

    public UserView create(UserRegister userRegister) throws EmailAlreadyExists;

    public UserView modify(UserModify userModify) throws EmailAlreadyExists, ResourceNotFound, ExceptionsUser;

    public List<UserView> getAllUsers();

    public void delete(int id) throws ResourceNotFound;

    public UserView findById(Integer id) throws ResourceNotFound;

    public UserView findByEmail(String email) throws ResourceNotFound;

    public Optional<User> findUserByEmail(String email);

    public void checkNewUserEmail(String email) throws EmailAlreadyExists;

    public void deleteAllUsers();

    public User saveToDataBase(UserRegister userRegister);

    public User getFromDataBase(int userId) throws ResourceNotFound;

    public UserView findByPassword(String password) throws ResourceNotFound;

    public Object login(String email, String password) throws ResourceNotFound;


}
