package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.service.EventPlaceService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping("/api/v1/eventplaces")
public class EventPlaceEndpoint {

    private EventPlaceService eventPlaceService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public EventPlaceEndpoint(EventPlaceService eventPlaceService) {
        this.eventPlaceService = eventPlaceService;
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Find EventPlace by search parameters.")
    public ResponseEntity findEventPlace(EventPlaceSearchDto eventPlaceSearchDto) {
        ResponseEntity response = new ResponseEntity(eventPlaceService.findEventPlace(eventPlaceSearchDto).stream(), HttpStatus.OK);
        return response;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @Operation(summary = "persist new eventPlace.")
    public ResponseEntity saveEventPlace(@RequestBody @Validated EventPlaceDto eventPlaceDto) {
        try {
            ResponseEntity response = new ResponseEntity(eventPlaceService.save(eventPlaceDto), HttpStatus.CREATED);
            return response;
        } catch (ContextException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "EventPlace already exists:  " + e.getLocalizedMessage(), e);
        }
    }
}
