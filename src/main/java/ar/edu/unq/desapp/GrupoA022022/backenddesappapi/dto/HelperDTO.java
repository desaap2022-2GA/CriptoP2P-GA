package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class HelperDTO {

    public User userRegistertoUser(UserRegister userRegister) {
        return new User(userRegister.getName(), userRegister.getLastname(), userRegister.getEmail()
                , userRegister.getAdress(), userRegister.getPassword(), userRegister.getCvumercadoPago()
                , userRegister.getAdressWalletActiveCripto());
    }

    public User userModifytoUser(UserModify userModify) {
        return new User(userModify.getName(), userModify.getLastname(), userModify.getEmail()
                , userModify.getAdress(), userModify.getPassword(), userModify.getCvumercadoPago()
                , userModify.getAdressWalletActiveCripto());
    }

    public UserView usertoUserView(User user) {
        return new UserView(user.getId(), user.getName(), user.getLastname(), user.getEmail(), user.getAdress()
                , user.getCVUMercadoPago(), user.getAdressWalletActiveCripto());
    }

    public List<UserView> userstoUsersView(List<User> all) {
        return all.stream().map(u -> this.usertoUserView(u)).collect(Collectors.toList());
    }
}
