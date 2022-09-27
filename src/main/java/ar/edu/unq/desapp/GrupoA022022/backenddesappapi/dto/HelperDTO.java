package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ExceptionsUser;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.IUserRepo;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.webservice.UserController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class HelperDTO {

    public User userRegistertoUser(UserRegister userRegister) {
        return new User(userRegister.getName(), userRegister.getLastname(), userRegister.getEmail()
                , userRegister.getAddress(), userRegister.getPassword(), userRegister.getmercadoPagoCVU()
                , userRegister.getAddressWalletActiveCripto());
    }

    public User userModifytoUser(UserModify userModify, User userOriginal) throws ExceptionsUser {

        User userToModify = userOriginal;

        if (userModify.getName() != null && userModify.getName() != userToModify.getName()) {
            userToModify.setName(userModify.getName());
        }
        if (userModify.getLastname() != null && userModify.getLastname() != userToModify.getLastname()) {
            userToModify.setLastname(userModify.getLastname());
        }
        if (userModify.getEmail() != null && userModify.getEmail() != userToModify.getEmail()) {
            userToModify.setEmail(userModify.getEmail());
        }
        if (userModify.getAddress() != null && userModify.getAddress() != userToModify.getAddress()) {
            userToModify.setAddress(userModify.getAddress());
        }
        if (userModify.getPassword() != null && userModify.getPassword() != userToModify.getPassword()) {
            userToModify.setPassword(userModify.getPassword());
        }
        if (userModify.getMercadoPagoCVU() != null && userModify.getMercadoPagoCVU() != userToModify.getMercadoPagoCVU()) {
            userToModify.setMercadoPagoCVU(userModify.getMercadoPagoCVU());
        }
        if (userModify.getAddressWalletActiveCripto() != null && userModify.getAddressWalletActiveCripto() != userToModify.getAddressWalletActiveCripto()) {
            userToModify.setAddressWalletActiveCripto(userModify.getAddressWalletActiveCripto());
        }
    return userToModify;
    }

    public UserView usertoUserView(User user) {
        return new UserView(user.getId(), user.getName(), user.getLastname(), user.getEmail(), user.getAddress()
                , user.getMercadoPagoCVU(), user.getAddressWalletActiveCripto());
    }

    public List<UserView> userstoUsersView(List<User> all) {
        return all.stream().map(u -> this.usertoUserView(u)).collect(Collectors.toList());
    }
}
