package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
public class UserRegister extends UserAPI {

    @NotBlank(message = "Password: it can not be null")
    private String password;

/*    public UserRegister(String name, String lastname, String email, String address, String password, String mercadoPagoCVU, String addressWalletActiveCripto) {
        super(name, lastname, email, address, password, mercadoPagoCVU, addressWalletActiveCripto);
    }*/
}
