package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Intention;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IIntentionRepo extends JpaRepository<Intention, Integer> {
}
