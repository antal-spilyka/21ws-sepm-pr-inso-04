package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Artist {
    Long id;
    String firstName;
    String lastName;
    String bandName;
    String description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Column(name = "first_name", length = 100)
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "last_name", length = 100)
    public String getLastName() {
        return lastName;
    }

    @Column(length = 100)
    public String getBandName() {
        return bandName;
    }

    @Column(length = 1000)
    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Artist artist = (Artist) o;
        return Objects.equals(id, artist.id) && Objects.equals(firstName, artist.firstName) && Objects.equals(lastName, artist.lastName) && Objects.equals(bandName, artist.bandName) && Objects.equals(description, artist.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, bandName, description);
    }

    @Override
    public String toString() {
        return "Artist{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", bandName='" + bandName + '\'' +
            ", description='" + description + '\'' +
            '}';
    }
}
