package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RoomSearchDto {
    String name;
    String eventPlaceName;

    @NotNull()
    public String getName() {
        return name;
    }

    @NotNull()
    @NotBlank
    public String getEventPlaceName() {
        return eventPlaceName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEventPlaceName(String eventPlaceName) {
        this.eventPlaceName = eventPlaceName;
    }
}
