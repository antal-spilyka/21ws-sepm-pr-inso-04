package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallAddDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventPlaceMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.service.EventPlaceService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping("/api/v1/eventplaces") // todo ist das nicht das gleiche iw EventLocationEndpoint
public class EventPlaceEndpoint {

    private final EventPlaceService eventPlaceService;
    private final EventPlaceMapper eventPlaceMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public EventPlaceEndpoint(EventPlaceService eventPlaceService, EventPlaceMapper eventPlaceMapper) {
        this.eventPlaceService = eventPlaceService;
        this.eventPlaceMapper = eventPlaceMapper;
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Find EventPlace by search parameters.")
    public ResponseEntity findEventPlace(EventPlaceSearchDto eventPlaceSearchDto) {
        return new ResponseEntity(eventPlaceService.findEventPlace(eventPlaceSearchDto).stream(), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @Operation(summary = "persist new eventPlace.")
    public EventPlaceDto saveEventPlace(@RequestBody @Validated EventPlaceDto eventPlaceDto) {
        try {
            return eventPlaceMapper.entityToDto(eventPlaceService.save(eventPlaceDto));
        } catch (ContextException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "EventPlace already exists:  " + e.getLocalizedMessage(), e);
        }
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/{eventPlaceId:^[0-9]+$}/halls/add")
    @Operation(summary = "add a hall.")
    public ResponseEntity<String> addHall(@RequestBody @Valid HallAddDto hallAddDto, @PathVariable String eventPlaceId) {
        LOGGER.info("PUT /api/v1/eventplaces/" + eventPlaceId + "/halls/add" + hallAddDto.toString());
        try {
            eventPlaceService.addHall(eventPlaceId, hallAddDto);
        } catch (ContextException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "EventPlace already exists:  " + e.getLocalizedMessage(), e);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
