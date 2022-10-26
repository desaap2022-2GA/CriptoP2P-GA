package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.IntentionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class IntentionRegister {

    @NotNull
    @Enumerated(EnumType.STRING)
    private IntentionType type;
    @NotNull
    private int cryptocurrencyId;
    @NotNull
    @Min(value = 0)
    private Double price;
    @NotNull
    @Min(value = 0)
    private int units;
    @NotNull
    private int userId;
}
