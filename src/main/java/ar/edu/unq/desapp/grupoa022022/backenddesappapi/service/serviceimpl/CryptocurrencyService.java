package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyLastQuote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.ICryptocurrencyRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IQuoteRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.ICryptocurrencyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CryptocurrencyService implements ICryptocurrencyService {

    protected final Logger logger = LogManager.getLogger(getClass());
    RestTemplate restTemplate = new RestTemplate();
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
        return cryptocurrencyRepo.findAll(Sort.by(Sort.Direction.ASC,"id"));
    }

    @Override
    public Cryptocurrency findById(int id) throws ResourceNotFound {
        return cryptocurrencyRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("Cryptocurrency not found with id " + id)
        );
    }

    @Override
    @Cacheable("cryptoCurrency")
    public List<CryptocurrencyLastQuote> latestQuotes(){

        List<CryptocurrencyLastQuote> cryptocurrencyLastQuotesList = new ArrayList<>();

        List<String> cryptocurrencyNameList = Arrays.asList("ALICEUSDT", "MATICUSDT", "AXSUSDT", "AAVEUSDT", "ATOMUSDT", "NEOUSDT", "DOTUSDT"
                , "ETHUSDT", "CAKEUSDT", "BTCUSDT", "BNBUSDT", "ADAUSDT", "TRXUSDT", "AUDIOUSDT");

        cryptocurrencyNameList.forEach(name -> {
            String url = "https://api1.binance.com/api/v3/ticker/price?symbol="+name;

            ResponseEntity<CryptocurrencyLastQuote> cryptoCurrencyLastQuote =
                    restTemplate.getForEntity(url, CryptocurrencyLastQuote.class);

            CryptocurrencyLastQuote responseBean = cryptoCurrencyLastQuote.getBody();

            cryptocurrencyLastQuotesList.add(responseBean);
        });
        return cryptocurrencyLastQuotesList;
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