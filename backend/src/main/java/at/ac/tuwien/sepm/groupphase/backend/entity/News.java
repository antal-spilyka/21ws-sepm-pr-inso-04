package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Column;
import javax.persistence.CascadeType;
import java.util.Objects;

@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    private Event event;

    @Column(nullable = false)
    private Long rating;

    @Column(nullable = false)
    private Long fsk;

    private String shortDescription;
    private String longDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof News)) {
            return false;
        }
        News news = (News) o;
        return id.equals(news.id) && event.equals(news.event) && rating.equals(news.rating) && fsk.equals(news.fsk) && Objects.equals(shortDescription, news.shortDescription) && Objects.equals(longDescription, news.longDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, event, rating, fsk, shortDescription, longDescription);
    }
}