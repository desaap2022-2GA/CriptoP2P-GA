package ar.edu.unq.desapp.grupoa022022.backenddesappapi.aspects.log_data;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.TokenService;
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

    /*timestamp,
    user,
    operación/metodo,
    parámetros,
    tiempoDeEjecicion>
    de los servicios publicados con Spring utilizando Log4j/logback*/
    /// ANNOTATION POINTCUT////
    @Around("@annotation(LogMethodData)")
    public Object logMethodData(ProceedingJoinPoint joinPoint) throws Throwable {
        //Object proceed;
        logger.info("/////// START LOG METHOD DATA //////");
        long startTime = System.currentTimeMillis();
        String email = tokenService.emailFromToken(joinPoint.getArgs()[0].toString());
        logger.info("/////// USER-EMAIL: " + email + " //////");
        String operation = String.valueOf(joinPoint.getSignature());
        logger.info("/////// OPERATION: " + operation + " //////");
        String parameters = Arrays.toString(joinPoint.getArgs());
        logger.info("/////// PARAMETERS: " + parameters + " //////");

        //proceed =
        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - startTime;
        logger.info("/////// " + joinPoint.getSignature() + " executed in " + executionTime + "ms " + proceed.toString());
        logger.info("/////// FINISH LOG METHOD DATA ///////");

        return proceed;
    }
}
