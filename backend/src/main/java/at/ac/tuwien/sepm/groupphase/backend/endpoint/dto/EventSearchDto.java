package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.Objects;

public class EventSearchDto {
    Integer duration;
    String description;
    String category;
    Integer page;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "EventSearchDto{" +
            "duration=" + duration +
            ", description='" + description + '\'' +
            '}';
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
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
        EventSearchDto that = (EventSearchDto) o;
        return Objects.equals(duration, that.duration) && Objects.equals(description, that.description)
               && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration, description, category);
    }
}
