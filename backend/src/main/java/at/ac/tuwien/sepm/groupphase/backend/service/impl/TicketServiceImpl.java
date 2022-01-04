package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.TicketService;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    TicketRepository ticketRepository;
    UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public TicketServiceImpl(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Ticket> getAllReserved(String userEmail) {
        LOGGER.debug("Get all reserved Tickets");
        ApplicationUser user = userRepository.findUserByEmail(userEmail);
        if (user == null) {
            throw new ServiceException("User not found");
        }
        return ticketRepository.getTicketByUserAndBoughtFalse(user);
    }

    @Override
    public List<Ticket> getAllBought(String userEmail) {
        LOGGER.debug("Get all bought Tickets");
        ApplicationUser user = userRepository.findUserByEmail(userEmail);
        if (user == null) {
            throw new ServiceException("User not found");
        }
        return ticketRepository.getTicketByUserAndBoughtTrue(user);
    }
}
