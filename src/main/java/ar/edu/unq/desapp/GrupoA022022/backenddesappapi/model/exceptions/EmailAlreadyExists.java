package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions;

public class EmailAlreadyExists extends Throwable {
    String message = "";

    public EmailAlreadyExists(String message) {
        super(message);
    }
}