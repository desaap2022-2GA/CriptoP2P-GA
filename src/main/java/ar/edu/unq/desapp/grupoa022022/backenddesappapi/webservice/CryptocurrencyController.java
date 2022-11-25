package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.aspects.log_data.LogMethodData;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyLastQuoteDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyRegisterDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.ICryptocurrencyService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @LogMethodData
    @Operation(summary = "Create a cryptocurrency")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<Cryptocurrency> createCryptocurrency(@RequestHeader(value = "Authorization") String token, @RequestBody @Valid CryptocurrencyRegisterDTO cryptocurrencyRegisterDTO) {
        return ResponseEntity.ok(cryptocurrencyService.create(cryptocurrencyRegisterDTO));

    }

    @Operation(summary = "List all cryptocurrency")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping

    public ResponseEntity<List<Cryptocurrency>> listAllCryptocurrencies(@RequestHeader(value = "Authorization") String token) {
        return ResponseEntity.ok(cryptocurrencyService.getAll());
    }

    @Operation(summary = "List last quote of cryptocurrencies")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/latest_quotes")
    public ResponseEntity<List<CryptocurrencyLastQuoteDTO>> listLastQuoteOfCryptocurrencies(@RequestHeader(value = "Authorization") String token) {
        return ResponseEntity.ok(cryptocurrencyService.latestQuotes());
    }

    @Operation(summary = "List 24hs quotes of cryptocurrency")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/oneday_quotes/{id}")
    public ResponseEntity<List<CryptocurrencyLastQuoteDTO>> list24hsQuotesOfCryptocurrency(@RequestHeader(value = "Authorization") String token, @PathVariable("id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(cryptocurrencyService.oneDayQuotes(id));
    }
}