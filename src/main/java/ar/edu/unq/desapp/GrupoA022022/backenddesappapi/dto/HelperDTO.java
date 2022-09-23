package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class HelperDTO {

    public User userDTOtoUser(UserDTO userDTO) {
        return new User(userDTO.getName(), userDTO.getLastname(), userDTO.getEmail(), userDTO.getAdress()
                , userDTO.getPassword(), userDTO.getCVUMercadoPago(), userDTO.getAdressWalletActiveCripto());
    }

    public UserDTO usertoUserDTO(User user) {
        return new UserDTO(user.getName(), user.getLastname(), user.getEmail(), user.getAdress()
                , user.getPassword(), user.getCVUMercadoPago(), user.getAdressWalletActiveCripto());
    }

    public List<UserDTO> userstoUsersDTO(List<User> all) {
        return all.stream().map(u -> this.usertoUserDTO(u)).collect(Collectors.toList());
    }
}
