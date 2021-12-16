package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Event {
    private Long id;
    private String name;
    private Integer duration;
    private String content;
    private LocalDateTime dateTime;
    private Category category;
    private Room room;
    private Artist artist;
    private String description;

    public Event() {}

    public Event(Long id, String name, Integer duration, String content, LocalDateTime dateTime,
                 Category category, Room room, Artist artist, String description) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.content = content;
        this.dateTime = dateTime;
        this.category = category;
        this.room = room;
        this.artist = artist;
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private Long duration;

    @OneToMany(mappedBy = "event", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<Performance> performances = new ArrayList<>();

    @OneToOne
    private EventPlace eventPlace;

    @Column
    private String description;

    @Column (nullable = false)
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    public EventPlace getEventPlace() {
        return eventPlace;
    }

    public void setEventPlace(EventPlace eventPlace) {
        this.eventPlace = eventPlace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        String performanceToString;
        if (performances == null || performances.size() <= 0) {
            performanceToString = "null";
        } else {
            performanceToString = performances.toString();
        }

        return "Event{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", startTime=" + startTime +
            ", duration=" + duration +
            ", performances=" + performanceToString +
            ", eventPlace=" + eventPlace +
            ", description='" + description + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(name, event.name)
            && Objects.equals(startTime, event.startTime) && Objects.equals(duration, event.duration)
            && Objects.equals(performances, event.performances) && Objects.equals(eventPlace, event.eventPlace)
            && Objects.equals(description, event.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startTime, duration, performances, eventPlace, description);
    }

    public static final class EventBuilder {
        private Long id;
        private String name;
        private Integer duration;
        private String content;
        private LocalDateTime dateTime;
        private Category category;
        private Room room;
        private Artist artist;
        private String description;

        private EventBuilder() {
        }

        public static Event.EventBuilder anEvent() {
            return new Event.EventBuilder();
        }

        public Event.EventBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public Event.EventBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public Event.EventBuilder withDuration(Integer duration) {
            this.duration = duration;
            return this;
        }

        public Event.EventBuilder withContent(String content) {
            this.content = content;
            return this;
        }

        public Event.EventBuilder withDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Event.EventBuilder withCategory(Category category) {
            this.category = category;
            return this;
        }

        public Event.EventBuilder withRoom(Room room) {
            this.room = room;
            return this;
        }

        public Event.EventBuilder withArtist(Artist artist) {
            this.artist = artist;
            return this;
        }

        public Event.EventBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Event build() {
            Event event = new Event();
            event.setId(id);
            event.setName(name);
            event.setDuration(duration);
            event.setContent(content);
            event.setDateTime(dateTime);
            event.setCategory(category);
            event.setRoom(room);
            event.setArtist(artist);
            event.setDescription(description);
            return event;
        }
    }
}
