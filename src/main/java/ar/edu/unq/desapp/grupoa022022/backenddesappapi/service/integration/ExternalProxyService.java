package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.integration;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CasaDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyLastQuoteDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.ObjectCasaDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DateTimeInMilliseconds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExternalProxyService {

    RestTemplate restTemplate = new RestTemplate();

    @Value("${integration.dolarsi.api.url.valoresprincipales}")
    private String dolarSiApiURL;

    @Value("${integration.binance.api.url}")
    private String binanceApiURL;

    public double dolarSiLatestQuote() {

        ResponseEntity<ObjectCasaDTO[]> objetcsCasa =
                restTemplate.getForEntity(dolarSiApiURL, ObjectCasaDTO[].class);
        CasaDTO responseBean = Objects.requireNonNull(objetcsCasa.getBody())[1].getCasa();
        String quoteDollarBlueSale = responseBean.getVenta().replace(",", ".");
        return Double.parseDouble(quoteDollarBlueSale);
    }

    public List<CryptocurrencyLastQuoteDTO> binanceLatestQuotes() {

        List<CryptocurrencyLastQuoteDTO> cryptocurrencyLastQuotesList = new ArrayList<>();

        List<String> cryptocurrencyNameList = Arrays.asList("ALICEUSDT", "MATICUSDT", "AXSUSDT", "AAVEUSDT", "ATOMUSDT", "NEOUSDT", "DOTUSDT"
                , "ETHUSDT", "CAKEUSDT", "BTCUSDT", "BNBUSDT", "ADAUSDT", "TRXUSDT", "AUDIOUSDT");

        cryptocurrencyNameList.forEach(name -> {
            String url = binanceApiURL + "ticker/price?symbol=" + name;

            ResponseEntity<CryptocurrencyLastQuoteDTO> cryptoCurrencyLastQuote =
                    restTemplate.getForEntity(url, CryptocurrencyLastQuoteDTO.class);

            CryptocurrencyLastQuoteDTO responseBean = cryptoCurrencyLastQuote.getBody();
            responseBean.setDateTime(String.valueOf(new Date()));

            cryptocurrencyLastQuotesList.add(responseBean);
        });
        return cryptocurrencyLastQuotesList;
    }

    public List<CryptocurrencyLastQuoteDTO> binance24hsQuotesForCryptocurrency(Cryptocurrency cryptocurrency) {

        DateTimeInMilliseconds dateUtils = new DateTimeInMilliseconds();

        String url = binanceApiURL + "klines?symbol=" + cryptocurrency.getName() +
                "&interval=1h&startTime=" + dateUtils.getCurrentTimeMinusOneDayInMilliseconds() +
                "&endTime=" + dateUtils.getCurrentTimeInMilliseconds();

        ResponseEntity<List[]> responseList =
                restTemplate.getForEntity(url, List[].class);

        return Arrays.stream(Objects.requireNonNull(responseList.getBody())).map(
                obj -> new CryptocurrencyLastQuoteDTO(cryptocurrency.getName(),
                        obj.get(4) + "",
                        dateUtils.convertLongToDate((long) obj.get(0)))).collect(Collectors.toList());
    }
}