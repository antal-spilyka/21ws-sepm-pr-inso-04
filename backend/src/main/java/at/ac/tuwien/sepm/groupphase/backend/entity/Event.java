package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Event {
    Long id;
    String name;
    Integer duration;
    String content;
    LocalDateTime dateTime;
    Category category;
    Room room;
    Artist artist;

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
}
