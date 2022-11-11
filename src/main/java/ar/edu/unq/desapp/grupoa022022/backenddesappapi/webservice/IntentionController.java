package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionView;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceNotInAValidRange;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IIntentionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public IntentionView openIntention(@RequestHeader(value = "Authorization") String token, @RequestBody @Valid IntentionRegister intentionRegister) throws ResourceNotFound, PriceNotInAValidRange {
        return intentionService.open(intentionRegister);
    }

    @Operation(summary = "Search an intention by id")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/{id}")
    public IntentionView getIntentionById(@RequestHeader(value = "Authorization") String token, @PathVariable("id") Integer id) throws ResourceNotFound {
        return intentionService.getIntentionById(id);
    }

    @Operation(summary = "List all intentions")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public List<IntentionView> listAllIntentions(@RequestHeader(value = "Authorization") String token) {
        return intentionService.getAll();
    }

    @Operation(summary = "List all active intentions")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/active")
    public List<IntentionView> listIntentionActive(@RequestHeader(value = "Authorization") String token) {
        return intentionService.getActiveIntentions();
    }

}