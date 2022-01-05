package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Order;

import java.util.List;

public interface OrderService {

    /**
     * Method for returning all reserved tickets from the user.
     *
     * @param userEmail of the room
     * @return all the rooms stored in database
     */
    List<Order> getAllReserved(String userEmail);

    /**
     * Method for returning all bought tickets from the user.
     *
     * @param userEmail of the room
     * @return all the rooms stored in database
     */
    List<Order> getAllBought(String userEmail);
}
