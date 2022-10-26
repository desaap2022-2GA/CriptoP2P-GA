package ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.OperationState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class OperationModify {

    @NotNull
    private int operationId;
    @NotNull
    @Enumerated(EnumType.STRING)
    private OperationState state;
    @NotNull
    private int userId;
}
