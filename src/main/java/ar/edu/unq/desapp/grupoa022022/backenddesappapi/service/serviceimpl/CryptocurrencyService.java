package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.ICryptocurrencyRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IQuoteRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.ICryptocurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptocurrencyService implements ICryptocurrencyService {

    @Autowired
    private ICryptocurrencyRepo cryptocurrencyRepo;

    @Autowired
    private IQuoteRepo quoteRepo;

    @Override
    public Cryptocurrency create(CryptocurrencyRegister cryptocurrencyRegister) {
        Cryptocurrency cryptocurrency = new Cryptocurrency(cryptocurrencyRegister.getName());
        Cryptocurrency savedCryptocurrency = cryptocurrencyRepo.save(cryptocurrency);
        Quote quote = new Quote(savedCryptocurrency, cryptocurrencyRegister.getPrice());
        quoteRepo.save(quote);
        return savedCryptocurrency;
    }

    @Override
    public void delete(int id) {
        cryptocurrencyRepo.findById(id).ifPresent(cryptocurrency -> {
            cryptocurrency.getQuotes().forEach(quote -> quoteRepo.deleteById(quote.getId()));
            cryptocurrencyRepo.deleteById(id);
        });
    }

    @Override
    public void update(Cryptocurrency cryptocurrency) {
        cryptocurrencyRepo.save(cryptocurrency);
    }

    @Override
    public void deleteAll() {
        cryptocurrencyRepo.findAll().forEach(cryptocurrency -> this.delete(cryptocurrency.getId()));
    }

    @Override
    public List<Cryptocurrency> getAll() {
        return cryptocurrencyRepo.findAll();
    }

    @Override
    public Cryptocurrency findById(int id) throws ResourceNotFound {
        return cryptocurrencyRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("Cryptocurrency not found with id " + id)
        );
    }
}
