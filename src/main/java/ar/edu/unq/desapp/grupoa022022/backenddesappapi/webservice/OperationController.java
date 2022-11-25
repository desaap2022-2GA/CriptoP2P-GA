package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.aspects.log_data.LogMethodData;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationModifyDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationRegisterDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationViewDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.IntentionAlreadyTakenException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.InvalidStateException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceExceedVariationWithRespectIntentionTypeLimitsException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IOperationService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operations")
public class OperationController {

    @Autowired
    private IOperationService operationService;

    @Autowired
    TokenService tokenService;

    @LogMethodData
    @Operation(summary = "Start an operation")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<OperationViewDTO> openOperation(@RequestHeader(value = "Authorization") String token, @RequestBody OperationRegisterDTO operationRegisterDTO) throws ResourceNotFoundException, IntentionAlreadyTakenException, PriceExceedVariationWithRespectIntentionTypeLimitsException {
        return ResponseEntity.ok(operationService.open(operationRegisterDTO));
    }

    @LogMethodData
    @Operation(summary = "Modify an operation")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> modifyOperation(@RequestHeader(value = "Authorization") String token, @RequestBody OperationModifyDTO operationModifyDTO) throws ResourceNotFoundException, InvalidStateException {
        operationService.modify(operationModifyDTO);
        return ResponseEntity.ok("");
    }

    @Operation(summary = "Search for operation id")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/{userId}/{operationId}")
    public ResponseEntity<OperationViewDTO> getOperationById(@RequestHeader(value = "Authorization") String token, @PathVariable int operationId, @PathVariable int userId) throws ResourceNotFoundException {
        return ResponseEntity.ok(operationService.getOperationById(operationId,userId));
    }
}