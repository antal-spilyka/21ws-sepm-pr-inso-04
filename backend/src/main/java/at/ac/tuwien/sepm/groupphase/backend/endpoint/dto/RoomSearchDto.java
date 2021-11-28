package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class RoomSearchDto {
    String name;
    String eventPlaceName;

    public String getName() {
        return name;
    }

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
