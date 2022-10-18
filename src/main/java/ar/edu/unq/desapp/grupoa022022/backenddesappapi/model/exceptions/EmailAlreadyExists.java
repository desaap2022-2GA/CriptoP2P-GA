package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions;

public class EmailAlreadyExists extends Throwable {

    public EmailAlreadyExists(String message) {
        super(message);
    }
}