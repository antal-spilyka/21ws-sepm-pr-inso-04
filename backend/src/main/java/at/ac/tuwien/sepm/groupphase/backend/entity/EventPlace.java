package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class EventPlace {
    private String name;
    private Address address;

    public EventPlace() {}

    public EventPlace(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    @Id
    public String getName() {
        return name;
    }

    @ManyToOne()
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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventPlace that = (EventPlace) o;
        return Objects.equals(name, that.name) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

    @Override
    public String toString() {
        return "EventPlace{" +
            "name='" + name + '\'' +
            ", address=" + address +
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
