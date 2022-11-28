package ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Operation state values", description = "Operation state values")
public enum OperationState {

    ACTIVE,
    PAID,
    CRYPTOSENT,
    CANCELLED
}
