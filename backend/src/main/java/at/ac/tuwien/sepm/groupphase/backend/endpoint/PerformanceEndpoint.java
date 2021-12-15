package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.PerformanceMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.PerformanceService;
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

import java.lang.invoke.MethodHandles;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/performances")
public class PerformanceEndpoint {

    private final PerformanceService performanceService;
    private final PerformanceMapper performanceMapper;
    private final EventMapper eventMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass()); // todo logger

    public PerformanceEndpoint(PerformanceService performanceService, PerformanceMapper performanceMapper, EventMapper eventMapper) {
        this.performanceService = performanceService;
        this.performanceMapper = performanceMapper;
        this.eventMapper = eventMapper;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @Operation(summary = "persist new performance.")
    public PerformanceDto savePerformance(@RequestBody @Validated PerformanceDto performanceDto) {
        LOGGER.info("POST /api/v1/performance/{}", performanceDto);
        return performanceMapper.entityToDto(performanceService.save(performanceDto), performanceDto.getEvent());
    }

    @Secured("ROLE_USER")
    @GetMapping("/search")
    @Operation(summary = "Find events by search parameters.")
    public Stream<PerformanceDto> findEventsByDateTime(@Validated PerformanceSearchDto performanceSearchDto) {
        LOGGER.info("GET /api/v1/performance/search {}", performanceSearchDto.toString());
        return this.performanceService.findPerformanceByDateTime(performanceSearchDto);
    }

    @Secured("ROLE_USER")
    @GetMapping(value = "/artist/{id}")
    @Operation(summary = "Find performances for specified artist.")
    public Stream<PerformanceDto> findEventsByDateTime(@PathVariable("id") Long id) {
        LOGGER.info("GET /api/v1/performance/id {}", id);
        return this.performanceService.findPerformanceForArtist(id);
    }
}
