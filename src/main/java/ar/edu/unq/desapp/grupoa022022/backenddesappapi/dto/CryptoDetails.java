package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CryptoDetails {

    private String name;
    private int units;
    private Double actualQuote;
    private Double pesosAmount;
}
