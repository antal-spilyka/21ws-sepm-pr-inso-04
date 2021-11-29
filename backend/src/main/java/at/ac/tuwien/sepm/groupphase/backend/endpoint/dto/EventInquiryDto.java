package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Objects;

public class EventInquiryDto {
    String name;
    Integer duration;
    String content;
    LocalDateTime dateTime;
    String categoryName;
    Long roomId;
    Long artistId;

    @NotNull(message = "Event must have a name!")
    @NotBlank
    public String getName() {
        return name;
    }

    @NotNull(message = "Event must have a duration!")
    @Positive
    public Integer getDuration() {
        return duration;
    }

    public String getContent() {
        return content;
    }

    @NotNull(message = "Event must have a date!")
    @Future
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @NotNull(message = "Event must have a category!")
    public String getCategoryName() {
        return categoryName;
    }

    @NotNull(message = "Event must have a room!")
    public Long getRoomId() {
        return roomId;
    }

    @NotNull(message = "Event must have an artist!")
    public Long getArtistId() {
        return artistId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setCategoryName(String categoryId) {
        this.categoryName = categoryId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventInquiryDto that = (EventInquiryDto) o;
        return Objects.equals(name, that.name)
                && Objects.equals(duration, that.duration)
                && Objects.equals(content, that.content)
                && Objects.equals(dateTime, that.dateTime)
                && Objects.equals(categoryName, that.categoryName)
                && Objects.equals(roomId, that.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, duration, content, dateTime, categoryName, roomId);
    }

    @Override
    public String toString() {
        return "EventInquiryDto{" +
            "name='" + name + '\'' +
            ", duration=" + duration +
            ", content='" + content + '\'' +
            ", dateTime=" + dateTime +
            ", categoryName=" + categoryName +
            ", roomId=" + roomId +
            '}';
    }
}
