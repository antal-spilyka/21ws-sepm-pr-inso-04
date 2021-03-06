package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.HallMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.service.HallService;
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

import javax.annotation.security.PermitAll;
import java.lang.invoke.MethodHandles;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/halls")
public class HallEndpoint {

    private final HallService hallService;
    private final HallMapper hallMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/halls";

    public HallEndpoint(HallService hallService, HallMapper hallMapper) {
        this.hallService = hallService;
        this.hallMapper = hallMapper;
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Find hall by search parameters.")
    public Stream<HallDto> findHall(String name) {
        LOGGER.info("GET " + BASE_URL + " " + name);
        return hallService.findHall(name).stream().map(hallMapper::entityToDto);
    }

    @Secured("ROLE_USER")
    @GetMapping("/eventplaces")
    @Operation(summary = "Find hall by search parameters.")
    public Stream<HallDto> findHall(String name, Long eventPlaceId) {
        LOGGER.info("GET " + BASE_URL + " " + name);
        return hallService.findHallWithLocation(name, eventPlaceId).stream().map(hallMapper::entityToDto);
    }

    @PermitAll
    @GetMapping("/{hallId:^[0-9]+$}")
    @Operation(summary = "Get hall by id.")
    public HallDetailDto getHall(@PathVariable String hallId) {
        LOGGER.info("GET " + BASE_URL + "/{}", hallId);
        return hallService.getHall(hallId);
    }

    @Secured("ROLE_USER")
    @GetMapping(value = "/search")
    @Operation(summary = "Get the list of all hall.")
    public ResponseEntity getAllHalls() {
        LOGGER.info("GET " + BASE_URL + "/search");
        return new ResponseEntity(hallService.getAll().stream(), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @Operation(summary = "persist new hall.")
    public HallDto saveHall(@RequestBody @Validated HallDto hallDto) {
        LOGGER.info("POST " + BASE_URL + " " + hallDto.toString());
        return hallMapper.entityToDto(hallService.save(hallDto));
    }
}
