package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.TicketMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketEndpoint {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/tickets";

    @Autowired
    public TicketEndpoint(TicketService ticketService, TicketMapper ticketMapper) {
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
    }

    @GetMapping("/{email}/reserved")
    public List<TicketDetailDto> getReservedTickets(@PathVariable String email) {
        LOGGER.info("GET " + BASE_URL + "/{}/reserved", email);
        return this.ticketMapper.ticketToTicketDetailDto(ticketService.getAllReserved(email));
    }

    @GetMapping("/{email}/bought")
    public List<TicketDetailDto> getBoughtTickets(@PathVariable String email) {
        LOGGER.info("GET " + BASE_URL + "/{}/bought", email);
        return this.ticketMapper.ticketToTicketDetailDto(ticketService.getAllBought(email));
    }
}
