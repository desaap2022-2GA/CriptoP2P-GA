package ar.edu.unq.desapp.grupoa022022.backenddesappapi.aspects.log_data;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserRegisterDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.TokenService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.DateTimeInMilliseconds;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
@Order(0)
public class LogMethodDataAspect {
    static Logger logger = LoggerFactory.getLogger(LogMethodDataAspect.class);

    @Autowired
    TokenService tokenService;

    /// ANNOTATION POINTCUT////
    @Around("@annotation(LogMethodData)")
    public Object logMethodData(ProceedingJoinPoint joinPoint) throws Throwable {

        logger.info("/////// START LOG METHOD DATA ");
        long startTime = System.currentTimeMillis();
        String email = emailFromRegisterOrLogin(joinPoint);
        String operation = String.valueOf(joinPoint.getSignature());
        String parameters = Arrays.toString(joinPoint.getArgs());
        String dateTime = new DateTimeInMilliseconds().convertLongToDate(startTime);

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - startTime;
        logger.info("/////// TIMESTAMP: " + dateTime +
                " USER-EMAIL: " + email +
                " OPERATION: " + operation +
                " PARAMETERS: " + parameters +
                " EXECUTION TIME: " + executionTime + "ms //////");
        logger.info(" FINISH LOG METHOD DATA ///////");

        return proceed;
    }

    private String emailFromRegisterOrLogin(ProceedingJoinPoint joinPoint) {
        switch (joinPoint.getArgs()[0].getClass().toString()) {
            case "class ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserRegisterDTO" -> {
                UserRegisterDTO user = (UserRegisterDTO) joinPoint.getArgs()[0];
                return user.getEmail();
            }
            case "class ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.UserDTO" -> {
                UserDTO user = (UserDTO) joinPoint.getArgs()[0];
                return user.getEmail();
            }
            default -> {
                return tokenService.emailFromToken(joinPoint.getArgs()[0].toString());
            }
        }
    }
}