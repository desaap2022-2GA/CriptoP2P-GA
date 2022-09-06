package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions;

public class ExceptionsUser extends Throwable {
    String message = "";
    public ExceptionsUser(String message){
        super(message);
    }
}
