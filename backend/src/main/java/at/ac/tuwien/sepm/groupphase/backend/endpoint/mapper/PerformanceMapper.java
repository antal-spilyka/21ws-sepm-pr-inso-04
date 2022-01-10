package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDetailDto;
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
    private SectorMapper sectorMapper;
    private TicketMapper ticketMapper;
    private final OrderMapper orderMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public PerformanceMapper(ArtistMapper artistMapper, HallMapper hallMapper,
                             SectorMapper sectorMapper, TicketMapper ticketMapper, OrderMapper orderMapper) {
        this.artistMapper = artistMapper;
        this.hallMapper = hallMapper;
        this.sectorMapper = sectorMapper;
        this.ticketMapper = ticketMapper;
        this.orderMapper = orderMapper;
    }

    public Performance dtoToEntity(PerformanceDto performanceDto, Event event) {
        LOGGER.trace("Mapping {}", performanceDto);
        Performance performance = new Performance();
        performance.setId(performanceDto.getId());
        performance.setName(performanceDto.getName());
        performance.setStartTime(performanceDto.getStartTime());
        performance.setDuration(performanceDto.getDuration());
        performance.setEvent(event);
        performance.setArtist(artistMapper.dtoToEntity(performanceDto.getArtist()));
        performance.setHall(hallMapper.dtoToEntity(performanceDto.getHall()));
        performance.setPriceMultiplicant(performanceDto.getPriceMultiplicant() != null ? performanceDto.getPriceMultiplicant() : 1);
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
        performanceDto.setPriceMultiplicant(performance.getPriceMultiplicant());
        return performanceDto;
    }

    public PerformanceDetailDto entityToDetailDto(Performance performance) {
        LOGGER.trace("Mapping {}", performance);
        PerformanceDetailDto performanceDto = new PerformanceDetailDto();
        performanceDto.setId(performance.getId());
        performanceDto.setName(performance.getName());
        performanceDto.setStartTime(performance.getStartTime());
        performanceDto.setDuration(performance.getDuration());
        performanceDto.setArtist(artistMapper.entityToDto(performance.getArtist()));
        performanceDto.setHall(hallMapper.entityToDto(performance.getHall()));
        performanceDto.setTickets(ticketMapper.ticketToTicketDto(performance.getTickets()));
        return performanceDto;
    }

    public PerformanceDto detailDtoToDto(PerformanceDetailDto performance) {
        LOGGER.trace("Mapping {}", performance);
        PerformanceDto performanceDto = new PerformanceDto();
        performanceDto.setId(performance.getId());
        performanceDto.setName(performance.getName());
        performanceDto.setStartTime(performance.getStartTime());
        performanceDto.setDuration(performance.getDuration());
        performanceDto.setArtist(performance.getArtist());
        performanceDto.setHall(performance.getHall());
        return performanceDto;
    }
}
