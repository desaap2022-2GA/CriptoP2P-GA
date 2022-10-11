package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;

import java.util.List;
import java.util.Set;

public interface ICryptocurrencyService {

    Cryptocurrency create(String name);

    void delete(int id);

    void update(Cryptocurrency cryptocurrency);

    void deleteAll();

    Quote getLatestQuote(Cryptocurrency cryptocurrency) throws ResourceNotFound;

    Set<Quote> last24hoursQuotes(Cryptocurrency cryptocurrency);

    Set<Intention> getReferencedIntentions(Cryptocurrency cryptocurrency);

    List<Cryptocurrency> getAll();

    Cryptocurrency findById(int id) throws ResourceNotFound;


    Set<Quote> getQuotes(Cryptocurrency cryptocurrency);
}
