package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions;

public class ResourceNotFound extends Throwable {
    String message = "";
    public ResourceNotFound(String message){
        super(message);
    }
}
