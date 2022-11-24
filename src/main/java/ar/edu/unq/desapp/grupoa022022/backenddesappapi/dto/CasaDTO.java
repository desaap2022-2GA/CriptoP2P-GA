package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CasaDTO {
    @JsonProperty("compra")
    String compra;
    @JsonProperty("venta")
    String venta;
    @JsonProperty("agencia")
    String agencia;
    @JsonProperty("nombre")
    String nombre;
    @JsonProperty("variacion")
    String variacion;
    @JsonProperty("ventaCero")
    String ventaCero;
    @JsonProperty("decimales")
    String decimales;
}