package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.NewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Component
public class TicketMapper {
    private final HallMapper hallMapper;
    private final ArtistMapper artistMapper;
    private final SectorMapper sectorMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public TicketMapper(HallMapper hallMapper, ArtistMapper artistMapper, SectorMapper sectorMapper) {
        this.hallMapper = hallMapper;
        this.artistMapper = artistMapper;
        this.sectorMapper = sectorMapper;
    }

    public List<TicketDetailDto> ticketToTicketDetailDto(List<Ticket> tickets) {
        LOGGER.trace("entityToDto(List<Ticket>)");
        if (tickets == null) {
            return null;
        }
        List<TicketDetailDto> all = new ArrayList<>();
        for (int i = 0; i < tickets.size(); i++) {
            all.add(this.ticketToTicketDetailDto(tickets.get(i)));
        }
        return all;
    }

    public TicketDetailDto ticketToTicketDetailDto(Ticket ticket) {
        LOGGER.trace("entityToDto(Ticket)");
        if (ticket == null) {
            return null;
        }
        PerformanceDto performanceDto = new PerformanceDto();
        performanceDto.setId(ticket.getPerformance().getId());
        performanceDto.setName(ticket.getPerformance().getName());
        performanceDto.setStartTime(ticket.getPerformance().getStartTime());
        performanceDto.setDuration(ticket.getPerformance().getDuration());
        performanceDto.setArtist(artistMapper.entityToDto(ticket.getPerformance().getArtist()));
        performanceDto.setHall(hallMapper.entityToDto(ticket.getPerformance().getHall()));

        TicketDetailDto ticketDetailDto = new TicketDetailDto();
        ticketDetailDto.setPerformanceDto(performanceDto);
        ticketDetailDto.setTicketType(ticket.getTypeOfTicket());
        ticketDetailDto.setPrice(ticket.getPrice());
        return ticketDetailDto;
    }

    public TicketDto ticketToTicketDto(Ticket ticket) {
        LOGGER.trace("entityToDto(Ticket)");
        if (ticket == null) {
            return null;
        }
        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(ticket.getId());
        ticketDto.setSectorDto(sectorMapper.entityToDto(ticket.getSector()));
        ticketDto.setPrice(ticket.getPrice());
        ticketDto.setTicketType(ticket.getTypeOfTicket());
        if (ticket.getPosition() != null) {
            ticketDto.setRowIndex(ticket.getPosition().getRowIndex());
            ticketDto.setSeatIndex(ticket.getPosition().getSeatIndex());
        }
        return ticketDto;
    }

    public List<TicketDto> ticketToTicketDto(List<Ticket> tickets) {
        LOGGER.trace("entityToDto(List<Ticket>)");
        if (tickets == null) {
            return null;
        }
        List<TicketDto> all = new ArrayList<>();
        for (int i = 0; i < tickets.size(); i++) {
            all.add(this.ticketToTicketDto(tickets.get(i)));
        }
        return all;
    }
}
