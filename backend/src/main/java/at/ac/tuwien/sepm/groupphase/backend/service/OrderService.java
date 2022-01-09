package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OrderRefundDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SetOrderToBoughtDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;

import java.util.List;

public interface OrderService {

    /**
     * Method for returning all reserved orders from the user.
     *
     * @param userEmail of the order.
     * @return all the orders stored in database
     */
    List<Order> getAllReserved(String userEmail);

    /**
     * Method for returning all bought orders from the user.
     *
     * @param userEmail of the order.
     * @return all the orders stored in database
     */
    List<Order> getAllBought(String userEmail);

    /**
     * Method for returning all orders from the user.
     *
     * @param userEmail of the order.
     * @return all the orders stored in database
     */
    List<Order> getAll(String userEmail);

    /**
     * Method for setting an order to bought and update the paymentInformation for the order.
     *
     * @param setOrderToBoughtDto with the id´s of order and paymentInformation
     */
    void setOrderToBought(SetOrderToBoughtDto setOrderToBoughtDto);

    /**
     * Refunds a bought or reserved order.
     *
     * @param orderRefundDto containg orderId
     */
    void refund(OrderRefundDto orderRefundDto);
}
