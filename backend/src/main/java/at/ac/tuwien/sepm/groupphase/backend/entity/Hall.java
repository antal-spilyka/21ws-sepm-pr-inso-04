package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    String name;

    @ManyToOne()
    EventPlace eventPlace;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

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
        Hall hall = (Hall) o;
        return Objects.equals(id, hall.id) && Objects.equals(name, hall.name) && Objects.equals(eventPlace, hall.eventPlace);
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