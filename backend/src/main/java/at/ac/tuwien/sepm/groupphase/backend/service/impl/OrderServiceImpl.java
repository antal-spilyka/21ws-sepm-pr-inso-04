package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.repository.OrderRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.OrderService;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Order> getAllReserved(String userEmail) {
        LOGGER.debug("Get all reserved Tickets");
        ApplicationUser user = userRepository.findUserByEmail(userEmail);
        if (user == null) {
            throw new ServiceException("User not found");
        }
        return orderRepository.getOrderByUserAndBoughtFalse(user);
    }

    @Override
    public List<Order> getAllBought(String userEmail) {
        LOGGER.debug("Get all bought Tickets");
        ApplicationUser user = userRepository.findUserByEmail(userEmail);
        if (user == null) {
            throw new ServiceException("User not found");
        }
        return orderRepository.getOrderByUserAndBoughtTrue(user);
    }
}
