package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class Room {
    Long id;
    String name;
    EventPlace eventPlace;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long  getId() {
        return id;
    }

    @Column(nullable = false, length = 100)
    public String getName() {
        return name;
    }

    @ManyToOne()
    public EventPlace getEventPlace() {
        return eventPlace;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEventPlace(EventPlace eventPlace) {
        this.eventPlace = eventPlace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        return Objects.equals(id, room.id) && Objects.equals(name, room.name) && Objects.equals(eventPlace, room.eventPlace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, eventPlace);
    }

    @Override
    public String toString() {
        return "Room{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", eventPlace=" + eventPlace +
            '}';
    }
}
