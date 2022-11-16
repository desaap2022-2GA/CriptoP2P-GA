package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionView;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.TokenDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceNotInAValidRange;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.interfaceservice.IIntentionService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/intentions")
public class IntentionController {

    @Autowired
    IIntentionService intentionService;

    @Autowired
    TokenService tokenService;

    @Operation(summary = "Create an intention")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<IntentionView> openIntention(@RequestHeader(value = "Authorization") String token, @RequestBody @Valid IntentionRegister intentionRegister) throws ResourceNotFound, PriceNotInAValidRange {
        TokenDTO tokenDTO = tokenService.validate(token);
        if (tokenDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(intentionService.open(intentionRegister));
    }

    @Operation(summary = "Search an intention by id")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/{id}")
    public ResponseEntity<IntentionView> getIntentionById(@RequestHeader(value = "Authorization") String token, @PathVariable("id") Integer id) throws ResourceNotFound {
        TokenDTO tokenDTO = tokenService.validate(token);
        if (tokenDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(intentionService.getIntentionById(id));
    }

    @Operation(summary = "List all intentions")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public ResponseEntity<List<IntentionView>> listAllIntentions(@RequestHeader(value = "Authorization") String token) {
        TokenDTO tokenDTO = tokenService.validate(token);
        if (tokenDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(intentionService.getAll());
    }

    @Operation(summary = "List all active intentions")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/active")
    public ResponseEntity<List<IntentionView>> listIntentionActive(@RequestHeader(value = "Authorization") String token) {
        TokenDTO tokenDTO = tokenService.validate(token);
        if (tokenDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(intentionService.getActiveIntentions());
    }
}