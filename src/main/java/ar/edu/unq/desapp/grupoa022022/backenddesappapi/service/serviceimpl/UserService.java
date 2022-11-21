package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.EmailAlreadyExists;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ExceptionsUser;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IUserRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IUserService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DollarConvert;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.JwtProvider;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
//@Builder
public class UserService implements IUserService {

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;


    private final HelperDTO helper = new HelperDTO();

/*    @Override
    public UserView create(UserRegister userRegister) throws EmailAlreadyExists {
        this.checkNewUserEmail(userRegister.getEmail());
        return helper.userToUserView(saveToDataBase(userRegister));
    }*/

    /***Agregado***/
    @Override
    public UserView create(UserRegister userRegister) throws EmailAlreadyExists {
        this.checkNewUserEmail(userRegister.getEmail());
        User user = helper.userRegisterToUser(userRegister);
        return helper.userToUserView(userRepo.save(user));
    }

    public TokenDTO login(UserDTO dto) {
        Optional<User> user = userRepo.findByEmail(dto.getEmail());
        if (!user.isPresent()) {
            return null;
        }
        if (passwordEncoder.matches(dto.getPassword(), user.get().getPassword())) {
            return new TokenDTO(jwtProvider.createToken(user.get()));
        }
        return null;
    }
    /***Fin Agregado***/

    @Override
    public List<UserView> getAllUsers() {
        return helper.usersToUsersView(userRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
    }

    @Override
    public void delete(int id) throws ResourceNotFound {
        this.findById(id);
        userRepo.deleteById(id);
    }

    @Override
    public UserView findById(Integer id) throws ResourceNotFound {
        return helper.userToUserView(getFromDataBase(id));
    }

    @Override
    public UserView findByEmail(String email) throws ResourceNotFound {
        return helper.userToUserView(userRepo.findByEmail(email).orElseThrow(
                () -> new ResourceNotFound("User not found with user email")
        ));
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public void checkNewUserEmail(String email) throws EmailAlreadyExists {
        if (findUserByEmail(email).isPresent()) {
            throw new EmailAlreadyExists("The email is already registered");
        }
    }

    @Override
    public UserView findByPassword(String password) throws ResourceNotFound {
        return helper.userToUserView(userRepo.findByPassword(password).orElseThrow(
                () -> new ResourceNotFound("User not found with user password")
        ));
    }

    @Override
    public Object login(String email, String password) throws ResourceNotFound {
        UserView user = findByPassword(password);

        return (user.getEmail().equals(email)) ? user : new ResourceNotFound("Incorrect email or password");
    }

    @Override
    public TradedBetweenDates operationsBetweenDates(int userId, long firstDate, long secondDate) throws ResourceNotFound {
        User user = this.getFromDataBase(userId);
        Set<Operation> operations = user.operationsBetweenDates(firstDate, secondDate);

        double amountInPesos = user.volumeTraded(operations);
        double amountInDollars = new DollarConvert().amountInDollars(amountInPesos);
        TradedBetweenDates tradedBetweenDates = new TradedBetweenDates(amountInDollars, amountInPesos);
        for (Operation operation : operations) {
            Intention intention = operation.getIntention();
            tradedBetweenDates.addCryptoDetails(helper.intentionToCryptoDetails(intention));
        }
        return tradedBetweenDates;
    }

    public void deleteAllUsers() {
        userRepo.deleteAll();
    }

    public User saveToDataBase(UserRegister userRegister) {
        return userRepo.save(helper.userRegisterToUser(userRegister));
    }

    public User getFromDataBase(int userId) throws ResourceNotFound {
        return userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFound("User not found with userId " + userId)
        );
    }

    public void update(User user) {
        userRepo.save(user);
    }

    @Override
    public List<UserQuery> getListUsers() {

        ArrayList<UserQuery> userList = new ArrayList<>();
        List<User> users = userRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
        for (User us : users) {
            UserQuery user = new UserQuery(us.getName(), us.getLastname(), us.getNumberOperations(),
                    us.getReputation());

            userList.add(user);
        }
        return userList.stream().toList();
    }

    @Override
    public User modifyUser(int id, String field, String data) throws ResourceNotFound, ExceptionsUser {

        User user = userRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("User not found with id " + id)
        );

        //UserView newUser = helper.userToUserView(userRepo.save(helper.userModify(user, field, data)));
        User newUser = helper.userModify(user, field, data);
        System.out.println("usuario cambiado: " + newUser);

        return newUser;
    }
}