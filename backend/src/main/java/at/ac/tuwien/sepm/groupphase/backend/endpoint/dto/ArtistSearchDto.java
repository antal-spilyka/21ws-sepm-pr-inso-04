package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.Objects;

public class ArtistSearchDto {
    String misc; // search by first- and lastname and bandName
    Integer page = 0;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
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
        return Objects.equals(misc, that.misc) && Objects.equals(page, that.page);
    }

    @Override
    public int hashCode() {
        return Objects.hash(misc, page);
    }

    @Override
    public String toString() {
        return "ArtistSearchDto{" +
            "misc='" + misc + '\'' +
            ", page=" + page +
            '}';
    }
}
