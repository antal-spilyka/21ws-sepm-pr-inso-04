package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.PermitAll;
import java.lang.invoke.MethodHandles;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/events")
public class EventEndpoint {

    private final EventService eventService;
    private final EventMapper eventMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public EventEndpoint(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
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

    @GetMapping
    @Operation(summary = "find events.")
    public Stream<EventDto> findEvent(String name) {
        LOGGER.info("GET /api/v1/events/{}", name);
        return this.eventService.findEvent(name).stream().map(this.eventMapper::entityToDto);
    }
}
