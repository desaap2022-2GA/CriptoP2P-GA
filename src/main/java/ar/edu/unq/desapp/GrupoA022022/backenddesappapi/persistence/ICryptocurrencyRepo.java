package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Cryptocurrency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICryptocurrencyRepo extends JpaRepository<Cryptocurrency, Integer> {
}
