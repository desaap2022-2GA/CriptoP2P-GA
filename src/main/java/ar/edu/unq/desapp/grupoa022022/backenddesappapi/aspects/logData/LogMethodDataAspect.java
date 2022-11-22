package ar.edu.unq.desapp.grupoa022022.backenddesappapi.aspects.logData;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.TokenService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.JwtProvider;
import io.jsonwebtoken.Jwts;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${jwt.secret}")
    private String secret;
    /*timestamp,
    user,
    operación/metodo,
    parámetros,
    tiempoDeEjecicion>
    de los servicios publicados con Spring utilizando Log4j/logback*/
    /// ANNOTATION POINTCUT////
    @Around("@annotation(LogMethodData)")
    public Object LogMethodData(ProceedingJoinPoint joinPoint) throws Throwable {
        //Object proceed;
        logger.info("/////// START LOG METHOD DATA //////");
        long startTime = System.currentTimeMillis();
        String email0 = tokenService.emailFromToken(joinPoint.getArgs()[0].toString());
        //String email = new JwtProvider().getEmailFromToken(newToken.toString());
        System.out.println("email prueba: " + email0);
//        User user = tokenService.findUserByEmail(email).get();
//        logger.info("/////// USER: " + user + " //////");
        logger.info("/////// USER-EMAIL: " + email0 + " //////");
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
