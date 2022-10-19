package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceDetails implements UserDetailsService {

    @Autowired
    private IUserRepo userRepo;

    /*
    UserDetails está esperando un usuario. El userDatails tiene la información del usuario que quiere iniciar sesión
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user;
        try {
            user = userRepo.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFound("User not found with email " + email)
            );
        } catch (ResourceNotFound e) {
            throw new RuntimeException(e);
        }

        UserDetails userDetails = (UserDetails) new User(user.getName(), user.getLastname(), user.getEmail(), user.getAddress(),
                user.getPassword(), user.getMercadoPagoCVU(), user.getAddressWalletActiveCripto());

        return userDetails;
    }


}

