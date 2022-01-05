package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OrderDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.OrderMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.OrderService;
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
public class OrderEndpoint {
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/tickets";

    @Autowired
    public OrderEndpoint(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping("/{email}/reserved")
    public List<OrderDto> getReservedTickets(@PathVariable String email) {
        LOGGER.info("GET " + BASE_URL + "/{}/reserved", email);
        return this.orderMapper.orderToOrderDto(orderService.getAllReserved(email));
    }

    @GetMapping("/{email}/bought")
    public List<OrderDto> getBoughtTickets(@PathVariable String email) {
        LOGGER.info("GET " + BASE_URL + "/{}/bought", email);
        return this.orderMapper.orderToOrderDto(orderService.getAllBought(email));
    }
}
