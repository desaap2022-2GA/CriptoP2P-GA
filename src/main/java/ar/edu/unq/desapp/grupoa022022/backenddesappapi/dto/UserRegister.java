package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserRegister extends UserAPI {

    @NotBlank(message = "Password: it can not be null")
    private String password;

    public UserRegister(String name, String lastname, String email, String address, String password, String mercadoPagoCVU,
                        String addressWalletActiveCrypto) {
        super(name, lastname, email, address, mercadoPagoCVU, addressWalletActiveCrypto);
        this.password = password;
    }
}