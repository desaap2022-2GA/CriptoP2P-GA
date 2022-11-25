package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.aspects.log_data.LogMethodData;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.ActiveIntentionViewDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionRegisterDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.IntentionViewDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.PriceNotInAValidRangeException;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
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

    @LogMethodData
    @Operation(summary = "Create an intention")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<IntentionViewDTO> openIntention(@RequestHeader(value = "Authorization") String token, @RequestBody @Valid IntentionRegisterDTO intentionRegisterDTO) throws ResourceNotFoundException, PriceNotInAValidRangeException {
        return ResponseEntity.ok(intentionService.open(intentionRegisterDTO));
    }

    @Operation(summary = "Search an intention by id")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/{id}")
    public ResponseEntity<IntentionViewDTO> getIntentionById(@RequestHeader(value = "Authorization") String token, @PathVariable("id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(intentionService.getIntentionById(id));
    }

    @Operation(summary = "List all intentions")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public ResponseEntity<List<IntentionViewDTO>> listAllIntentions(@RequestHeader(value = "Authorization") String token) {
        return ResponseEntity.ok(intentionService.getAll());
    }

    @Operation(summary = "List all active intentions")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/active")
    public ResponseEntity<List<ActiveIntentionViewDTO>> listIntentionActive(@RequestHeader(value = "Authorization") String token) {
        return ResponseEntity.ok(intentionService.getActiveIntentions());
    }
}