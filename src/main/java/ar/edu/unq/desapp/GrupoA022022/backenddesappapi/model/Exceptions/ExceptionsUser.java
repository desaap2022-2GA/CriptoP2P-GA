package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.Model.Exceptions;

public class ExceptionsUser extends Throwable {
    String message = "";
    public ExceptionsUser(String message){
        //String message = "El nombre es obligatorio. Debe contener entre 3 y 30 caracteres";
        super(message);
    }


    /*public String getMessage() {
        System.out.print(this.message);
        return this.message;
    }*/
}
