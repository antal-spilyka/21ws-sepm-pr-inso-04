package at.ac.tuwien.sepm.groupphase.backend.entity;

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

    @Column(nullable = false)
    private int standingPlaces;

    @ManyToOne()
    private EventPlace eventPlace;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Sector> sectors;

    @OneToMany(cascade = CascadeType.REMOVE,
        fetch = FetchType.LAZY)
    private List<HallplanElement> rows;

    public Hall() {
    }

    public Hall(Long id, String name, EventPlace eventPlace) {
        this.id = id;
        this.name = name;
        this.eventPlace = eventPlace;
    }

    public int getStandingPlaces() {
        return standingPlaces;
    }

    public void setStandingPlaces(int standingPlaces) {
        this.standingPlaces = standingPlaces;
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

    public List<Sector> getSectors() {
        return sectors;
    }

    public void setSectors(List<Sector> sectors) {
        this.sectors = sectors;
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
            ", sectors" + sectors.toString() +
            '}';
    }

    public void setRow(List<HallplanElement> rows) {
        this.rows = rows;
    }
}
