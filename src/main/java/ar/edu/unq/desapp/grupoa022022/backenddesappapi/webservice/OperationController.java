package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationModify;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationView;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.IntentionAlreadyTaken;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.InvalidState;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceExceedVariationWithRespectIntentionTypeLimits;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IOperationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operations")
public class OperationController {

    @Autowired
    private IOperationService operationService;

    @Operation(summary = "Start an operation")
    @PostMapping
    public ResponseEntity<?> openOperation(@RequestBody OperationRegister operationRegister) throws ResourceNotFound, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        return ResponseEntity.ok(operationService.open(operationRegister));
    }

    @Operation(summary = "Modify an operation")
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> modifyOperation(@RequestBody OperationModify operationModify) throws ResourceNotFound, InvalidState {
        operationService.modify(operationModify);
        return ResponseEntity.ok("");
    }

    @Operation(summary = "Search for operation id")
    @GetMapping(value = "/{userId}/{operationId}")
    public ResponseEntity<OperationView> getOperationById(@PathVariable int operationId, @PathVariable int userId) throws ResourceNotFound {
        return ResponseEntity.ok(operationService.getOperationById(operationId,userId));
    }
}