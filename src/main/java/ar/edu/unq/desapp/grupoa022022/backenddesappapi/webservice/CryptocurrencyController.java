package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyLastQuote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.ICryptocurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cryptocurrencies")
public class CryptocurrencyController {

    @Autowired
    ICryptocurrencyService cryptocurrencyService;

    @Operation(summary = "Create a cryptocurrency")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public Cryptocurrency createCryptocurrency(@RequestHeader(value = "Authorization") String token, @RequestBody @Valid CryptocurrencyRegister cryptocurrencyRegister) {
        return cryptocurrencyService.create(cryptocurrencyRegister);
    }

    @Operation(summary = "List all cryptocurrency")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public List<Cryptocurrency> listAllCryptocurrencies(@RequestHeader(value = "Authorization") String token) {
        return cryptocurrencyService.getAll();
    }

    @Operation(summary = "List last quote of cryptocurrencies")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/latest_quotes")
    public List<CryptocurrencyLastQuote> listLastQuoteOfCryptocurrencies(@RequestHeader(value = "Authorization") String token) {
        return cryptocurrencyService.latestQuotes();
    }
}