package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.HallplanElement;

import java.util.List;
import java.util.Objects;

public class HallDto {
    private Long id;
    private String name;
    private int standingPlaces;
    private List<SectorDto> sectors;
    private List<HallplanElement> rows;
    private EventPlaceDto eventPlaceDto;

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

    public List<HallplanElement> getRows() {
        return rows;
    }

    public void setRows(List<HallplanElement> rows) {
        this.rows = rows;
    }

    public int getStandingPlaces() {
        return standingPlaces;
    }

    public void setStandingPlaces(int standingPlaces) {
        this.standingPlaces = standingPlaces;
    }

    public List<SectorDto> getSectors() {
        return sectors;
    }

    public void setSectors(List<SectorDto> sectors) {
        this.sectors = sectors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HallDto hallDto = (HallDto) o;
        return Objects.equals(name, hallDto.name) && Objects.equals(eventPlaceDto, hallDto.eventPlaceDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, eventPlaceDto);
    }

    @Override
    public String toString() {
        return "HallDto{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", eventPlaceDto=" + eventPlaceDto +
            '}';
    }
}
