package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class EventDateTimeSearchDto {
    private LocalDateTime dateTime;
    private String event;
    private String room;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventDateTimeSearchDto that = (EventDateTimeSearchDto) o;
        return Objects.equals(dateTime, that.dateTime) && Objects.equals(event, that.event) && Objects.equals(room, that.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, event, room);
    }

    @Override
    public String toString() {
        return "EventDateTimeSearchDto{" +
            "dateTime=" + dateTime +
            ", event='" + event + '\'' +
            ", room='" + room + '\'' +
            '}';
    }
}
