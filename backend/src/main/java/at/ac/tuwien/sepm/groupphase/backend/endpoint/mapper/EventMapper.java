package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class EventMapper {

    private RoomMapper roomMapper;
    private CategoryMapper categoryMapper;
    private ArtistMapper artistMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public EventMapper(RoomMapper roomMapper, CategoryMapper categoryMapper, ArtistMapper artistMapper) {
        this.roomMapper = roomMapper;
        this.categoryMapper = categoryMapper;
        this.artistMapper = artistMapper;
    }

    public Event inquiryDtoToEntity(EventInquiryDto eventInquiryDto, Room room, Category category, Artist artist) {
        LOGGER.trace("Mapping {}, {}, {}, {}", eventInquiryDto, room, category, artist);
        Event event = new Event();
        event.setName(eventInquiryDto.getName());
        event.setDuration(eventInquiryDto.getDuration());
        event.setContent(event.getContent());
        event.setDateTime(eventInquiryDto.getDateTime());
        event.setCategory(category);
        event.setRoom(room);
        event.setArtist(artist);
        return event;
    }

    public EventDto entityToDto(Event event) {
        LOGGER.trace("Mapping {}", event);
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setName(event.getName());
        eventDto.setDuration(event.getDuration());
        eventDto.setContent(event.getContent());
        eventDto.setDateTime(event.getDateTime());
        eventDto.setCategory(categoryMapper.entityToDto(event.getCategory()));
        eventDto.setRoom(roomMapper.entityToDto(event.getRoom()));
        eventDto.setArtist(artistMapper.entityToDto(event.getArtist()));
        return eventDto;
    }

    public Event dtoToEntity(EventDto eventDto) {
        LOGGER.trace("Mapping {}", eventDto);
        Event event = new Event();
        event.setId(eventDto.getId());
        event.setName(eventDto.getName());
        event.setDuration(eventDto.getDuration());
        event.setContent(eventDto.getContent());
        event.setDateTime(eventDto.getDateTime());
        event.setCategory(categoryMapper.dtoToEntity(eventDto.getCategory()));
        event.setRoom(roomMapper.dtoToEntity(eventDto.getRoom()));
        event.setArtist(artistMapper.dtoToEntity(eventDto.getArtist()));
        return event;
    }
}
