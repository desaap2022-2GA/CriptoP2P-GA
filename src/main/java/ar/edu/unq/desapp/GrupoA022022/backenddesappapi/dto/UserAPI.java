package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserAPI {

    @NotBlank
    @Size(min = 3, max = 30, message = "Name: must be between 3 and 30 characters")
    private String name;
    @NotBlank
    @Size(min = 3, max = 30, message = "Lastname: must be between 3 and 30 characters")
    private String lastname;
    @Email(message = "Email: must be a properly formatted email address")
    private String email;
    @NotBlank(message = "Address: it can not be null")
    private String address;
    @NotBlank(message = "Password: it can not be null")
    private String password;
    @NotBlank
    @Size(min = 22, max = 22, message = "MercadoPagoCVU: must be 22 characters")
    private String mercadoPagoCVU;
    @NotBlank
    @Size(min = 8, max = 8, message = "AdressWalleActiveCryptot: must be 8 characters")
    private String addressWalletActiveCripto;

    public UserAPI(String name, String lastname, String email, String address, String password, String mercadoPagoCVU, String adressWalletActiveCripto) {

        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.password = password;
        this.mercadoPagoCVU = mercadoPagoCVU;
        this.addressWalletActiveCripto = adressWalletActiveCripto;
    }

    public UserAPI() {
    }
}
