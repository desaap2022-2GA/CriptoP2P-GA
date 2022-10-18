package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import java.util.List;

public interface IQuoteService {

    Quote create(Cryptocurrency cryptocurrency, Double price);

    void update(Quote quote);

    void delete(int id) throws ResourceNotFound;

    void deleteAll();

    List<Quote> getAll();

    Quote findById(int id) throws ResourceNotFound;

    boolean intentionPriceInARangeOfFiveUpAndDownRespectToQuotePrice(double intentionPrice, Quote quote);

    boolean intentionPriceHigherThanQuotePrice(double intentionPrice, Quote quote);

    boolean intentionPriceLowerThanQuotePrice(double intentionPrice, Quote quote);
}
