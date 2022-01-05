package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class PerformanceMapper {

    private final ArtistMapper artistMapper;
    private final HallMapper hallMapper;
    private SectorMapper sectorMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public PerformanceMapper(ArtistMapper artistMapper, HallMapper hallMapper, SectorMapper sectorMapper) {
        this.artistMapper = artistMapper;
        this.hallMapper = hallMapper;
        this.sectorMapper = sectorMapper;
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
        performance.setPriceMultiplicant(performanceDto.getPriceMultiplicant());
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

        List<TicketDto> tickets = new ArrayList<>();
        for (Ticket ticket : performance.getTickets()) {
            TicketDto ticketDto = new TicketDto();
            if (ticket.getPosition() != null) {
                ticketDto.setRowIndex(ticket.getPosition().getRowIndex());
                ticketDto.setSeatIndex(ticket.getPosition().getSeatIndex());
            }
            ticketDto.setTicketType(ticket.getTypeOfTicket());
            ticketDto.setPrice(ticket.getPrice());
            ticketDto.setSectorDto(sectorMapper.entityToDto(ticket.getSector()));
            ticketDto.setId(ticket.getId());
            tickets.add(ticketDto);
        }
        performanceDto.setTickets(tickets);
        return performanceDto;
    }
}
