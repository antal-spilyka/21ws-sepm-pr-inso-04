package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class TopTenEventsDto {
    private EventDto eventDto;
    private Long ticketsSold;

    public EventDto getEvent() {
        return eventDto;
    }

    public void setEvent(EventDto event) {
        this.eventDto = event;
    }

    public Long getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(Long ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    @Override
    public String toString() {
        return "TopTenEventsDto{" +
            "event=" + eventDto +
            ", ticketsSold=" + ticketsSold +
            '}';
    }
}
