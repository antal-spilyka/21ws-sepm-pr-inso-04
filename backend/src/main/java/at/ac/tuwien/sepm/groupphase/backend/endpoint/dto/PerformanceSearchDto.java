package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.time.LocalDateTime;

public class PerformanceSearchDto {
    private String eventName;
    private LocalDateTime startTime;
    private String hallName;

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
            '}';
    }
}
