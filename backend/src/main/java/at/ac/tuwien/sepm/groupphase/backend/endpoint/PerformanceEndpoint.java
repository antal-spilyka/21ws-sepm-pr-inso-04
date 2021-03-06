package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.BasketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.GeneralSearchEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.PerformanceMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.PerformanceService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
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
import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.security.Principal;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/performances")
public class PerformanceEndpoint {
    private final PerformanceService performanceService;
    private final PerformanceMapper performanceMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/performances";

    public PerformanceEndpoint(PerformanceService performanceService, PerformanceMapper performanceMapper) {
        this.performanceService = performanceService;
        this.performanceMapper = performanceMapper;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @Operation(summary = "persist new performance.")
    public PerformanceDto savePerformance(@RequestBody @Validated PerformanceDto performanceDto) {
        LOGGER.info("POST " + BASE_URL + "/{}", performanceDto);
        return performanceMapper.entityToDto(performanceService.save(performanceDto), performanceDto.getEventDto());
    }

    @Secured("ROLE_USER")
    @GetMapping("/general-search")
    @Operation(summary = "Find events by search parameters.")
    @Transactional
    public Stream<PerformanceDto> findGeneralEventsByDateTime(@Validated GeneralSearchEventDto generalSearchEventDto) {
        LOGGER.info("GET " + BASE_URL + "/search " + generalSearchEventDto);
        return this.performanceService.findGeneralPerformanceByDateTime(generalSearchEventDto);
    }

    @Secured("ROLE_USER")
    @GetMapping("/search")
    @Operation(summary = "Find events by search parameters.")
    @Transactional
    public Stream<PerformanceDto> findEventsByDateTime(@Validated PerformanceSearchDto performanceSearchDto) {
        LOGGER.info("GET " + BASE_URL + "/search " + performanceSearchDto);
        return this.performanceService.findPerformanceByDateTime(performanceSearchDto);
    }

    @Secured("ROLE_USER")
    @GetMapping(value = "/artist/{id}")
    @Operation(summary = "Find performances for specified artist.")
    @Transactional
    public Stream<PerformanceDto> findPerformanceForArtist(@PathVariable("id") Long id, @RequestParam Integer page) {
        LOGGER.info("GET " + BASE_URL + "/id {}", id);
        return this.performanceService.findPerformanceForArtist(id, page);
    }

    @GetMapping(value = "/{id}")
    @PermitAll
    @Operation(summary = "Find specific performance.")
    @Transactional
    public PerformanceDetailDto findPerformance(@PathVariable("id") Long id) {
        LOGGER.info("GET " + BASE_URL + "/id {}", id);
        if (performanceService.findPerformanceById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No performance found with id " + id);
        } else {
            return this.performanceService.findPerformanceById(id);
        }
    }

    @Secured("ROLE_USER")
    @PostMapping(value = "/buy/{id}")
    @Operation(summary = "Buy seats for a performance.")
    public void buySeats(@RequestBody @Valid BasketDto basket, @PathVariable("id") Long id, Principal principal) {
        LOGGER.info("POST " + BASE_URL + "/buy {}", basket);
        this.performanceService.buySeats(basket, id, principal);
    }

    @Secured("ROLE_USER")
    @PostMapping(value = "/reserve/{id}")
    @Operation(summary = "Reserve seats for a performance.")
    public void reserveSeats(@RequestBody @Valid BasketDto basket, @PathVariable("id") Long id, Principal principal) {
        LOGGER.info("POST " + BASE_URL + "/reserve {}", basket);
        this.performanceService.reserveSeats(basket, id, principal);
    }
}
