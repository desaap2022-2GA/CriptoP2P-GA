package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.QuoteRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.QuoteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Quote> createQuote(@RequestBody @Valid QuoteRegister quoteRegister) throws ResourceNotFound {
        return ResponseEntity.ok(quoteService.create(quoteRegister.getCryptocurrencyId(), quoteRegister.getPrice()));
    }

    @Operation(summary = "List all quotes")//eliminar ya esta en otro endpoint
    @GetMapping
    public ResponseEntity<List<Quote>> listAllQuotes() {
        return ResponseEntity.ok(quoteService.getAll());
    }

    @Operation(summary = "Search for a quote by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Quote> getQuoteById(@PathVariable("id") Integer id) throws ResourceNotFound {
        return ResponseEntity.ok(quoteService.findById(id));
    }
}