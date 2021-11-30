package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class EventPlaceSearchDto {
    String name;

    @NotNull()
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventPlaceSearchDto that = (EventPlaceSearchDto) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "EventPlaceSearchDto{" +
            "name='" + name + '\'' +
            '}';
    }
}
