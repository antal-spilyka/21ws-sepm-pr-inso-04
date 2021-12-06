package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class HallMapper {

    EventPlaceMapper eventPlaceMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public HallMapper(EventPlaceMapper eventPlaceMapper) {
        this.eventPlaceMapper = eventPlaceMapper;
    }

    public HallDto entityToDto(Hall hall) {
        LOGGER.trace("Mapping {}", hall);
        HallDto hallDto = new HallDto();
        hallDto.setId(hall.getId());
        hallDto.setName(hall.getName());
        hallDto.setEventPlaceDto(eventPlaceMapper.entityToDto(hall.getEventPlace()));
        return hallDto;
    }

    public Hall dtoToEntity(HallDto hallDto) {
        LOGGER.trace("Mapping {}", hallDto);
        Hall hall = new Hall();
        hall.setId(hallDto.getId());
        hall.setName(hallDto.getName());
        hall.setEventPlace(eventPlaceMapper.dtoToEntity(hallDto.getEventPlaceDto()));
        return hall;
    }
}
