package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class UserRequest {
    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getAdress() {
        return adress;
    }

    public String getPassword() {
        return password;
    }

    public String getCVUMercadoPago() {
        return CVUMercadoPago;
    }

    public String getAdressWalletActiveCripto() {
        return adressWalletActiveCripto;
    }

    @NotBlank
    @Size(min = 3, max = 30, message = "name must be between 3 and 30 characters")
    private String name;
    //@Column
    @NotBlank
    @Size(min = 3, max = 30, message = "lastname must be between 3 and 30 characters")
    private String lastname;
    // @Column
    @Email(message = "lastname must be between 3 and 30 characters")
    private String email;
    //@Column
    @NotBlank
    @Size(min = 10, max = 30, message = "adress must be between 10 and 30 characters")
    private String adress;
    //@Column
    @NotBlank
    private String password;//investigar como validar los requerimentos indicados
    //@Column
    @NotBlank
    @Size(min = 22, max = 22, message = "CVU must be 22 characters")
    private String CVUMercadoPago;
    //@Column
    @NotBlank
    @Size(min = 8, max = 8, message = "wallet must be 8 characters")
    private String adressWalletActiveCripto;
}
