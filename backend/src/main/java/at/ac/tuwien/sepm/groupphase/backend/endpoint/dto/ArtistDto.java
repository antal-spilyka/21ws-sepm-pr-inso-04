package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.Objects;

public class ArtistDto {
    Long id;
    String firstName;
    String lastName;
    String bandName;
    String description;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDescription() {
        return description;
    }

    public String getBandName() {
        return bandName;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArtistDto artistDto = (ArtistDto) o;
        return Objects.equals(id, artistDto.id) && Objects.equals(firstName, artistDto.firstName) && Objects.equals(lastName, artistDto.lastName) && Objects.equals(description, artistDto.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, description);
    }

    @Override
    public String toString() {
        return "ArtistDto{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", description='" + description + '\'' +
            '}';
    }

    public static final class ArtistDtoBuilder {
        Long id;
        String firstName;
        String lastName;
        String description;

        public ArtistDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ArtistDtoBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ArtistDtoBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ArtistDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ArtistDto build() {
            ArtistDto artistDto = new ArtistDto();
            artistDto.setId(id);
            artistDto.setFirstName(firstName);
            artistDto.setLastName(lastName);
            artistDto.setDescription(description);
            return artistDto;
        }
    }
}
