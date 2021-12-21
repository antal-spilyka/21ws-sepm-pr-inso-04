package at.ac.tuwien.sepm.groupphase.backend.entity;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Entity
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne()
    private EventPlace eventPlace;

    @ManyToOne()
    private Sector sector;

    @OneToMany(cascade = CascadeType.REMOVE,
        fetch = FetchType.LAZY)
    private List<HallplanElement> rows;

    public Hall() {}

    public Hall(Long id, String name, EventPlace eventPlace) {
        this.id = id;
        this.name = name;
        this.eventPlace = eventPlace;
    }

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

    public List<HallplanElement> getRows() {
        return rows;
    }

    public void setRows(List<HallplanElement> rows) {
        this.rows = rows;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
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

    public void setRow(List<HallplanElement> rows) {
        this.rows = rows;
    }
}
