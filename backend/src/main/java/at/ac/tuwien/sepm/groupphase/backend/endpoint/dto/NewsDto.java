package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotNull;

public class NewsDto {

    private Long id;

    @NotNull(message = "Event must not be null")
    private EventDto event;

    @NotNull(message = "Rating must not be null")
    private Long rating;

    @NotNull(message = "FSK must not be null")
    private Long fsk;

    private String shortDescription;
    private String longDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventDto getEvent() {
        return event;
    }

    public void setEvent(EventDto event) {
        this.event = event;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Long getFsk() {
        return fsk;
    }

    public void setFsk(Long fsk) {
        this.fsk = fsk;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
}
