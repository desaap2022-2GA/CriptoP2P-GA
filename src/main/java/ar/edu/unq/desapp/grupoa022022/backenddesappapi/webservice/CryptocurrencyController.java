package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.ICryptocurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cryptocurrencies")
public class CryptocurrencyController {

    @Autowired
    ICryptocurrencyService cryptocurrencyService;

    @PostMapping("/{cryptocurrencyName}")
    public Cryptocurrency createCryptocurrency(@PathVariable String cryptocurrencyName) throws ResourceNotFound {
        return cryptocurrencyService.create(cryptocurrencyName);
    }

    @GetMapping
    public List<Cryptocurrency> listAllCryptocurrencies() {
        return cryptocurrencyService.getAll();
    }
}
