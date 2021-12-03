package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class EventDto {
    Long id;
    String name;
    Integer duration;
    String content;
    LocalDateTime dateTime;
    CategoryDto category;
    RoomDto room;
    ArtistDto artist;
    String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public RoomDto getRoom() {
        return room;
    }

    public void setRoom(RoomDto room) {
        this.room = room;
    }

    public ArtistDto getArtist() {
        return artist;
    }

    public void setArtist(ArtistDto artist) {
        this.artist = artist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventDto eventDto = (EventDto) o;
        return Objects.equals(name, eventDto.name)
            && Objects.equals(duration, eventDto.duration)
            && Objects.equals(content, eventDto.content)
            && Objects.equals(dateTime, eventDto.dateTime)
            && Objects.equals(category, eventDto.category)
            && Objects.equals(room, eventDto.room)
            && Objects.equals(artist, eventDto.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, duration, content, dateTime, category, room, artist);
    }

    @Override
    public String toString() {
        return "EventDto{" +
            "name='" + name + '\'' +
            ", duration=" + duration +
            ", content='" + content + '\'' +
            ", dateTime=" + dateTime +
            ", category=" + category +
            ", room=" + room +
            ", artist=" + artist +
            '}';
    }
}
