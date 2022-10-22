package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserQuery {


    private final String name;
    private final String lastname;
    private final int numberOperations;
    private final int reputation;



}
