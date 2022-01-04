package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.NewsDto;
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
    private final PerformanceMapper performanceMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public TicketMapper(PerformanceMapper performanceMapper) {
        this.performanceMapper = performanceMapper;
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
        TicketDetailDto ticketDetailDto = new TicketDetailDto();
        ticketDetailDto.setTicketType(ticket.getTypeOfTicket());
        ticketDetailDto.setPerformanceDto(performanceMapper.entityToDto(ticket.getPerformance(), null));
        ticketDetailDto.setPrice(ticket.getPrice());
        return ticketDetailDto;
    }
}
