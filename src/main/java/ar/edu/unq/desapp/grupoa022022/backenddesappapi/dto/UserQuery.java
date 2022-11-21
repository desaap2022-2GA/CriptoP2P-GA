package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserQuery {

    private final String name;
    private final String lastname;
    private final String numberOperations;
    private final String reputation;
}
