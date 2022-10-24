package ar.edu.unq.desapp.grupoa022022.backenddesappapi;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.OperationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
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

    public static void main(String[] args) {
        SpringApplication.run(BackendDesappApiApplication.class, args);
    }

    //	protected final Log logger = LogFactory.getLog(getClass());
/*    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
*/
    @Value("${spring.datasource.driverClassName:NONE}")
    private String className;

    @PostConstruct
    public void initialize() throws PriceNotInAValidRange, IntentionAlreadyTaken, ResourceNotFound, PriceExceedVariationWithRespectIntentionTypeLimits, InvalidState {
        if (className.equals("org.h2.Driver")) {
//			logger.info("Init Data Using H2 DB");
            fireInitialData();
        }
    }

    private void fireInitialData() throws PriceNotInAValidRange, ResourceNotFound, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits, InvalidState {

        //USERS
        User user = userService.saveToDataBase(new UserRegister("Paston", "Gaudio", "gaudio@yahoo.com",
                "Av Libertador 5000, CABA", "1234", "6352879863528798635287",
                "Xwf5u5ef"));

        User user2 = userService.saveToDataBase(new UserRegister("Martin", "Fierro", "fierro@gmail.com",
                "Av Cordoba 3000, CABA", "1234", "6352879863528798635288",
                "Zwf5u5ef"));

        //CRYPTOCURRENCIES

        Cryptocurrency cryptocurrency = cryptocurrencyService.create(new CryptocurrencyRegister("DAI", 320.38));

        Cryptocurrency cryptocurrency2 = cryptocurrencyService.create(new CryptocurrencyRegister("BITCOIN", 5840798.98));

        List<String> cryptocurrencyNameList = Arrays.asList("ALICEUSDT", "MATICUSDT", "AXSUSDT", "AAVEUSDT", "ATOMUSDT", "NEOUSDT", "DOTUSDT"
                , "ETHUSDT", "CAKEUSDT", "BTCUSDT", "BNBUSDT", "ADAUSDT", "TRXUSDT", "AUDIOUSDT");

        cryptocurrencyNameList.forEach(name -> cryptocurrencyService.create(new CryptocurrencyRegister(name, 0.00)));

        //QUOTES
        quoteService.create(cryptocurrency.getId(), 305.00);

        quoteService.create(cryptocurrency2.getId(), 5607166.15);

        //INTENTIONS
        Intention intention = intentionService.create(new IntentionRegister(IntentionType.BUY, cryptocurrency.getId(),
                289.75, 2, user.getId()));

        intentionService.create(new IntentionRegister(IntentionType.SELL, cryptocurrency2.getId(),
                5326807.85, 2, user.getId()));

        //OPERATION
        Operation operation = operationService.create(new OperationRegister(intention.getId(), user2.getId()));

        //OPERATION PAID
        operationService.modify(new OperationModify(operation.getId(), OperationState.PAID, user.getId()));

        //OPERATION CRYPTOSENT (TERMINATED)
        operationService.modify(new OperationModify(operation.getId(), OperationState.CRYPTOSENT, user2.getId()));
    }
}