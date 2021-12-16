package at.ac.tuwien.sepm.groupphase.backend.endpoint;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/halls")
public class HallEndpoint {

    private final HallService hallService;
    private final HallMapper hallMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass()); // todo logger

    public HallEndpoint(HallService hallService, HallMapper hallMapper) {
        this.hallService = hallService;
        this.hallMapper = hallMapper;
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Find hall by search parameters.")
    public Stream<HallDto> findHall(String name) {
        return hallService.findHall(name).stream().map(hallMapper::entityToDto);
    }

    @Secured("ROLE_USER")
    @GetMapping(value = "/search")
    @Operation(summary = "Get the list of all hall.")
    public ResponseEntity getAllHalls() {
        return new ResponseEntity(hallService.getAll().stream(), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @Operation(summary = "persist new hall.")
    public HallDto saveHall(@RequestBody @Validated HallDto hallDto) {
        //try {
        return hallMapper.entityToDto(hallService.save(hallDto));
        /*} catch (ContextException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Hall already exists:  " + e.getLocalizedMessage(), e);
        }*/
    }
}
