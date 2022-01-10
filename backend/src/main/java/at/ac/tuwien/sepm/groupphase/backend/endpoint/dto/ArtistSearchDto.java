package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.Objects;

public class ArtistSearchDto {
    private String misc; // search by first- and lastname and bandName

    public ArtistSearchDto() {}

    public ArtistSearchDto(String misc) {
        this.misc = misc;
    }

    public String getMisc() {
        return misc;
    }

    public void setMisc(String misc) {
        this.misc = misc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArtistSearchDto that = (ArtistSearchDto) o;
        return Objects.equals(misc, that.misc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(misc);
    }

    @Override
    public String toString() {
        return "ArtistSearchDto{" +
            "misc='" + misc + '\'' +
            '}';
    }
}
