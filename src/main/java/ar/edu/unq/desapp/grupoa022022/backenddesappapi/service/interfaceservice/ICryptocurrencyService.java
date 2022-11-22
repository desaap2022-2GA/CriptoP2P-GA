package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyLastQuote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ICryptocurrencyService {
    Cryptocurrency create(CryptocurrencyRegister cryptocurrencyRegister);

    void delete(int id);

    void update(Cryptocurrency cryptocurrency);

    void deleteAll();

    List<Cryptocurrency> getAll();

    Cryptocurrency findById(int id) throws ResourceNotFoundException;

    List<CryptocurrencyLastQuote> latestQuotes();

    List<CryptocurrencyLastQuote> oneDayQuotes(Integer id) throws ResourceNotFoundException;

//    List<CryptocurrencyLastQuote> latestQuotes10Min();
}
