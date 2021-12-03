package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RoomDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RoomInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.entity.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class RoomMapper {

    EventPlaceMapper eventPlaceMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public RoomMapper(EventPlaceMapper eventPlaceMapper) {
        this.eventPlaceMapper = eventPlaceMapper;
    }

    public RoomDto entityToDto(Room room) {
        LOGGER.trace("Mapping {}", room);
        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setName(room.getName());
        roomDto.setEventPlaceDto(eventPlaceMapper.entityToDto(room.getEventPlace()));
        return roomDto;
    }

    public Room dtoToEntity(RoomDto roomDto) {
        LOGGER.trace("Mapping {}", roomDto);
        Room room = new Room();
        room.setId(roomDto.getId());
        room.setName(roomDto.getName());
        room.setEventPlace(eventPlaceMapper.dtoToEntity(roomDto.getEventPlaceDto()));
        return room;
    }

    public Room dtoToEntity(RoomInquiryDto roomInquiryDto, EventPlace eventPlace) {
        LOGGER.trace("Mapping {}, {}", roomInquiryDto, eventPlace);
        Room room = new Room();
        room.setName(roomInquiryDto.getName());
        room.setEventPlace(eventPlace);
        return room;
    }
}
