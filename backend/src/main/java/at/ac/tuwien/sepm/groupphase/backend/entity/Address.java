package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Address {
    Long id;
    String city;
    String state;
    Integer zip;
    String country;
    String description;
    String street;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Column(nullable = false, length = 100)
    public String getCity() {
        return city;
    }

    @Column(nullable = false, length = 100)
    public String getState() {
        return state;
    }

    @Column(nullable = false, length = 10)
    public Integer getZip() {
        return zip;
    }

    @Column(nullable = false, length = 100)
    public String getCountry() {
        return country;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(id, address.id) && Objects.equals(city, address.city) && Objects.equals(state, address.state) && Objects.equals(zip, address.zip) && country == address.country;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, state, zip, country);
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + id +
            ", city='" + city + '\'' +
            ", state='" + state + '\'' +
            ", zip=" + zip +
            ", country=" + country +
            '}';
    }
}
