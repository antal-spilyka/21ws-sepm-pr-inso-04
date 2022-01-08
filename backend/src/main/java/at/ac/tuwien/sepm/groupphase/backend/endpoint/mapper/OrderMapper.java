package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OrderDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final TicketMapper ticketMapper;
    private final HallMapper hallMapper;
    private final ArtistMapper artistMapper;
    private final UserMapper userMapper;

    public OrderMapper(TicketMapper ticketMapper, UserMapper userMapper, HallMapper hallMapper, ArtistMapper artistMapper) {
        this.ticketMapper = ticketMapper;
        this.userMapper = userMapper;
        this.hallMapper = hallMapper;
        this.artistMapper = artistMapper;
    }

    public OrderDto orderToOrderDto(Order order) {
        LOGGER.trace("entityToDto(Ticket)");
        if (order == null) {
            return null;
        }
        PerformanceDto performanceDto = new PerformanceDto();
        performanceDto.setId(order.getPerformance().getId());
        performanceDto.setName(order.getPerformance().getName());
        performanceDto.setStartTime(order.getPerformance().getStartTime());
        performanceDto.setDuration(order.getPerformance().getDuration());
        performanceDto.setArtist(artistMapper.entityToDto(order.getPerformance().getArtist()));
        performanceDto.setHall(hallMapper.entityToDto(order.getPerformance().getHall()));

        OrderDto orderDto = new OrderDto();
        orderDto.setDateOfOrder(order.getDateOfOrder());
        orderDto.setId(order.getId());
        orderDto.setPerformanceDto(performanceDto);
        orderDto.setPrice(order.getPrize());
        orderDto.setUserDto(userMapper.applicationUserToUserDto(order.getUser()));
        orderDto.setBought(order.isBought());
        orderDto.setTicketDetailDtos(ticketMapper.ticketToTicketSimpleDto(order.getTickets()));
        orderDto.setRefunded(order.isRefunded());
        return orderDto;
    }

    public List<OrderDto> orderToOrderDto(List<Order> orders) {
        LOGGER.trace("entityToDto(List<Order>)");
        if (orders == null) {
            return null;
        }
        List<OrderDto> all = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            all.add(this.orderToOrderDto(orders.get(i)));
        }
        return all;
    }
}
