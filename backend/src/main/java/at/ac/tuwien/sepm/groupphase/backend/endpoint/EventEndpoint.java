package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDateTimeSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping("/api/v1/events")
public class EventEndpoint {

    EventService eventService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public EventEndpoint(EventService eventService) {
        this.eventService = eventService;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @Operation(summary = "Persist a new event.")
    public ResponseEntity saveEvent(@RequestBody @Validated EventInquiryDto eventInquiryDto) {
        try {
            ResponseEntity response = new ResponseEntity(eventService.createEvent(eventInquiryDto), HttpStatus.OK);
            return response;
        } catch (ContextException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Event already exists:  " + e.getLocalizedMessage(), e);
        }
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Find events by search parameters.")
    public ResponseEntity findEvents(@Validated EventSearchDto eventSearchDto) {
        try {
            ResponseEntity response = new ResponseEntity(eventService.findEvents(eventSearchDto).stream(), HttpStatus.OK);
            return response;
        } catch (NotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage(), e);
        }
    }

    @Secured("ROLE_USER")
    @GetMapping("/dateTime")
    @Operation(summary = "Find events by search parameters.")
    public ResponseEntity findEventsByDateTime(@Validated EventDateTimeSearchDto eventDateTimeSearchDto) {
        ResponseEntity response = new ResponseEntity(eventService.findEventsByDateTime(eventDateTimeSearchDto).stream(), HttpStatus.OK);
        return response;
    }
}
