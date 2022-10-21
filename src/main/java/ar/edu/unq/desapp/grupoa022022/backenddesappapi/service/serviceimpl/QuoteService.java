package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.ICryptocurrencyRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence.IQuoteRepo;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.ICryptocurrencyService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IQuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class QuoteService implements IQuoteService {

    @Autowired
    private IQuoteRepo quoteRepo;
    @Autowired
    private ICryptocurrencyRepo cryptocurrencyRepo;

    @Autowired
    private ICryptocurrencyService cryptocurrencyService;

    @Override
    public Quote create(int cryptocurrencyId, Double price) throws ResourceNotFound {
        Cryptocurrency cryptocurrency = cryptocurrencyService.findById(cryptocurrencyId);
        Quote quote = new Quote(cryptocurrency, price);
        return quoteRepo.save(quote);
    }

    @Override
    public void update(Quote quote) {
        quoteRepo.save(quote);
    }

    @Override
    public void delete(int id) throws ResourceNotFound {
        Quote quote = this.findById(id);
        Cryptocurrency cryptocurrency = quote.getCryptocurrency();
        Set<Quote> quotes = cryptocurrency.getQuotes();
        quotes.remove(quote);
        cryptocurrency.setQuotes(quotes);
        cryptocurrencyRepo.save(cryptocurrency);
        quoteRepo.deleteById(id);
    }

    @Override
    public void deleteAll() {
        quoteRepo.deleteAll();
    }

    @Override
    public List<Quote> getAll() {
        return quoteRepo.findAll();
    }

    @Override
    public Quote findById(int id) throws ResourceNotFound {
        return quoteRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("Quote not found with id " + id)
        );
    }

    @Override
    public boolean intentionPriceInARangeOfFiveUpAndDownRespectToQuotePrice(double intentionPrice, Quote quote) {
        return quote.intentionPriceInARangeOfFiveUpAndDown(intentionPrice);
    }

    @Override
    public boolean intentionPriceHigherThanQuotePrice(double intentionPrice, Quote quote) {
        return quote.intentionPriceHigherThanQuotePrice(intentionPrice);
    }

    @Override
    public boolean intentionPriceLowerThanQuotePrice(double intentionPrice, Quote quote) {
        return quote.intentionPriceLowerThanQuotePrice(intentionPrice);
    }
}
