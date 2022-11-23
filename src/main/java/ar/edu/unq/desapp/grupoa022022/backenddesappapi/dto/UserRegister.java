package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class UserRegister {
    @NotBlank
    @Size(min = 3, max = 30, message = "Name: must be between 3 and 30 characters")
    private String name;
    @NotBlank
    @Size(min = 3, max = 30, message = "Lastname: must be between 3 and 30 characters")
    private String lastname;
    @Pattern(regexp = "^[^@]+@[^@]+\\.[a-zA-Z]{2,}$", message = "Email: must be a properly formatted email address")
    private String email;
    @NotBlank(message = "Address: it can not be null")
    private String address;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$", message = "Password must contain at " +
            "least 1 lowercase, 1 uppercase, 1 special character, and at least 6 characters")
    private String password;

    @NotBlank
    @Size(min = 22, max = 22, message = "MercadoPagoCVU: must be 22 characters")
    private String mercadoPagoCVU;
    @NotBlank
    @Size(min = 8, max = 8, message = "AddressWalletActiveCrypto: must be 8 characters")
    private String addressWalletActiveCrypto;



}