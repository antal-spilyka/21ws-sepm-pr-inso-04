package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
public class EventPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @OneToOne(
        orphanRemoval = true,
        cascade = CascadeType.ALL)
    Address address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventPlace)) {
            return false;
        }
        EventPlace that = (EventPlace) o;
        return id.equals(that.id) && name.equals(that.name) && address.equals(that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address);
    }

    @Override
    public String toString() {
        return "EventPlace{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", address=" + address.toString() +
            '}';
    }

    public static final class EventPlaceBuilder {
        private String name;
        private Address address;

        private EventPlaceBuilder() {
        }

        public static EventPlace.EventPlaceBuilder anEventPlace() {
            return new EventPlace.EventPlaceBuilder();
        }

        public EventPlace.EventPlaceBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public EventPlace.EventPlaceBuilder withAddress(Address address) {
            this.address = address;
            return this;
        }

        public EventPlace build() {
            EventPlace eventPlace = new EventPlace();
            eventPlace.setName(name);
            eventPlace.setAddress(address);
            return eventPlace;
        }
    }
}
