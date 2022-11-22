package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {

    @Email
    private String email;

    @NotBlank(message = "Password: it can not be null")
    private String password;
}