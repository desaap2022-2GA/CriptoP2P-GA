package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserModify extends UserAPI {

    @NotNull(message = "Id: it can not be null")
    private int id;

    @NotBlank(message = "Password: it can not be null")
    private String password;

    public UserModify(String name, String lastname, String email, String address, String password, String mercadoPago, String wallet) {
        super(name,lastname,email,address, mercadoPago,wallet);
        this.password = password;
    }
}
