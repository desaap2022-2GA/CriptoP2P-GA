package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyLastQuote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.TokenDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.ICryptocurrencyService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.TokenService;
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

    @Autowired
    TokenService tokenService;

    @Operation(summary = "Create a cryptocurrency")
    @PostMapping
    public ResponseEntity<Cryptocurrency> createCryptocurrency(@RequestHeader(value = "Authorization") String token, @RequestBody @Valid CryptocurrencyRegister cryptocurrencyRegister) {
        TokenDTO tokenDTO = tokenService.validate(token);
        if (tokenDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(cryptocurrencyService.create(cryptocurrencyRegister));
    }

    @Operation(summary = "List all cryptocurrency")
    @GetMapping
    public ResponseEntity<List<Cryptocurrency>> listAllCryptocurrencies(@RequestHeader(value = "Authorization") String token) {
        TokenDTO tokenDTO = tokenService.validate(token);
        if (tokenDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(cryptocurrencyService.getAll());
    }

    @Operation(summary = "List last quote of cryptocurrencies")
    @GetMapping("/latest_quotes")
    public ResponseEntity<List<CryptocurrencyLastQuote>> listLastQuoteOfCryptocurrencies(@RequestHeader(value = "Authorization") String token) {
        TokenDTO tokenDTO = tokenService.validate(token);
        if (tokenDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(cryptocurrencyService.latestQuotes());
    }
/*
    @Operation(summary = "List last quote of cryptocurrencies")
    @GetMapping("/latest_quotes10Min")
    public List<CryptocurrencyLastQuote> listLastQuoteOfCryptocurrenciesEvery10Min() {
        return cryptocurrencyService.latestQuotes10Min();
    }*/
}