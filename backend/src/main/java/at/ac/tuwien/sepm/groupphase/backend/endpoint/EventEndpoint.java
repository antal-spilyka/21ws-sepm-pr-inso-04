package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.GeneralSearchEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TopTenEventsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/event";

    public EventEndpoint(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Find events by search parameters.")
    public ResponseEntity findEvents(@Validated EventSearchDto eventSearchDto) {
        LOGGER.info("GET " + BASE_URL + " " + eventSearchDto.toString());
        try {
            return new ResponseEntity(eventService.findEvents(eventSearchDto).stream(), HttpStatus.OK);
        } catch (NotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage(), e);
        }
    }

    @Secured("ROLE_USER")
    @GetMapping("/general-search")
    @Operation(summary = "Find events by search parameters.")
    public ResponseEntity findGeneralEvents(@Validated GeneralSearchEventDto generalSearchEventDto) {
        LOGGER.info("GET " + BASE_URL + " " + generalSearchEventDto);
        try {
            return new ResponseEntity(eventService.findGeneralEvents(generalSearchEventDto).stream(), HttpStatus.OK);
        } catch (NotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage(), e);
        }
    }

    @Secured("ROLE_USER")
    @GetMapping("/news")
    @Operation(summary = "find events.")
    public Stream<EventDto> findEventByName(@RequestParam String name) {
        LOGGER.info("GET " + BASE_URL + "/{}", name);
        return this.eventService.findEvent(name).stream().map(this.eventMapper::entityToDto);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @Operation(summary = "Persist a new event.")
    public EventDto saveEvent(@RequestBody @Validated EventDto eventDto) {
        LOGGER.info("POST " + BASE_URL + "/{}", eventDto);
        return eventMapper.entityToDto(eventService.saveEvent(eventDto));
    }

    @Secured("ROLE_USER")
    @GetMapping(value = "/{id}/performances")
    @Operation(summary = "Find performances for specified event.")
    public Stream<PerformanceDto> findPerformancesByEvent(@PathVariable("id") Long id) {
        LOGGER.info("GET " + BASE_URL + "/{}/performances", id);
        return this.eventService.getPerformances(id);
    }

    @Secured("ROLE_USER")
    @GetMapping(value = "/location/{id}/performances")
    @Operation(summary = "Find performances for specified location.")
    public Stream<PerformanceDto> findPerformancesByLocation(@PathVariable("id") Long id, @RequestParam Integer page) {
        LOGGER.info("GET " + BASE_URL + "/location/{}/performances for the {} page", id, page);
        return this.eventService.getPerformancesByLocation(id, page);
    }

    @Secured("ROLE_USER")
    @GetMapping(value = "/categories")
    @Operation(summary = "get all categories.")
    public Stream<String> findDistinctByOrderByCategoryAsc() {
        LOGGER.info("GET " + BASE_URL + "/categories");
        return this.eventService.findDistinctByOrderByCategoryAsc().stream();
    }

    @Secured("ROLE_USER")
    @GetMapping(value = "/topTenEvents")
    @Operation(summary = "get top ten events by category.")
    public Stream<TopTenEventsDto> findByCategoryEquals(@RequestParam String category) {
        LOGGER.info("GET " + BASE_URL + "/topTenEvents/{}", category);
        return this.eventService.findByCategoryEquals(category).stream();
    }
}
