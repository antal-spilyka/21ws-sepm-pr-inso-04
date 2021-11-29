package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CategoryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.service.ArtistService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventPlaceService;
import io.swagger.v3.oas.annotations.Operation;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
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
        try {
            ResponseEntity response = new ResponseEntity(eventPlaceService.findEventPlace(eventPlaceSearchDto).stream(), HttpStatus.OK);
            return response;
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unknown error occured.");
        }
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @Operation(summary = "persist new eventPlace.")
    public ResponseEntity saveEventPlace(@RequestBody EventPlaceDto eventPlaceDto) {
        try {
            ResponseEntity response = new ResponseEntity(eventPlaceService.save(eventPlaceDto), HttpStatus.CREATED);
            return response;
        } catch (ContextException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("EventPlace with same id already exists.");
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unknown error occured.");
        }
    }
}
