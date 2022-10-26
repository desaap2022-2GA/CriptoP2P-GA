package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionView;
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
    public IntentionView openIntention(@RequestBody @Valid IntentionRegister intentionRegister) throws ResourceNotFound, PriceNotInAValidRange {
        return intentionService.open(intentionRegister);
    }

    @Operation(summary = "Search an intention by id")
    @GetMapping(value = "/{id}")
    public IntentionView getIntentionById(@PathVariable("id") Integer id) throws ResourceNotFound {
        return intentionService.getIntentionById(id);
    }

    @Operation(summary = "List all intentions")
    @GetMapping
    public List<IntentionView> listAllIntentions() {
        return intentionService.getAll();
    }

    @Operation(summary = "List all active intentions")
    @GetMapping(value = "/active")
    public List<IntentionView> listIntentionActive() {
        return intentionService.getActiveIntentions();
    }

}