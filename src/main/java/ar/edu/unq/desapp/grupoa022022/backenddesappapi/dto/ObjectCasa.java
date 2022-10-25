package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ObjectCasa {
    @JsonProperty("casa")
    Casa casa;
}
