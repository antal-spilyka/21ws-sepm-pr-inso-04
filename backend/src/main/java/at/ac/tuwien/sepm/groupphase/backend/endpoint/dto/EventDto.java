package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class EventDto {
    private Long id;
    private String name;
    private LocalDateTime startTime;
    private Long duration;
    private List<PerformanceDto> performances;
    private EventPlaceDto eventPlace;
    private String description;
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public List<PerformanceDto> getPerformances() {
        if (performances == null) {
            return null;
        } else {
            return performances;
        }
    }

    public void setPerformances(List<PerformanceDto> performances) {
        this.performances = performances;
    }

    public EventPlaceDto getEventPlace() {
        return eventPlace;
    }

    public void setEventPlace(EventPlaceDto eventPlace) {
        this.eventPlace = eventPlace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        String performanceToString;
        if (performances == null || performances.size() <= 0) {
            performanceToString = "null";
        } else {
            performanceToString = performances.toString();
        }

        return "EventDto{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", startTime=" + startTime +
            ", duration=" + duration +
            ", performances=" + performanceToString +
            ", eventPlace=" + eventPlace.toString() +
            ", description='" + description  +
            ", category='" + category + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventDto)) {
            return false;
        }
        EventDto eventDto = (EventDto) o;
        return Objects.equals(id, eventDto.id) && Objects.equals(name, eventDto.name) && Objects.equals(startTime, eventDto.startTime)
            && Objects.equals(duration, eventDto.duration) && Objects.equals(performances, eventDto.performances)
            && Objects.equals(eventPlace, eventDto.eventPlace) && Objects.equals(description, eventDto.description)
            && Objects.equals(category, eventDto.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startTime, duration, performances, eventPlace, description, category);
    }
}
