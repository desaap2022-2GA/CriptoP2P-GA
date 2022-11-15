package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CryptocurrencyLastQuote {
    @JsonProperty("symbol")
    private String name;
    @JsonProperty("price")
    private BigDecimal lastQuote;

    private String date;
    public CryptocurrencyLastQuote(String name, BigDecimal lastQuote){
        this.name = name;
        this.lastQuote = lastQuote;
    }
}