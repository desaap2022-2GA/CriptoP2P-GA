package ar.edu.unq.desapp.grupoa022022.backenddesappapi;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.integration.ExternalProxyService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.OperationState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class BackendDesappApiApplication {

    @Autowired
    IUserService userService;

    @Autowired
    ICryptocurrencyService cryptocurrencyService;

    @Autowired
    IQuoteService quoteService;

    @Autowired
    IIntentionService intentionService;

    @Autowired
    IOperationService operationService;

    @Autowired
    private ExternalProxyService externalProxyService;

    public static void main(String[] args) {
        SpringApplication.run(BackendDesappApiApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    protected final Logger logger = LogManager.getLogger(getClass());

    @Scheduled(cron = "0 */10 * * * *")//10 minutos
    @CachePut("cryptoCurrency")

    public List<CryptocurrencyLastQuoteDTO> updateCryptocurrenciesQuotes() {
        logger.info("Quotes UPDATED" + new Date());
        return externalProxyService.binanceLatestQuotes();
    }

    @Value("${spring.profiles.active}")
    private String className;

    @PostConstruct
    public void initialize() throws PriceNotInAValidRangeException, IntentionAlreadyTakenException, ResourceNotFoundException, PriceExceedVariationWithRespectIntentionTypeLimitsException, InvalidStateException, UserValidationException {
        if (className.equals("dev")) {
            logger.info("Init Data Using H2 DB");
            fireInitialData();
        }
    }

    private void fireInitialData() throws PriceNotInAValidRangeException, ResourceNotFoundException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException, InvalidStateException, UserValidationException {

        //USERS
        User user = userService.saveToDataBase(new UserRegisterDTO("Paston", "Gaudio", "gaudio@yahoo.com",
                "Av Libertador 5000, CABA", "Ruben?", "6352879863528798635287",
                "Xwf5u5ef"));

        User user2 = userService.saveToDataBase(new UserRegisterDTO("Martin", "Fierro", "fierro@gmail.com",
                "Av Cordoba 3000, CABA", "Maria!", "6352879863528798635288",
                "Zwf5u5ef"));

        //CRYPTOCURRENCIES

        Cryptocurrency cryptocurrency = cryptocurrencyService.create(new CryptocurrencyRegisterDTO("DAI", 320.38));

        Cryptocurrency cryptocurrency2 = cryptocurrencyService.create(new CryptocurrencyRegisterDTO("BITCOIN", 5840798.98));

        List<String> cryptocurrencyNameList = Arrays.asList("ALICEUSDT", "MATICUSDT", "AXSUSDT", "AAVEUSDT", "ATOMUSDT", "NEOUSDT", "DOTUSDT"
                , "ETHUSDT", "CAKEUSDT", "BTCUSDT", "BNBUSDT", "ADAUSDT", "TRXUSDT", "AUDIOUSDT");

        cryptocurrencyNameList.forEach(name -> cryptocurrencyService.create(new CryptocurrencyRegisterDTO(name, 0.00)));

        //QUOTES
        quoteService.create(cryptocurrency.getId(), 305.00);

        quoteService.create(cryptocurrency2.getId(), 5607166.15);

        //INTENTIONS
        Intention intention = intentionService.create(new IntentionRegisterDTO(IntentionType.BUY, cryptocurrency.getId(),
                289.75, 2, user.getId()));

        intentionService.create(new IntentionRegisterDTO(IntentionType.SELL, cryptocurrency2.getId(),
                5326807.85, 2, user.getId()));

        intentionService.create(new IntentionRegisterDTO(IntentionType.SELL, cryptocurrency2.getId(),
                5726807.85, 1, user.getId()));

        //OPERATION
        Operation operation = operationService.create(new OperationRegisterDTO(intention.getId(), user2.getId()));

        //OPERATION PAID
        operationService.modify(new OperationModifyDTO(operation.getId(), OperationState.PAID, user.getId()));

        //OPERATION CRYPTOSENT (TERMINATED)
        operationService.modify(new OperationModifyDTO(operation.getId(), OperationState.CRYPTOSENT, user2.getId()));

    }



}

