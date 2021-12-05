package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Artist {
    Long id;
    String bandName;
    String description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Column(length = 100)
    public String getBandName() {
        return bandName;
    }

    @Column(length = 1000)
    public String getDescription() {
        return description;
    }

    public Artist() {}

    public Artist(Long id, String bandName, String description) {
        this.id = id;
        this.bandName = bandName;
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
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
        return Objects.equals(id, artist.id) && Objects.equals(bandName, artist.bandName) && Objects.equals(description, artist.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bandName, description);
    }

    @Override
    public String toString() {
        return "Artist{" +
            "id=" + id +
            ", bandName='" + bandName + '\'' +
            ", description='" + description + '\'' +
            '}';
    }

    public static final class ArtistBuilder {
        private Long id;
        private String bandName;
        private String description;

        private ArtistBuilder() {
        }

        public static Artist.ArtistBuilder anArtist() {
            return new Artist.ArtistBuilder();
        }

        public Artist.ArtistBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public Artist.ArtistBuilder withBandName(String bandName) {
            this.bandName = bandName;
            return this;
        }

        public Artist.ArtistBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Artist build() {
            Artist artist = new Artist();
            artist.setId(id);
            artist.setBandName(bandName);
            artist.setDescription(description);
            return artist;
        }
    }
}
