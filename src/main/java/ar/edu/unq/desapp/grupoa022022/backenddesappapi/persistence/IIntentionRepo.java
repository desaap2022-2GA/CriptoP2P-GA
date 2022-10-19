package ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IIntentionRepo extends JpaRepository<Intention, Integer> {


    @Query(value = "SELECT * FROM intentionp2p_des_CG intention WHERE intention.taken := 'false'", nativeQuery = true)
    List<Intention> getIntentionActive();

    public Intention getIntetnionById(int id);
}
