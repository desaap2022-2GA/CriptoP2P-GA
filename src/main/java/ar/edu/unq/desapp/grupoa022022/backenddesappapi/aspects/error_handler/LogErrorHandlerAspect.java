package ar.edu.unq.desapp.grupoa022022.backenddesappapi.aspects.error_handler;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.ResponseEntityCustom;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
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
            String exceptionClass = e.getClass().toString();
            String exceptionMessage = e.getMessage();

            if (Arrays.stream(businessExcpetions).toList().contains(exceptionClass)) {
                errors.add(exceptionMessage);
            } else {
                errors.add("Exception not handled: " +"Class: "+ exceptionClass + " Message: " + exceptionMessage + " " + e);
            }

            ResponseEntity responseEntityBadRequest = (new ResponseEntityCustom(errors)).getResponseEntityBadRequest();
            long errorExecutionTime = System.currentTimeMillis();
            logger.info("/////// EXCEPTION ON: " + joinPoint.getSignature() + " executed at " + errorExecutionTime + "ms " +
                    " exception class: " + exceptionClass + " " + responseEntityBadRequest.getBody() + " " + responseEntityBadRequest.getStatusCode() + "///////");

            return responseEntityBadRequest;
        }
    }

    private static final String[] businessExcpetions = {
            "class ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceNotInAValidRangeException",
            "class ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceExceedVariationWithRespectIntentionTypeLimitsException",
            "class ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.InvalidStateException",
            "class ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.IntentionAlreadyTakenException",
            "class ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ExceptionsUserException",
            "class ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.EmailAlreadyExistsException",
            "class ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException"
    };
}