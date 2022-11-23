package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserQuery;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserView;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.EmailAlreadyExistsException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.UserValidationException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    UserView create(UserRegister userRegister) throws EmailAlreadyExistsException;

    List<UserView> getAllUsers();

    void delete(int id) throws ResourceNotFoundException;

    UserView findById(Integer id) throws ResourceNotFoundException;

    UserView findByEmail(String email) throws ResourceNotFoundException;

    Optional<User> findUserByEmail(String email);

    void checkNewUserEmail(String email) throws EmailAlreadyExistsException;

    TradedBetweenDates operationsBetweenDates(int userId, long firstDate, long secondDate) throws ResourceNotFoundException;

    void deleteAllUsers();

    User saveToDataBase(UserRegister userRegister) throws UserValidationException;

    User getFromDataBase(int userId) throws ResourceNotFoundException;

    UserView findByPassword(String password) throws ResourceNotFoundException;

    Object login(UserDTO userDTO) throws ResourceNotFoundException;

    void update(User user);

    List<UserQuery> getListUsers() throws UserValidationException;

    UserView modifyUser(int id, String field, String data) throws ResourceNotFoundException, UserValidationException;
}
