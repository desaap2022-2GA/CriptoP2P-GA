package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/quote")
public class QuoteController {

    @Autowired
    QuoteService quoteService;

    @PostMapping
    public Quote createQuote(@RequestBody @Valid Quote quote) throws ResourceNotFound {
        return quoteService.create(quote.getCryptocurrency(), quote.getPrice());
    }

    @PutMapping(value = "/quotes/{id}")
    public void modifyQuote(@RequestBody @Valid Quote quote) throws ResourceNotFound {
        quoteService.update(quote);
    }

    @DeleteMapping(value = "/quotes/{id}")
    public void deleteQuote(@PathVariable("id") int id) throws ResourceNotFound {
        quoteService.delete(id);
    }

    @DeleteMapping(value = "/all")
    public void deleteAllQuotes() {
        quoteService.deleteAll();
    }

    @GetMapping
    public List<Quote> listAllQuotes() {
        return quoteService.getAll();
    }

    @GetMapping(value = "/quotes/{id}")
    public Quote getUserById(@PathVariable("id") Integer id) throws ResourceNotFound {
        return quoteService.findById(id);
    }
}