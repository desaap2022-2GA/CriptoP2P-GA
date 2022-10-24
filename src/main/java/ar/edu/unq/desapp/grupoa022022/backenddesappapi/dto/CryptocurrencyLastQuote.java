package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CryptocurrencyLastQuote {
    @JsonProperty("symbol")
    private String name;
    @JsonProperty("price")
    private Double lastQuote;
}
