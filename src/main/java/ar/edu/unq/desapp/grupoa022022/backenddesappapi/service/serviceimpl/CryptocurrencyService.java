package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.ICryptocurrencyRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IQuoteRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.ICryptocurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CryptocurrencyService implements ICryptocurrencyService {

    @Autowired
    private ICryptocurrencyRepo cryptocurrencyRepo;

    @Autowired
    private IQuoteRepo quoteRepo;

    @Override
    public Cryptocurrency create(String name) {
        Cryptocurrency cryptocurrency = new Cryptocurrency(name);
        return cryptocurrencyRepo.save(cryptocurrency);
    }

    @Override
    public void delete(int id) {
        cryptocurrencyRepo.deleteById(id);
    }

    @Override
    public void update(Cryptocurrency cryptocurrency) {
        cryptocurrencyRepo.save(cryptocurrency);
    }

    @Override
    public void deleteAll() {
        cryptocurrencyRepo.deleteAll();
    }

    @Override
    public Quote getLatestQuote(Cryptocurrency cryptocurrency) throws ResourceNotFound {
        return cryptocurrency.latestQuote();
    }

    @Override
    public Set<Quote> last24hoursQuotes(Cryptocurrency cryptocurrency) {
        return cryptocurrency.last24HoursQuotes();
    }

    @Override
    public Set<Intention> getReferencedIntentions(Cryptocurrency cryptocurrency) {
        return cryptocurrency.getIntentions();
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

    @Override
    public Set<Quote> getQuotes(Cryptocurrency cryptocurrency) {
        return cryptocurrency.getQuotes();
    }
}
