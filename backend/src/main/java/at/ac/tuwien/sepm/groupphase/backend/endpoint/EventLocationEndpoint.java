package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventLocationSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.GeneralSearchEventDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.EventPlaceService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping("/api/v1/eventlocations")
public class EventLocationEndpoint {

    private EventPlaceService eventPlaceService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/eventlocations";

    public EventLocationEndpoint(EventPlaceService eventPlaceService) {
        this.eventPlaceService = eventPlaceService;
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Find Event Location by search parameters.")
    public ResponseEntity findEventLocation(EventLocationSearchDto eventLocationSearchDto) {
        LOGGER.info("GET " + BASE_URL + " " + eventLocationSearchDto.toString());
        return new ResponseEntity(eventPlaceService.findEventLocation(eventLocationSearchDto).stream(), HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @GetMapping("/general-search")
    @Operation(summary = "Find Event Location by search parameters.")
    public ResponseEntity findGeneralEventLocation(GeneralSearchEventDto generalSearchEventDto) {
        LOGGER.info("GET " + BASE_URL + " " + generalSearchEventDto);
        return new ResponseEntity(eventPlaceService.findGeneralEventLocation(generalSearchEventDto).stream(), HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @GetMapping("/{eventPlaceDtoId}")
    @Operation(summary = "Returns the address of the given location.")
    public ResponseEntity findAddress(@PathVariable Long eventPlaceDtoId) {
        LOGGER.info("GET " + BASE_URL + "/{}", eventPlaceDtoId);
        return new ResponseEntity(eventPlaceService.findAddress(eventPlaceDtoId), HttpStatus.OK);
    }
}
