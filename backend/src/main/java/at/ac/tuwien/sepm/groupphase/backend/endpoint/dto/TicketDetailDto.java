package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.Objects;

public class TicketDetailDto {
    PerformanceDto performanceDto;
    String ticketType;
    double price;

    public PerformanceDto getPerformanceDto() {
        return performanceDto;
    }

    public void setPerformanceDto(PerformanceDto performanceDto) {
        this.performanceDto = performanceDto;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
