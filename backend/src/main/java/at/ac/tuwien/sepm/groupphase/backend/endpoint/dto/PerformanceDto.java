package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class PerformanceDto {
    private Long id;
    private String name;
    private LocalDateTime startTime;
    private Long duration;
    private EventDto eventDto;
    private ArtistDto artist;
    private HallDto hall;
    private Double priceMultiplicant;

    public PerformanceDto() {}

    public PerformanceDto(Long id, String name, LocalDateTime startTime, Long duration,
                          EventDto eventDto, ArtistDto artist, HallDto hall, Double priceMultiplicant) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.eventDto = eventDto;
        this.artist = artist;
        this.hall = hall;
        this.priceMultiplicant = priceMultiplicant;
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

    public EventDto getEventDto() {
        return eventDto;
    }

    public void setEventDto(EventDto eventDto) {
        this.eventDto = eventDto;
    }

    public ArtistDto getArtist() {
        return artist;
    }

    public void setArtist(ArtistDto artist) {
        this.artist = artist;
    }

    public HallDto getHall() {
        return hall;
    }

    public void setHall(HallDto hall) {
        this.hall = hall;
    }

    public Double getPriceMultiplicant() {
        return priceMultiplicant;
    }

    public void setPriceMultiplicant(Double priceMultiplicant) {
        this.priceMultiplicant = priceMultiplicant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PerformanceDto that = (PerformanceDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(startTime, that.startTime)
               && Objects.equals(duration, that.duration) && Objects.equals(eventDto, that.eventDto) && Objects.equals(artist, that.artist)
               && Objects.equals(hall, that.hall) && Objects.equals(priceMultiplicant, that.priceMultiplicant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startTime, duration, eventDto, artist, hall, priceMultiplicant);
    }

    @Override
    public String toString() {
        return "PerformanceDto{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", startTime=" + startTime +
            ", duration=" + duration +
            ", event=" + null +
            ", artist=" + artist +
            ", hall=" + hall +
            ", priceMultiplicant=" + priceMultiplicant +
            '}';
    }
}
