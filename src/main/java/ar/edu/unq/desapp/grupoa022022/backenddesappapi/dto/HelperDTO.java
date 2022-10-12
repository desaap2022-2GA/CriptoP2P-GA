package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ExceptionsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Objects;

public class HelperDTO {
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public User userRegistertoUser(UserRegister userRegister) {
        return new User(userRegister.getName(), userRegister.getLastname(), userRegister.getEmail()
                , userRegister.getAddress(), encoder.encode(userRegister.getPassword()), userRegister.getMercadoPagoCVU()
                , userRegister.getAddressWalletActiveCripto());
    }

    public User userModifytoUser(UserModify userModify, User userOriginal) throws ExceptionsUser {

        User userToModify = userOriginal;

        if (userModify.getName() != null && !Objects.equals(userModify.getName(), userToModify.getName())) {
            userToModify.setName(userModify.getName());
        }
        if (userModify.getLastname() != null && !Objects.equals(userModify.getLastname(), userToModify.getLastname())) {
            userToModify.setLastname(userModify.getLastname());
        }
        if (userModify.getEmail() != null && !Objects.equals(userModify.getEmail(), userToModify.getEmail())) {
            userToModify.setEmail(userModify.getEmail());
        }
        if (userModify.getAddress() != null && !Objects.equals(userModify.getAddress(), userToModify.getAddress())) {
            userToModify.setAddress(userModify.getAddress());
        }
        if (userModify.getPassword() != null && !Objects.equals(userModify.getPassword(), userToModify.getPassword())) {
            userToModify.setPassword(encoder.encode(userModify.getPassword()));
        }
        if (userModify.getMercadoPagoCVU() != null && !Objects.equals(userModify.getMercadoPagoCVU(), userToModify.getMercadoPagoCVU())) {
            userToModify.setMercadoPagoCVU(userModify.getMercadoPagoCVU());
        }
        if (userModify.getAddressWalletActiveCripto() != null && !Objects.equals(userModify.getAddressWalletActiveCripto(), userToModify.getAddressWalletActiveCripto())) {
            userToModify.setAddressWalletActiveCripto(userModify.getAddressWalletActiveCripto());
        }
    return userToModify;
    }

    public UserView usertoUserView(User user) {
        return new UserView(user.getId(), user.getName(), user.getLastname(), user.getEmail(), user.getAddress(),
                user.getMercadoPagoCVU(), user.getAddressWalletActiveCripto(), user.getPoints(),
                user.getNumberOperations(), user.getReputation());
    }

    public List<UserView> userstoUsersView(List<User> all) {
        return all.stream().map(this::usertoUserView).toList();
    }
}
