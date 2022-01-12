package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(100)")
    private String headline;

    @OneToOne()
    private Event event;

    private Long rating;

    private Long fsk;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(columnDefinition = "VARCHAR(255)")
    private String shortDescription;

    @Column(columnDefinition = "VARCHAR(1000)")
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

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
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
        return id.equals(news.id) && headline.equals(news.headline) && event.equals(news.event) && rating.equals(news.rating) && fsk.equals(news.fsk)
            && Objects.equals(shortDescription, news.shortDescription) && Objects.equals(longDescription, news.longDescription) && Objects.equals(createDate, news.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, event, rating, fsk, shortDescription, longDescription, createDate);
    }

    @Override
    public String toString() {
        return "News{"
            + "id=" + id
            + ", headline=" + headline
            + ", event=" + event
            + ", rating=" + rating
            + ", fsk=" + fsk
            + ", createDate=" + createDate
            + ", shortDescription='" + shortDescription + '\''
            + ", longDescription='" + longDescription + '\''
            + '}';
    }
}
