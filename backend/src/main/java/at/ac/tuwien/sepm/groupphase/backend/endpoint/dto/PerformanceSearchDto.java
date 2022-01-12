package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class PerformanceSearchDto {
    private String eventName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime;
    private String hallName;
    private Integer price;
    private Integer page;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public PerformanceSearchDto() {}

    public PerformanceSearchDto(String eventName, LocalDateTime startTime, String hallName) {
        this.eventName = eventName;
        this.startTime = startTime;
        this.hallName = hallName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    @Override
    public String toString() {
        return "PerformanceSearchDto{" +
            "eventName='" + eventName + '\'' +
            ", startTime=" + startTime +
            ", hallName='" + hallName + '\'' +
            ", price=" + price +
            '}';
    }
}
