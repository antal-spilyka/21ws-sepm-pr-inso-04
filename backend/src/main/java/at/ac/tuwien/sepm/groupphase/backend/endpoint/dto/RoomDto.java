package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class RoomDto {
    Long id;
    String name;
    EventPlaceDto eventPlaceDto;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public EventPlaceDto getEventPlaceDto() {
        return eventPlaceDto;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEventPlaceDto(EventPlaceDto eventPlaceDto) {
        this.eventPlaceDto = eventPlaceDto;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoomDto roomDto = (RoomDto) o;
        return Objects.equals(name, roomDto.name) && Objects.equals(eventPlaceDto, roomDto.eventPlaceDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, eventPlaceDto);
    }
}
