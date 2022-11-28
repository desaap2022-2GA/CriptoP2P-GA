package ar.edu.unq.desapp.grupoa022022.backenddesappapi.aspects.error_handler;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.ResponseEntityBadRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@Aspect
@Component
@Order(2)
public class LogErrorHandlerAspect {
    static Logger logger = LoggerFactory.getLogger(LogErrorHandlerAspect.class);

    @Around("execution(* ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice..*.*(..))")
    public Object logErrorHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            List<String> errors = new LinkedList<>();

            switch (e.getClass().toString()) {
                //business exceptions
                case "class ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceNotInAValidRangeException",
                        "class ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceExceedVariationWithRespectIntentionTypeLimitsException",
                        "class ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.InvalidStateException",
                        "class ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.IntentionAlreadyTakenException",
                        "class ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ExceptionsUserException",
                        "class ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.EmailAlreadyExistsException",
                        "class ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException" ->
                        errors.add(e.getMessage());
                //argument validations exceptions
                case "class org.springframework.web.bind.MethodArgumentNotValidException",
                        "class javax.validation.ConstraintViolationException" -> {
                    ConstraintViolationException ex = (ConstraintViolationException) e;
                    errors.add(ex.getConstraintViolations().stream().map(ConstraintViolation::getMessageTemplate).toList().toString());
                }
                default -> errors.add("Exception not handled: " + e.getClass() + " " + e.getMessage() + " " + e);
            }

            ResponseEntity responseEntityBadRequest = (new ResponseEntityBadRequest(errors)).getResponseEntity();
            long errorExecutionTime = System.currentTimeMillis();
            logger.info("/////// EXCEPTION ON: " + joinPoint.getSignature() + " executed at " + errorExecutionTime + "ms " +
                    " exception class: " + e.getClass() + " " + responseEntityBadRequest.getBody() + " " + responseEntityBadRequest.getStatusCode() + "///////");
            return responseEntityBadRequest;
        }
    }
}