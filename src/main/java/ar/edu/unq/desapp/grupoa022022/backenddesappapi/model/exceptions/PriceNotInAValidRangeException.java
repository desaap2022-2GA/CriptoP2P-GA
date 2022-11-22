package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions;

public class PriceNotInAValidRangeException extends Exception {

    public PriceNotInAValidRangeException(String message) {
        super(message);
    }
}