package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.EmailAlreadyExistsException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.UserValidationException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IUserRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IUserService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DollarConvert;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    private final HelperDTO helper = new HelperDTO();


    @Override
    public UserViewDTO create(UserRegisterDTO userRegisterDTO) throws EmailAlreadyExistsException {
        this.checkNewUserEmail(userRegisterDTO.getEmail());
        User user = helper.userRegisterToUser(userRegisterDTO);
        return helper.userToUserView(userRepo.save(user));
    }

    @Override
    public Object login(UserDTO dto) throws ResourceNotFoundException {
        User user = userRepo.findByEmail(dto.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException("email or password invalid"));
        if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return new TokenDTO(jwtProvider.createToken(user));
        } else throw new ResourceNotFoundException("email or password invalid");
    }

    @Override
    public List<UserViewDTO> getAllUsers() {
        return helper.usersToUsersView(userRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
    }

    @Override
    public void delete(int id) throws ResourceNotFoundException {
        this.findById(id);
        userRepo.deleteById(id);
    }

    @Override
    public UserViewDTO findById(Integer id) throws ResourceNotFoundException {
        return helper.userToUserView(getFromDataBase(id));
    }

    @Override
    public UserViewDTO findByEmail(String email) throws ResourceNotFoundException {
        return helper.userToUserView(userRepo.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User not found with user email")
        ));
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public void checkNewUserEmail(String email) throws EmailAlreadyExistsException {
        if (findUserByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException("The email is already registered");
        }
    }

    @Override
    public UserViewDTO findByPassword(String password) throws ResourceNotFoundException {
        return helper.userToUserView(userRepo.findByPassword(password).orElseThrow(
                () -> new ResourceNotFoundException("User not found with user password")
        ));
    }

    /*
        public Object login(String email, String password) throws ResourceNotFoundException {
            UserView user = findByPassword(password);

            return (user.getEmail().equals(email)) ? user : new ResourceNotFoundException("Incorrect email or password");
        }
    */
    @Override
    public TradedBetweenDatesDTO operationsBetweenDates(int userId, long firstDate, long secondDate) throws ResourceNotFoundException {
        User user = this.getFromDataBase(userId);
        Set<Operation> operations = user.operationsBetweenDates(firstDate, secondDate);

        double amountInPesos = user.volumeTraded(operations);
        double amountInDollars = new DollarConvert().amountInDollars(amountInPesos);
        TradedBetweenDatesDTO tradedBetweenDatesDTO = new TradedBetweenDatesDTO(amountInDollars, amountInPesos);
        for (Operation operation : operations) {
            Intention intention = operation.getIntention();
            tradedBetweenDatesDTO.addCryptoDetails(helper.intentionToCryptoDetails(intention));
        }
        return tradedBetweenDatesDTO;
    }

    public void deleteAllUsers() {
        userRepo.deleteAll();
    }

    public User saveToDataBase(UserRegisterDTO userRegisterDTO) {
        return userRepo.save(helper.userRegisterToUser(userRegisterDTO));
    }

    public User getFromDataBase(int userId) throws ResourceNotFoundException {
        return userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with userId " + userId)
        );
    }

    public void update(User user) {
        userRepo.save(user);
    }

    @Override
    public List<UserQueryDTO> getListUsers() {

        ArrayList<UserQueryDTO> userList = new ArrayList<>();
        List<User> users = userRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
        for (User us : users) {
            UserQueryDTO user = new UserQueryDTO(us.getName(), us.getLastname(), String.valueOf(us.getNumberOperations()),
                    String.valueOf(us.getReputation()));

            userList.add(user);
        }
        return userList.stream().toList();
    }

    @Override
    public UserViewDTO modifyUser(int id, String field, String data) throws ResourceNotFoundException, UserValidationException {

        User user = userRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id " + id)
        );

        User newUser = helper.userModify(user, field, data);
        update(newUser);

        return helper.userToUserView(newUser);
    }
}