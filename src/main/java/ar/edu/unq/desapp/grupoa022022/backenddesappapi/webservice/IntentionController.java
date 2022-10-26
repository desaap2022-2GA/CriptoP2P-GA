package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceNotInAValidRange;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IIntentionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/intentions")
public class IntentionController {

    @Autowired
    private IIntentionService intentionService;

    @Operation(summary = "Create an intention")
    @PostMapping
    public Intention createIntention(@RequestBody @Valid IntentionRegister intentionRegister) throws ResourceNotFound, PriceNotInAValidRange {
        return intentionService.create(intentionRegister);
    }

    @Operation(summary = "Search an intention by id")
    @GetMapping(value = "/{id}")
    public Intention getIntentionById(@PathVariable("id") Integer id) throws ResourceNotFound {
        return intentionService.findById(id);
    }

    @Operation(summary = "List all intentions")
    @GetMapping
    public List<Intention> listAllIntentions() {
        return intentionService.getAll();
    }

    @Operation(summary = "List all active intentions")
    @GetMapping(value = "/intention/active")
    public List<Intention> listIntentionActive() {
        return intentionService.getIntentionActive();
    }


}