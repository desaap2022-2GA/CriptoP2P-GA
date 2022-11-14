package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.QuoteRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.TokenDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Quote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.QuoteService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.TokenService;
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

    @Autowired
    TokenService tokenService;

    @Operation(summary = "Create a quote")
    @PostMapping
    public ResponseEntity<Quote> createQuote(@RequestHeader(value = "Authorization") String token, @RequestBody @Valid QuoteRegister quoteRegister) throws ResourceNotFound {
        TokenDTO tokenDTO = tokenService.validate(token);
        if (tokenDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(quoteService.create(quoteRegister.getCryptocurrencyId(), quoteRegister.getPrice()));
    }

    @Operation(summary = "List all quotes")//eliminar ya esta en otro endpoint
    @GetMapping
    public ResponseEntity<List<Quote>> listAllQuotes(@RequestHeader(value = "Authorization") String token) {
        TokenDTO tokenDTO = tokenService.validate(token);
        if (tokenDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(quoteService.getAll());
    }

    @Operation(summary = "Search for a quote by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Quote> getQuoteById(@RequestHeader(value = "Authorization") String token, @PathVariable("id") Integer id) throws ResourceNotFound {
        TokenDTO tokenDTO = tokenService.validate(token);
        if (tokenDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(quoteService.findById(id));
    }
}