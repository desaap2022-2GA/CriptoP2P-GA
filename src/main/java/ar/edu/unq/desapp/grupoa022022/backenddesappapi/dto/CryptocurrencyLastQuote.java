package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CryptocurrencyLastQuote {
    @JsonProperty("symbol")
    private String name;
    @JsonProperty("price")
    private String lastQuote;

    private String dateTime;
    public CryptocurrencyLastQuote(String name, String lastQuote){
        this.name = name;
        this.lastQuote = lastQuote;
    }
}