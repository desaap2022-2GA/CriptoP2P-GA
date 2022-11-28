package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class UserRegisterDTO {
    @NotBlank
    @Size(min = 3, max = 30, message = "Name: must be between 3 and 30 characters")
    private String name;
    @NotBlank
    @Size(min = 3, max = 30, message = "Lastname: must be between 3 and 30 characters")
    private String lastname;
    @ApiModelProperty(notes = "User email", example = "usuario@zmail.com", required = true)
    @Pattern(regexp = "^[^@]+@[^@]+\\.[a-zA-Z]{2,}$", message = "Email: must be a properly formatted email address")
    private String email;
    @Size(min = 10, max = 30, message = "Address: must be between 10 and 30 characters")
    private String address;
    @ApiModelProperty(notes = "User password", example = "noV.i3mbr3", required = true)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=.])(?=\\S+$).{6,}$", message = "Password must contain at " +
            "least 1 lowercase, 1 uppercase, 1 special character, and at least 6 characters")
    private String password;
    @NotBlank
    @Size(min = 22, max = 22, message = "MercadoPagoCVU: must be 22 characters")
    private String mercadoPagoCVU;
    @NotBlank
    @Size(min = 8, max = 8, message = "AddressWalletActiveCrypto: must be 8 characters")
    private String addressWalletActiveCrypto;
}