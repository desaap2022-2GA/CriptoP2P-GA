package ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IQuoteRepo extends JpaRepository<Quote, Integer> {
}