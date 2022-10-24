package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyLastQuote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.ICryptocurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
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
    /*
        @GetMapping
        public List<CryptocurrencyLastQuote> listLastQuoteOfCryptocurrencies() {
            RestTemplate restTemplate = new RestTemplate();
        List<CryptocurrencyLastQuote> cryptocurrencyLastQuotesList = new ArrayList<>();

        List<String> cryptocurrencyNameList = Arrays.asList("ALICEUSDT", "MATICUSDT", "AXSUSDT", "AAVEUSDT", "ATOMUSDT", "NEOUSDT", "DOTUSDT"
                , "ETHUSDT", "CAKEUSDT", "BTCUSDT", "BNBUSDT", "ADAUSDT", "TRXUSDT", "AUDIOUSDT");

        cryptocurrencyNameList.forEach(name -> {
            String url = "https://api1.binance.com/api/v3/ticker/price?symbol={symbol}";
            url.replace("{symbol}", name);
            System.out.println("Url is : "+url);

            ResponseEntity<CryptocurrencyLastQuote> cryptoCurrencyLastQuote =
                    restTemplate.getForEntity(url, CryptocurrencyLastQuote.class);

            System.out.println("Response status code is: "+cryptoCurrencyLastQuote.getStatusCode());
            CryptocurrencyLastQuote responseBean = cryptoCurrencyLastQuote.getBody();

            cryptocurrencyLastQuotesList.add(responseBean);
        });
        return cryptocurrencyLastQuotesList;
    }*/
}