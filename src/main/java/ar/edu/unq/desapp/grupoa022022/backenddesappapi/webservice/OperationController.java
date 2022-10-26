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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operations")
public class OperationController {

    @Autowired
    private IOperationService operationService;

    @Operation(summary = "Start an operation")
    @PostMapping
    public OperationView openOperation(@RequestBody OperationRegister operationRegister) throws ResourceNotFound, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        return operationService.open(operationRegister);
    }

    @Operation(summary = "Modify an operation")
    @PutMapping(value = "/{id}")
    public void modifyOperation(@RequestBody OperationModify operationModify) throws ResourceNotFound, InvalidState {
        operationService.modify(operationModify);
    }

    @Operation(summary = "Search for operation id")
    @GetMapping(value = "/{userId}/{operationId}")
    public OperationView getOperationById(@PathVariable int operationId, @PathVariable int userId) throws ResourceNotFound {
        return operationService.getOperationById(operationId,userId);

    }
}