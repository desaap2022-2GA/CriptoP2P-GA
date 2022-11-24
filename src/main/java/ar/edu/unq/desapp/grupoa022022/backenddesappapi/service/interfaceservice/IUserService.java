package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserQueryDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserRegisterDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserViewDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.EmailAlreadyExistsException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.UserValidationException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    UserViewDTO create(UserRegisterDTO userRegisterDTO) throws EmailAlreadyExistsException;

    List<UserViewDTO> getAllUsers();

    void delete(int id) throws ResourceNotFoundException;

    UserViewDTO findById(Integer id) throws ResourceNotFoundException;

    UserViewDTO findByEmail(String email) throws ResourceNotFoundException;

    Optional<User> findUserByEmail(String email);

    void checkNewUserEmail(String email) throws EmailAlreadyExistsException;

    TradedBetweenDatesDTO operationsBetweenDates(int userId, long firstDate, long secondDate) throws ResourceNotFoundException;

    void deleteAllUsers();

    User saveToDataBase(UserRegisterDTO userRegisterDTO) throws UserValidationException;

    User getFromDataBase(int userId) throws ResourceNotFoundException;

    UserViewDTO findByPassword(String password) throws ResourceNotFoundException;

    Object login(UserDTO userDTO) throws ResourceNotFoundException;

    void update(User user);

    List<UserQueryDTO> getListUsers() throws UserValidationException;

    UserViewDTO modifyUser(int id, String field, String data) throws ResourceNotFoundException, UserValidationException;
}
