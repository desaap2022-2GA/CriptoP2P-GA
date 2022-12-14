package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class QuoteRegisterDTO {

    @NotNull
    private int cryptocurrencyId;
    @NotNull
    @Min(value = 0)
    private Double price;
}
