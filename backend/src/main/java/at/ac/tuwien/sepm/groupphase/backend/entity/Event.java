package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
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
    public Long getId() {
        return id;
    }

    @Column(nullable = false, length = 100)
    public String getName() {
        return name;
    }

    @Column(nullable = false, length = 100)
    public Integer getDuration() {
        return duration;
    }

    @Column(nullable = false, length = 1000)
    public String getContent() {
        return content;
    }

    @Column(nullable = false)
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @ManyToOne()
    public Category getCategory() {
        return category;
    }

    @ManyToOne()
    public Room getRoom() {
        return room;
    }

    @ManyToOne()
    public Artist getArtist() {
        return artist;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        return Objects.equals(id, event.id)
            && Objects.equals(name, event.name)
            && Objects.equals(duration, event.duration)
            && Objects.equals(content, event.content)
            && Objects.equals(dateTime, event.dateTime)
            && Objects.equals(category, event.category)
            && Objects.equals(room, event.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, duration, content, dateTime, category, room);
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", duration=" + duration +
            ", content='" + content + '\'' +
            ", dateTime=" + dateTime +
            ", category=" + category +
            ", room=" + room +
            '}';
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
