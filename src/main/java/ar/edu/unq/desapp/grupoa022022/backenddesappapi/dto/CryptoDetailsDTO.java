package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CryptoDetailsDTO {

    private String name;
    private int units;
    private Double actualQuote;
    private Double pesosAmount;
}
