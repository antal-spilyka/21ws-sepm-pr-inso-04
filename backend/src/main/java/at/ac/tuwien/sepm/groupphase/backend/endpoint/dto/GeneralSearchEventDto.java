package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.Objects;

public class GeneralSearchEventDto {
    String searchQuery;
    Integer page;

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GeneralSearchEventDto that = (GeneralSearchEventDto) o;
        return Objects.equals(searchQuery, that.searchQuery) && Objects.equals(page, that.page);
    }

    @Override
    public int hashCode() {
        return Objects.hash(searchQuery, page);
    }

}
