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

        if (userModify.getName() != null && !Objects.equals(userModify.getName(), userOriginal.getName())) {
            userOriginal.setName(userModify.getName());
        }
        if (userModify.getLastname() != null && !Objects.equals(userModify.getLastname(), userOriginal.getLastname())) {
            userOriginal.setLastname(userModify.getLastname());
        }
        if (userModify.getEmail() != null && !Objects.equals(userModify.getEmail(), userOriginal.getEmail())) {
            userOriginal.setEmail(userModify.getEmail());
        }
        if (userModify.getAddress() != null && !Objects.equals(userModify.getAddress(), userOriginal.getAddress())) {
            userOriginal.setAddress(userModify.getAddress());
        }
        if (userModify.getPassword() != null && !Objects.equals(userModify.getPassword(), userOriginal.getPassword())) {
            userOriginal.setPassword(encoder.encode(userModify.getPassword()));
        }
        if (userModify.getMercadoPagoCVU() != null && !Objects.equals(userModify.getMercadoPagoCVU(), userOriginal.getMercadoPagoCVU())) {
            userOriginal.setMercadoPagoCVU(userModify.getMercadoPagoCVU());
        }
        if (userModify.getAddressWalletActiveCrypto() != null && !Objects.equals(userModify.getAddressWalletActiveCrypto(), userOriginal.getAddressWalletActiveCripto())) {
            userOriginal.setAddressWalletActiveCripto(userModify.getAddressWalletActiveCrypto());
        }
    return userOriginal;
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
