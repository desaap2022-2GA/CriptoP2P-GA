package ar.edu.unq.desapp.grupoa022022.backenddesappapi;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.EmailAlreadyExists;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.*;
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
    public void initialize() throws EmailAlreadyExists {
        if (className.equals("org.h2.Driver")) {
//			logger.info("Init Data Using H2 DB");
            fireInitialData();
        }
    }

    private void fireInitialData() throws EmailAlreadyExists {
        UserRegister userRegister = new UserRegister("Paston", "Gaudio", "gaudio@yahoo.com",
                "Av Libertador 5000, CABA", "1111", "6352879863528798635287",
                "Xwf5u5ef");
        userService.create(userRegister);
    }

}
