package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.TokenDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IUserRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenService {

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private JwtProvider jwtProvider;

    public TokenDTO validate(String token) {
        var newToken = token.replace("Bearer ", "");
        if (!jwtProvider.validate(newToken)) {
            return null;
        }
        String email = jwtProvider.getEmailFromToken(newToken);
        if (!userRepo.findByEmail(email).isPresent()) {
            return null;
        }
        return new TokenDTO(newToken);
    }

    public String emailFromToken(String token) {
        var newToken = token.replace("Bearer ", "");
        String email = jwtProvider.getEmailFromToken(newToken);
        if (email.isEmpty()) {
            return null;
        }
        return email;
    }

    public Optional<User> getUserFromToken(String token){
        String email = this.emailFromToken(token);
        return userRepo.findByEmail(email);
    }
}