package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions;

public class ResourceNotFoundException extends Throwable {
    String message = "";
    public ResourceNotFoundException(String message){
        super(message);
    }
}
