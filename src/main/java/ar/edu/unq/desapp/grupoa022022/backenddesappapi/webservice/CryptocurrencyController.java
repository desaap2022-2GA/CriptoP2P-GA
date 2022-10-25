package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyLastQuote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.ICryptocurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cryptocurrencies")
public class CryptocurrencyController {

    @Autowired
    ICryptocurrencyService cryptocurrencyService;

    @PostMapping
    public Cryptocurrency createCryptocurrency(@RequestBody @Valid CryptocurrencyRegister cryptocurrencyRegister) {
        return cryptocurrencyService.create(cryptocurrencyRegister);
    }

    @GetMapping
    public List<Cryptocurrency> listAllCryptocurrencies() {
        return cryptocurrencyService.getAll();
    }

    @GetMapping("/latest_quotes")
    public List<CryptocurrencyLastQuote> listLastQuoteOfCryptocurrencies() {
        return cryptocurrencyService.latestQuotes();
    }
}