package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.Objects;

public class EventSearchDto {
    Integer duration;
    String description;

    @Override
    public String toString() {
        return "EventSearchDto{" +
            "duration=" + duration +
            ", description='" + description + '\'' +
            '}';
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventSearchDto that = (EventSearchDto) o;
        return Objects.equals(duration, that.duration) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration, description);
    }
}
