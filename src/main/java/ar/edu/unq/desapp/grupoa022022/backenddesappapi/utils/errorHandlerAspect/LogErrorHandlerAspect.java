package ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.errorHandlerAspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@Aspect
@Component
public class LogErrorHandlerAspect {
    static Logger logger = LoggerFactory.getLogger(LogErrorHandlerAspect.class);

    /// CUSTOM POINTCUT////
    @Around("execution(* ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice..*.*(..))")
    public Object logErrorHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            long executionTime = System.currentTimeMillis();
            logger.info("/////// " + joinPoint.getSignature() + " executed at " + executionTime + "ms ");
            return joinPoint.proceed();
        } catch (Exception e) {
            HttpHeaders headers = HttpHeaders.EMPTY;
            HttpStatus status = HttpStatus.BAD_REQUEST;
            String ERROR_MESSAGE = "errorMessage: ";
            Map<String, Object> body = new LinkedHashMap<>();
            List<String> errors = new LinkedList<>();
            body.put("timestamp", new Date());
            body.put("status", status.value());
            switch (e.getClass().toString()) {
                case "PriceNotInAValidRange.class", "PriceExceedVariationWithRespectIntentionTypeLimits.class",
                        "InvalidState.class", "IntentionAlreadyTaken.class", "ExceptionsUser.class",
                        "EmailAlreadyExists.class", "ResourceNotFound.class" -> errors.add(e.getMessage());
                case "MethodArgumentNotValidException.class", "class javax.validation.ConstraintViolationException" -> {
                    ConstraintViolationException ex = (ConstraintViolationException) e;
                    errors.add(ex.getConstraintViolations().stream().map(ConstraintViolation::getMessageTemplate).toList().toString());;
                }
                default -> errors.add("Exception not handled: " + e.getClass() + " " + e.getMessage() + " " + e);
            }
            body.put(ERROR_MESSAGE, errors);
            long executionTime = System.currentTimeMillis();
            logger.info("/////// EXCEPTION HANDLED " + e.getClass() + " executed at " + executionTime + "ms " +
                    body + " " + status + "///////");
            return new ResponseEntity<>(body, headers, status);
        }
    }
}