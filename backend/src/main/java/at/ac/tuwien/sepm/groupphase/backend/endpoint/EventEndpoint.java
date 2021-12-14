package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDateTimeSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass()); // todo logger

    public EventEndpoint(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Find events by search parameters.")
    public ResponseEntity findEvents(@Validated EventSearchDto eventSearchDto) {
        try {
            return new ResponseEntity(eventService.findEvents(eventSearchDto).stream(), HttpStatus.OK);
        } catch (NotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage(), e);
        }
    }

    /*@Secured("ROLE_USER")
    @GetMapping("/dateTime")
    @Operation(summary = "Find events by search parameters.")
    public ResponseEntity findEventsByDateTime(@Validated EventDateTimeSearchDto eventDateTimeSearchDto) {
        return new ResponseEntity(eventService.findEventsByDateTime(eventDateTimeSearchDto).stream(), HttpStatus.OK);
    }*/

    @Secured("ROLE_USER")
    @GetMapping("/news")
    @Operation(summary = "find events.")
    public Stream<EventDto> findEventByName(@RequestParam String name) {
        LOGGER.info("GET /api/v1/events/news/{}", name);
        return this.eventService.findEvent(name).stream().map(this.eventMapper::entityToDto);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @Operation(summary = "Persist a new event.")
    public EventDto saveEvent(@RequestBody @Validated EventDto eventDto) {
        LOGGER.info("POST /api/v1/events/{}", eventDto);
        return eventMapper.entityToDto(eventService.saveEvent(eventDto));
    }

    @Secured("ROLE_USER")
    @GetMapping(value = "/{id}/performances")
    @Operation(summary = "Find performances for specified event.")
    public Stream<PerformanceDto> findEventsByDateTime(@PathVariable("id") Long id) {
        LOGGER.info("GET /api/v1/events/{}/performances", id);
        return this.eventService.getPerformances(id);
    }
}
