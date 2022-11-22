package ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.errorHandlerAspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@Aspect
@Component
@Order(1)
public class LogErrorHandlerAspect {
    static Logger logger = LoggerFactory.getLogger(LogErrorHandlerAspect.class);

    /// CUSTOM POINTCUT////
    @Around("execution(* ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice..*.*(..))")
    public Object logErrorHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            HttpHeaders headers = HttpHeaders.EMPTY;
            HttpStatus status = HttpStatus.BAD_REQUEST;
            String ERROR_MESSAGE = "errorMessage: ";
            Map<String, Object> body = new LinkedHashMap<>();
            List<String> errors = new LinkedList<>();
            body.put("timestamp: ", new Date());
            body.put("status: ", status.value());
            System.out.println("dentro de manejador de excepciones" + e.getClass().toString());
            switch (e.getClass().toString()) {
                case "class ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceNotInAValidRangeException",
                        "class ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceExceedVariationWithRespectIntentionTypeLimitsException",
                        "class ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.InvalidStateException",
                        "class ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.IntentionAlreadyTakenException",
                        "class ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ExceptionsUserException",
                        "class ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.EmailAlreadyExistsException",
                        "class ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException" ->
                        errors.add(e.getMessage());
                case "class org.springframework.web.bind.MethodArgumentNotValidException",
                        "class javax.validation.ConstraintViolationException" -> {
                    ConstraintViolationException ex = (ConstraintViolationException) e;
                    errors.add(ex.getConstraintViolations().stream().map(ConstraintViolation::getMessageTemplate).toList().toString());
                    ;
                }
                default -> errors.add("Exception not handled: " + e.getClass() + " " + e.getMessage() + " " + e);
            }
            body.put(ERROR_MESSAGE, errors);
            long errorExecutionTime = System.currentTimeMillis();
            logger.info("/////// EXCEPTION HANDLED ON: " + joinPoint.getSignature() + " executed at " + errorExecutionTime + "ms " +
                    " exception class: " + e.getClass() + " " + body + " " + status + "///////");
            return new ResponseEntity<>(body, headers, status);
        }
    }
}