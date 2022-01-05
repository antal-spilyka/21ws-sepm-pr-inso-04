package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {
    private PerformanceDto performanceDto;
    private UserDto userDto;
    private List<TicketDetailDto> ticketDetailDtos;
    private double price;
    private boolean bought;
    private LocalDateTime dateOfOrder;

    public PerformanceDto getPerformanceDto() {
        return performanceDto;
    }

    public void setPerformanceDto(PerformanceDto performanceDto) {
        this.performanceDto = performanceDto;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public List<TicketDetailDto> getTicketDetailDtos() {
        return ticketDetailDtos;
    }

    public void setTicketDetailDtos(List<TicketDetailDto> ticketDetailDtos) {
        this.ticketDetailDtos = ticketDetailDtos;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public LocalDateTime getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(LocalDateTime dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }
}
