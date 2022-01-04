package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;

import java.util.List;

public interface TicketService {

    /**
     * Method for returning all reserved tickets from the user.
     *
     * @param userEmail of the room
     * @return all the rooms stored in database
     */
    List<Ticket> getAllReserved(String userEmail);

    /**
     * Method for returning all bought tickets from the user.
     *
     * @param userEmail of the room
     * @return all the rooms stored in database
     */
    List<Ticket> getAllBought(String userEmail);
}
