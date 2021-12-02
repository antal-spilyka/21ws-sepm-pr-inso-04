package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventLocationSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.service.EventPlaceService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping("/api/v1/eventlocations")
public class EventLocationEndpoint {

    private EventPlaceService eventPlaceService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public EventLocationEndpoint(EventPlaceService eventPlaceService) {
        this.eventPlaceService = eventPlaceService;
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Find Event Location by search parameters.")
    public ResponseEntity findEventLocation(EventLocationSearchDto eventLocationSearchDto) {
        ResponseEntity response = new ResponseEntity(eventPlaceService.findEventLocation(eventLocationSearchDto).stream(), HttpStatus.OK);
        return response;
    }
}
