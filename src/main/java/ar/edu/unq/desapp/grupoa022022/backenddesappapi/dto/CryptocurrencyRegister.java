package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class CryptocurrencyRegister {

    @NotBlank
    @Column(unique = true)
    private String name;

    @NotNull
    @Min(value = 0)
    private Double price;
}
