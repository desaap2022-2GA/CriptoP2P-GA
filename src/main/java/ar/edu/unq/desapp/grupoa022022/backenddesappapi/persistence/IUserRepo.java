package ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IUserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);


    Optional<User> findByPassword(String password);

    /*@Query(value = "SELECT name, lastname, numberOperations, reputation FROM desappcriptp2p_10", nativeQuery = true)
    List<User> listUsers();
*/
}
