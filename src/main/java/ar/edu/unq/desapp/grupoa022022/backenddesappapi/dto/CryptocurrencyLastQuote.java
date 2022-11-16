package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CryptocurrencyLastQuote {
    @JsonProperty("symbol")
    private String name;
    @JsonProperty("price")
    private String lastQuote;

    private String date;
    public CryptocurrencyLastQuote(String name, String lastQuote){
        this.name = name;
        this.lastQuote = lastQuote;
    }
}