package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.QuoteRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.QuoteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/quotes")
public class QuoteController {

    @Autowired
    QuoteService quoteService;
    @Operation(summary = "Create a quote")
    @PostMapping
    public Quote createQuote(@RequestBody @Valid QuoteRegister quoteRegister) throws ResourceNotFound {
        return quoteService.create(quoteRegister.getCryptocurrencyId(), quoteRegister.getPrice());
    }
    @Operation(summary = "List all quotes")
    @GetMapping
    public List<Quote> listAllQuotes() {
        return quoteService.getAll();
    }

    @Operation(summary = "Search for a quote by id")
    @GetMapping(value = "/{id}")
    public Quote getQuoteById(@PathVariable("id") Integer id) throws ResourceNotFound {
        return quoteService.findById(id);
    }
}