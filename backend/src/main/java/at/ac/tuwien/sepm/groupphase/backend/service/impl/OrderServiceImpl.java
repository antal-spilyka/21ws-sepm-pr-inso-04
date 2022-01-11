package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OrderRefundDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SetOrderToBoughtDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.entity.PaymentInformation;
import at.ac.tuwien.sepm.groupphase.backend.repository.OrderRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PaymentInformationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.OrderService;
import at.ac.tuwien.sepm.groupphase.backend.service.OrderValidationService;
import org.hibernate.Transaction;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    UserRepository userRepository;
    PaymentInformationRepository paymentInformationRepository;
    TicketRepository ticketRepository;
    OrderValidationService orderValidationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public OrderServiceImpl(
        OrderRepository orderRepository,
        UserRepository userRepository,
        PaymentInformationRepository paymentInformationRepository,
        TicketRepository ticketRepository,
        OrderValidationService orderValidationService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.paymentInformationRepository = paymentInformationRepository;
        this.ticketRepository = ticketRepository;
        this.orderValidationService = orderValidationService;
    }

    @Override
    @Transactional
    public List<Order> getAllReserved(String userEmail) {
        LOGGER.debug("Get all reserved orders");
        ApplicationUser user = userRepository.findUserByEmail(userEmail);
        if (user == null) {
            throw new ServiceException("User not found");
        }
        return orderRepository.getOrderByUserAndBoughtFalse(user);
    }

    @Override
    @Transactional
    public List<Order> getAllBought(String userEmail) {
        LOGGER.debug("Get all bought orders");
        ApplicationUser user = userRepository.findUserByEmail(userEmail);
        if (user == null) {
            throw new ServiceException("User not found");
        }
        return orderRepository.getOrderByUserAndBoughtTrue(user);
    }

    @Override
    @Transactional
    public List<Order> getAll(String userEmail) {
        LOGGER.debug("Get all orders");
        ApplicationUser user = userRepository.findUserByEmail(userEmail);
        if (user == null) {
            throw new ServiceException("User not found");
        }
        return orderRepository.getOrderByUser(user);
    }

    @Override
    public void setOrderToBought(SetOrderToBoughtDto setOrderToBoughtDto) {
        Optional<Order> optionalOrder = orderRepository.findById(setOrderToBoughtDto.getOrderId());
        if (optionalOrder.isEmpty()) {
            throw new ServiceException("No matching Order found");
        }
        Optional<PaymentInformation> optionalPaymentInformation = paymentInformationRepository.findById(setOrderToBoughtDto.getPaymentInformationId());
        if (optionalPaymentInformation.isEmpty()) {
            throw new ServiceException("No matching Order found");
        }

        Order order = optionalOrder.get();
        if (order.isRefunded()) {
            throw new ServiceException("Cannot buy a refunded order!");
        }
        order.setBought(true);
        order.setPaymentInformation(optionalPaymentInformation.get());
        order = orderRepository.save(order);
        orderValidationService.createValidation(order);
    }

    @Override
    public void refund(OrderRefundDto orderRefundDto) {
        ticketRepository.refund(orderRefundDto.getOrderId());
        orderRepository.refund(orderRefundDto.getOrderId());
    }
}
