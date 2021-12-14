package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class PerformanceMapper {

    private final ArtistMapper artistMapper;
    private final HallMapper hallMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public PerformanceMapper(ArtistMapper artistMapper, HallMapper hallMapper) {
        this.artistMapper = artistMapper;
        this.hallMapper = hallMapper;
    }

    public Performance dtoToEntity(PerformanceDto performanceDto, Event event) {
        LOGGER.trace("Mapping {}", performanceDto);
        Performance performance =  new Performance();
        performance.setId(performanceDto.getId());
        performance.setName(performanceDto.getName());
        performance.setStartTime(performanceDto.getStartTime());
        performance.setDuration(performanceDto.getDuration());
        performance.setEvent(event);
        performance.setArtist(artistMapper.dtoToEntity(performanceDto.getArtist()));
        performance.setHall(hallMapper.dtoToEntity(performanceDto.getHall()));
        return performance;
    }

    public PerformanceDto entityToDto(Performance performance, EventDto eventDto) {
        LOGGER.trace("Mapping {}", performance);
        PerformanceDto performanceDto = new PerformanceDto();
        performanceDto.setId(performance.getId());
        performanceDto.setName(performance.getName());
        performanceDto.setStartTime(performance.getStartTime());
        performanceDto.setDuration(performance.getDuration());
        //performanceDto.setEvent(eventDto);
        performanceDto.setArtist(artistMapper.entityToDto(performance.getArtist()));
        performanceDto.setHall(hallMapper.entityToDto(performance.getHall()));
        return performanceDto;
    }

}
