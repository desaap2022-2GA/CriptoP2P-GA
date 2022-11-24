package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyLastQuoteDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyRegisterDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ICryptocurrencyService {
    Cryptocurrency create(CryptocurrencyRegisterDTO cryptocurrencyRegisterDTO);

    void delete(int id);

    void update(Cryptocurrency cryptocurrency);

    void deleteAll();

    List<Cryptocurrency> getAll();

    Cryptocurrency findById(int id) throws ResourceNotFoundException;

    List<CryptocurrencyLastQuoteDTO> latestQuotes();

    List<CryptocurrencyLastQuoteDTO> oneDayQuotes(Integer id) throws ResourceNotFoundException;
}
