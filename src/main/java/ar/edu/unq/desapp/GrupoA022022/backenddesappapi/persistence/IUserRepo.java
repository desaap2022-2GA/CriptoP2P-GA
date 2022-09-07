package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepo extends JpaRepository<User, Integer> {
}
