package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class RoomInquiryDto {
    String name;
    String eventPlaceName;

    @NotNull()
    public String getName() {
        return name;
    }

    @NotNull()
    public String getEventPlaceName() {
        return eventPlaceName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEventPlaceName(String eventPlaceName) {
        this.eventPlaceName = eventPlaceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoomInquiryDto that = (RoomInquiryDto) o;
        return Objects.equals(name, that.name) && Objects.equals(eventPlaceName, that.eventPlaceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, eventPlaceName);
    }

    @Override
    public String toString() {
        return "RoomInquiryDto{" +
            "name='" + name + '\'' +
            ", eventPlaceName='" + eventPlaceName + '\'' +
            '}';
    }
}
