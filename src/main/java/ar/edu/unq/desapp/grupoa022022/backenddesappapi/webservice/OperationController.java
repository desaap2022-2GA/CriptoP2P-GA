package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationModify;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.OperationRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.IntentionAlreadyTaken;
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

    @PostMapping
    public Operation openOperation(@RequestBody OperationRegister operationRegister) throws ResourceNotFound, IntentionAlreadyTaken {
        return operationService.create(operationRegister);
    }

    @PutMapping
    public void modifyOperation(@RequestBody OperationModify operationModify) throws ResourceNotFound {
        operationService.modify(operationModify);
    }

    @GetMapping
    public List<Operation> listAllOperations() {
        return operationService.getAll();
    }

    @DeleteMapping(value = "/all")
    public void deleteAllOperations() {
        operationService.deleteAll();
    }
}