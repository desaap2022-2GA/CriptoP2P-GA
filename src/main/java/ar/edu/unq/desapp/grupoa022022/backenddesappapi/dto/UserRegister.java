package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserRegister extends UserAPI {

    @Pattern(regexp = "^(?=.*?[A-Z])(?=(.*[a-z])+)(?=(.*[\\W])+)(?!.*\\s).{6,}$", message = "password must contain at " +
            "least 1 lowercase, 1 uppercase, 1 special character, and at least 6 characters")
    private String password;

    public UserRegister(String name, String lastname, String email, String address, String password, String mercadoPagoCVU,
                        String addressWalletActiveCrypto) {
        super(name, lastname, email, address, mercadoPagoCVU, addressWalletActiveCrypto);
        this.password = password;
    }
}