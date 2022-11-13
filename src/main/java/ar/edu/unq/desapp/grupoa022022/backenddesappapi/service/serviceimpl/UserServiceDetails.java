package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

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
        user = userRepo.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email " + email)
        );

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.emptyList();
            }

            @Override
            public String getPassword() {

                return user.getPassword();
            }

            @Override
            public String getUsername() {

                return user.getEmail();
            }

            @Override
            public boolean isAccountNonExpired() {

                return true;
            }

            @Override
            public boolean isAccountNonLocked() {

                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}