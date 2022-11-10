package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyLastQuote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.ICryptocurrencyService;
import io.swagger.v3.oas.annotations.Operation;
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
    @PostMapping
    public Cryptocurrency createCryptocurrency(@RequestBody @Valid CryptocurrencyRegister cryptocurrencyRegister) {
        return cryptocurrencyService.create(cryptocurrencyRegister);
    }

    @Operation(summary = "List all cryptocurrency")
    @GetMapping
    public List<Cryptocurrency> listAllCryptocurrencies() {
        return cryptocurrencyService.getAll();
    }

    @Operation(summary = "List last quote of cryptocurrencies")
    @GetMapping("/latest_quotes")
    public List<CryptocurrencyLastQuote> listLastQuoteOfCryptocurrencies() {
        return cryptocurrencyService.latestQuotes();
    }

    @Operation(summary = "List last quote of cryptocurrencies")
    @GetMapping("/latest_quotes10Min")
    public List<CryptocurrencyLastQuote> listLastQuoteOfCryptocurrenciesEvery10Min() {
        return cryptocurrencyService.latestQuotes10Min();
    }
}