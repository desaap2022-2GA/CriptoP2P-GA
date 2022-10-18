package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice.handler;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@RestControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERROR_MESSAGE = "errorMessage";
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        body.put("errors",errors);
        return new ResponseEntity<>(body,headers,status);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceNotFound.class)
    public Map<String, String> handleBusinessException(ResourceNotFound ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ERROR_MESSAGE, ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailAlreadyExists.class)
    public Map<String, String> handleBusinessException(EmailAlreadyExists ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ERROR_MESSAGE, ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExceptionsUser.class)
    public Map<String, String> handleBusinessException(ExceptionsUser ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ERROR_MESSAGE, ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IntentionAlreadyTaken.class)
    public Map<String, String> handleBusinessException(IntentionAlreadyTaken ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ERROR_MESSAGE, ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidState.class)
    public Map<String, String> handleBusinessException(InvalidState ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ERROR_MESSAGE, ex.getMessage());
        return errorMap;
    }
}