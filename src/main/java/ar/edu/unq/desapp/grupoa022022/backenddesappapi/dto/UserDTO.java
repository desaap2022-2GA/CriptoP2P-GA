package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
    private String name;

    @NotBlank
    @Size(min = 3, max = 30, message = "lastname must be between 3 and 30 characters")
    private String lastname;

    private String email;

    private String address;

    @NotBlank(message = "Password: it can not be null")
    private String password;

    @NotBlank
    @Size(min = 22, max = 22, message = "CVU must be 22 characters")
    private String mercadoPagoCVU;

    @NotBlank
    @Size(min = 8, max = 8, message = "wallet must be 8 characters")
    private String addressWalletActiveCrypto;
}
