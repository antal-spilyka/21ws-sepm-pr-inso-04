package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class ArtistDto {
    Long id;
    String bandName;
    String description;

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @NotNull()
    @NotBlank
    public String getBandName() {
        return bandName;
    }

    public void setId(Long id) {
        this.id = id;
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
        return Objects.equals(id, artistDto.id) && Objects.equals(description, artistDto.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "ArtistDto{" +
            "id=" + id +
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
            artistDto.setDescription(description);
            return artistDto;
        }
    }
}
