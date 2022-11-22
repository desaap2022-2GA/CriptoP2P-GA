package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import java.util.List;

public interface IQuoteService {

    Quote create(int cryptocurrencyId, Double price) throws ResourceNotFoundException;

    void update(Quote quote);

    void delete(int id) throws ResourceNotFoundException;

    void deleteAll();

    List<Quote> getAll();

    Quote findById(int id) throws ResourceNotFoundException;
}
