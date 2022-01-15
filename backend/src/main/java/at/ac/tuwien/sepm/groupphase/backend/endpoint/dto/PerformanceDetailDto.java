package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PerformanceDetailDto {
    private List<TicketDto> tickets;
    private Long id;
    private String name;
    private LocalDateTime startTime;
    private Long duration;
    private EventDto eventDto;
    private ArtistDto artist;
    private HallDto hall;
    private Double priceMultiplicant;

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

    public void setTickets(List<TicketDto> tickets) {
        this.tickets = tickets;
    }

    public List<TicketDto> getTickets() {
        return tickets;
    }

    public Double getPriceMultiplicant() {
        return priceMultiplicant;
    }

    public void setPriceMultiplicant(Double priceMultiplicant) {
        this.priceMultiplicant = priceMultiplicant;
    }

    @Override
    public String toString() {
        return "PerformanceDetailDto{" +
            "tickets=" + tickets +
            ", id=" + id +
            ", name='" + name + '\'' +
            ", startTime=" + startTime +
            ", duration=" + duration +
            ", eventDto=" + eventDto +
            ", artist=" + artist +
            ", hall=" + hall +
            '}';
    }
}
