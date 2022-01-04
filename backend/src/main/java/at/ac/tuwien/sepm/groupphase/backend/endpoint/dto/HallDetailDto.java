package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.Objects;

public class HallDetailDto {
    private Long id;
    private String name;
    private int standingPlaces;
    private EventPlaceDto eventPlaceDto;
    private HallplanElementDto[][] rows;
    private SectorDto[] sectors;

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

    public HallplanElementDto[][] getRows() {
        return rows;
    }

    public void setRows(HallplanElementDto[][] rows) {
        this.rows = rows;
    }

    public SectorDto[] getSectors() {
        return sectors;
    }

    public void setSectors(SectorDto[] sectors) {
        this.sectors = sectors;
    }

    public int getStandingPlaces() {
        return standingPlaces;
    }

    public void setStandingPlaces(int standingPlaces) {
        this.standingPlaces = standingPlaces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HallDetailDto hallDto = (HallDetailDto) o;
        return Objects.equals(id, hallDto.id) && Objects.equals(name, hallDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, eventPlaceDto);
    }
}
