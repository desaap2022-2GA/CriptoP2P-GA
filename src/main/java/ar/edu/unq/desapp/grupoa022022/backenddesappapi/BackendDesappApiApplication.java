package ar.edu.unq.desapp.grupoa022022.backenddesappapi;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.*;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

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

    @Value("${spring.datasource.driverClassName:NONE}")
    private String className;

    @PostConstruct
    public void initialize() throws EmailAlreadyExists, PriceNotInAValidRange, IntentionAlreadyTaken, ResourceNotFound, PriceExceedVariationWithRespectIntentionTypeLimits {
        if (className.equals("org.h2.Driver")) {
//			logger.info("Init Data Using H2 DB");
            fireInitialData();
        }
    }

    private void fireInitialData() throws EmailAlreadyExists, PriceNotInAValidRange, ResourceNotFound, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {

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

        //QUOTES
        quoteService.create(cryptocurrency.getId(), 305.00);

        quoteService.create(cryptocurrency2.getId(), 5607166.15);

        //INTENTIONS
        Intention intention = intentionService.create(new IntentionRegister(IntentionType.BUY, cryptocurrency.getId(),
                289.75, 2, user.getId()));

        intentionService.create(new IntentionRegister(IntentionType.SELL, cryptocurrency2.getId(),
                5326807.85, 2, user.getId()));

        //OPERATIONS
        operationService.create(new OperationRegister(intention.getId(), user2.getId()));
    }
}