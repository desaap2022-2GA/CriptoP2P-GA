package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import java.util.List;

public interface IQuoteService {

    Quote create(int cryptocurrencyId, Double price) throws ResourceNotFound;

    void update(Quote quote);

    void delete(int id) throws ResourceNotFound;

    void deleteAll();

    List<Quote> getAll();

    Quote findById(int id) throws ResourceNotFound;
}
