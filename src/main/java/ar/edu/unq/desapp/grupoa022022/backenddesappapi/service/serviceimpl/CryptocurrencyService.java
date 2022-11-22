package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyLastQuote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.ICryptocurrencyRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IQuoteRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.integration.ExternalProxyService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.ICryptocurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptocurrencyService implements ICryptocurrencyService {
    @Autowired
    private ICryptocurrencyRepo cryptocurrencyRepo;

    @Autowired
    private IQuoteRepo quoteRepo;

    @Autowired
    private ExternalProxyService externalProxyService;

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
        return cryptocurrencyRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Cryptocurrency findById(int id) throws ResourceNotFoundException {
        return cryptocurrencyRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cryptocurrency not found with id " + id)
        );
    }

    @Override
    @Cacheable("cryptoCurrency")
    public List<CryptocurrencyLastQuote> latestQuotes() {
        return externalProxyService.binanceLatestQuotes();
    }

    @Override
    public List<CryptocurrencyLastQuote> oneDayQuotes(Integer id) throws ResourceNotFoundException {
        Cryptocurrency cryptocurrency = findById(id);
        //return cryptocurrency.last24HoursQuotes();
        return externalProxyService.binance24hsQuotesForCryptocurrency(cryptocurrency);
    }

//    @Scheduled(cron = "* * * * * ") //Ahora en un min
    // "*/10 * * * * "cada 10 minutos - application.properties
    // "${cron.expression}
//    @CachePut("cryptoCurrency")
    //@Cacheable("cryptoCurrency")
//    @Override
//    public List<CryptocurrencyLastQuote> latestQuotes10Min(){
    //System.out.println("pase");
//        return latestQuotes();
    //return null;
//    }
}