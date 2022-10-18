package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions;

public class PriceNotInAValidRange extends Throwable {
    String message = "";

    public PriceNotInAValidRange(String message) {
        super(message);
    }
}