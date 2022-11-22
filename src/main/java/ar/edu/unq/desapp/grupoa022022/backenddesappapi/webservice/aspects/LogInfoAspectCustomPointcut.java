package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice.aspects;
/*
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceNotInAValidRangeException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Aspect
@Component
public class LogInfoAspectCustomPointcut {
    static Logger logger = LoggerFactory.getLogger(LogInfoAspectCustomPointcut.class);

    /// ANNOTATION POINTCUT////
    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTimeAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        String ERROR_MESSAGE = "errorMessage";
        Object proceed;
        logger.info("/////// AROUND START  logExecutionTime annotation //////");
        long start = System.currentTimeMillis();
        try {
            proceed = joinPoint.proceed();
        } catch (PriceNotInAValidRangeException e) {

//        } catch (Throwable e) {

            Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", new Date());
            body.put("status", 400);
            body.put(ERROR_MESSAGE, e.getMessage());
            return new ResponseEntity<>(body, getHeaders(), HttpStatus.BAD_REQUEST);
        }
        long executionTime = System.currentTimeMillis() - start;
        logger.info("/////// " + joinPoint.getSignature() + " executed in " + executionTime + "ms " + proceed.toString());
        logger.info("/////// AROUND FINISH  logExecutionTime annotation ///////");
        return null;
    }

    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }
}*/
