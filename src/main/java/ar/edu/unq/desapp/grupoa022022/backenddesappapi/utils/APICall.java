package ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.Casa;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyLastQuote;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.ObjectCasa;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

public class APICall {

    RestTemplate restTemplate = new RestTemplate();

    @Value("${integration.dolarsi.api.url.valoresprincipales}")
    private String dolarSiApiURL;

    @Value("${integration.binance.api.url}")
    private String binanceApiURL;

    public double dolarSiLatestQuote() {

        ResponseEntity<ObjectCasa[]> objetcsCasa =
                restTemplate.getForEntity(dolarSiApiURL, ObjectCasa[].class);
        Casa responseBean = Objects.requireNonNull(objetcsCasa.getBody())[1].getCasa();
        String quoteDollarBlueSale = responseBean.getVenta().replace(",", ".");
        return Double.parseDouble(quoteDollarBlueSale);
    }

    public List<CryptocurrencyLastQuote> binanceLatestQuotes() {

        List<CryptocurrencyLastQuote> cryptocurrencyLastQuotesList = new ArrayList<>();

        List<String> cryptocurrencyNameList = Arrays.asList("ALICEUSDT", "MATICUSDT", "AXSUSDT", "AAVEUSDT", "ATOMUSDT", "NEOUSDT", "DOTUSDT"
                , "ETHUSDT", "CAKEUSDT", "BTCUSDT", "BNBUSDT", "ADAUSDT", "TRXUSDT", "AUDIOUSDT");

        cryptocurrencyNameList.forEach(name -> {
            String url = binanceApiURL + "ticker/price?symbol=" + name;

            ResponseEntity<CryptocurrencyLastQuote> cryptoCurrencyLastQuote =
                    restTemplate.getForEntity(url, CryptocurrencyLastQuote.class);

            CryptocurrencyLastQuote responseBean = cryptoCurrencyLastQuote.getBody();
            responseBean.setDate(String.valueOf(new Date()));

            cryptocurrencyLastQuotesList.add(responseBean);
        });
        return cryptocurrencyLastQuotesList;
    }

    public List<CryptocurrencyLastQuote> binance24hsQuotesForCryptocurrency(Cryptocurrency cryptocurrency) {

        DateTimeInMilliseconds dateUtils = new DateTimeInMilliseconds();

        String url = binanceApiURL + "klines?symbol=" + cryptocurrency.getName() +
                "&interval=1h&startTime=" + dateUtils.getCurrentTimeMinusOneDayInMilliseconds() +
                "&endTime=" + dateUtils.getCurrentTimeInMilliseconds();

        ResponseEntity<List[]> responseList =
                restTemplate.getForEntity(url, List[].class);

        return Arrays.stream(Objects.requireNonNull(responseList.getBody())).map(
                obj -> new CryptocurrencyLastQuote(cryptocurrency.getName(),
                        obj.get(4) + "",
                        dateUtils.convertLongToDate((long) obj.get(0)))).collect(Collectors.toList());
    }
}