package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions;

public class IntentionAlreadyTaken extends Throwable {
    String message = "";

    public IntentionAlreadyTaken(String message) {
        super(message);
    }
}
