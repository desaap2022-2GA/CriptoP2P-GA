package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperationView {

    private String cryptocurrency;
    private double nominalAmount;
    private double quote;
    private String userWhoPostCompleteName;
    private int operationNumber;
    private int reputation;
    private String sentAddress;
    private String actionToDo;
}