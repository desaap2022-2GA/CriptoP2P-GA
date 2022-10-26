package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationModify;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationView;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.IntentionAlreadyTaken;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.InvalidState;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceExceedVariationWithRespectIntentionTypeLimits;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operations")
public class OperationController {

    @Autowired
    private IOperationService operationService;
    @io.swagger.v3.oas.annotations.Operation(summary = "Start an operation")
    @PostMapping
    public OperationView openOperation(@RequestBody OperationRegister operationRegister) throws ResourceNotFound, IntentionAlreadyTaken, PriceExceedVariationWithRespectIntentionTypeLimits {
        return operationService.open(operationRegister);
    }
    @io.swagger.v3.oas.annotations.Operation(summary = "Modify an operation")
    @PutMapping(value = "/{id}")
    public void modifyOperation(@RequestBody OperationModify operationModify) throws ResourceNotFound, InvalidState {
        operationService.modify(operationModify);
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Search an operation by id")
    @GetMapping(value = "/{id}")
    public Operation getOperationById(@PathVariable("id") Integer id) throws ResourceNotFound {
        return operationService.findById(id);
    }
    @io.swagger.v3.oas.annotations.Operation(summary = "List all operations")
    @GetMapping
    public List<Operation> listAllOperations() {
        return operationService.getAll();
    }
}