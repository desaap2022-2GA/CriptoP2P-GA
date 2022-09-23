package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.service;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto.HelperDTO;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto.UserDTO;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ExceptionsUser;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private IUserRepo userRepo;

    private HelperDTO helper = new HelperDTO();

    public UserDTO create(@Valid UserDTO userDTO) {
        return helper.usertoUserDTO(userRepo.save(helper.userDTOtoUser(userDTO)));
    }

    public UserDTO modify(@Valid UserDTO userDTO) {
        return helper.usertoUserDTO(userRepo.save(helper.userDTOtoUser(userDTO)));
    }

    public List<UserDTO> getAllUsers() {
        return helper.userstoUsersDTO(userRepo.findAll());
    }

    public void delete(int id) {
        userRepo.deleteById(id);
    }

    public UserDTO findById(Integer id) throws ResourceNotFoundException {
        return helper.usertoUserDTO(userRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with userId " + id)
        ));
    }

    public UserDTO findByEmail(String email) throws ResourceNotFoundException {
        return helper.usertoUserDTO(userRepo.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User not found with user email")
        ));
    }

    public void checkNewUserEmail(User user) throws ExceptionsUser, ResourceNotFoundException {

        try {
            User newUser = helper.userDTOtoUser(findByEmail(user.getEmail()));

        } catch (ResourceNotFoundException e) {
            create(helper.usertoUserDTO(user));
        }
    }
}