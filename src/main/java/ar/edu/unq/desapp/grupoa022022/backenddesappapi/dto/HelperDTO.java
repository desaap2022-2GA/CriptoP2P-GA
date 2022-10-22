package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ExceptionsUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Objects;

public class HelperDTO {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public User userRegisterToUser(UserRegister userRegister) {
        return new User(userRegister.getName(), userRegister.getLastname(), userRegister.getEmail()
                , userRegister.getAddress(), encoder.encode(userRegister.getPassword()), userRegister.getMercadoPagoCVU()
                , userRegister.getAddressWalletActiveCrypto());
    }

    public User userModifyToUser(UserModify userModify, User userOriginal) throws ExceptionsUser {

        if (firstNotNullAndFirstAndSecondNotEquals(userModify.getName(), userOriginal.getName())) {
            userOriginal.setName(userModify.getName());
        }
        if (firstNotNullAndFirstAndSecondNotEquals(userModify.getLastname(), userOriginal.getLastname())) {
            userOriginal.setLastname(userModify.getLastname());
        }
        if (firstNotNullAndFirstAndSecondNotEquals(userModify.getEmail(), userOriginal.getEmail())) {
            userOriginal.setEmail(userModify.getEmail());
        }
        if (firstNotNullAndFirstAndSecondNotEquals(userModify.getAddress(), userOriginal.getAddress())) {
            userOriginal.setAddress(userModify.getAddress());
        }
        if (firstNotNullAndFirstAndSecondNotEquals(userModify.getPassword(), userOriginal.getPassword())) {
            userOriginal.setPassword(encoder.encode(userModify.getPassword()));
        }
        if (firstNotNullAndFirstAndSecondNotEquals(userModify.getMercadoPagoCVU(), userOriginal.getMercadoPagoCVU())) {
            userOriginal.setMercadoPagoCVU(userModify.getMercadoPagoCVU());
        }
        if (firstNotNullAndFirstAndSecondNotEquals(userModify.getAddressWalletActiveCrypto(), userOriginal.getAddressWalletActiveCripto())) {
            userOriginal.setAddressWalletActiveCripto(userModify.getAddressWalletActiveCrypto());
        }
    return userOriginal;
    }

    public boolean firstNotNullAndFirstAndSecondNotEquals(String firstCheck, String secondCheck){
        return firstCheck !=null && !Objects.equals(firstCheck, secondCheck);
    }

    public UserView userToUserView(User user) {
        return new UserView(user.getId(), user.getName(), user.getLastname(), user.getEmail(), user.getAddress(),
                user.getMercadoPagoCVU(), user.getAddressWalletActiveCripto(), user.getPoints(),
                user.getNumberOperations(), user.getReputation());
    }

    public List<UserView> usersToUsersView(List<User> all) {
        return all.stream().map(this::userToUserView).toList();
    }
}
