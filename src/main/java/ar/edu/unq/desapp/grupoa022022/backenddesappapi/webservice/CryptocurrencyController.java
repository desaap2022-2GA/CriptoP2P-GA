package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyLastQuote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.ICryptocurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cryptocurrencies")
public class CryptocurrencyController {

    @Autowired
    ICryptocurrencyService cryptocurrencyService;

    @Operation(summary = "Create a cryptocurrency")
    @PostMapping
    public ResponseEntity<Cryptocurrency> createCryptocurrency(@RequestBody @Valid CryptocurrencyRegister cryptocurrencyRegister) {
        return ResponseEntity.ok(cryptocurrencyService.create(cryptocurrencyRegister));
    }

    @Operation(summary = "List all cryptocurrency")
    @GetMapping
    public ResponseEntity<List<Cryptocurrency>> listAllCryptocurrencies() {
        return ResponseEntity.ok(cryptocurrencyService.getAll());
    }

    @Operation(summary = "List last quote of cryptocurrencies")
    @GetMapping("/latest_quotes")
    public ResponseEntity<List<CryptocurrencyLastQuote>> listLastQuoteOfCryptocurrencies() {
        return ResponseEntity.ok(cryptocurrencyService.latestQuotes());
    }
}