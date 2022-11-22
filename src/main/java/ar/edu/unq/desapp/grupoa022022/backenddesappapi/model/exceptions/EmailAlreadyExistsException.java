package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions;

public class EmailAlreadyExistsException extends Exception {

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}