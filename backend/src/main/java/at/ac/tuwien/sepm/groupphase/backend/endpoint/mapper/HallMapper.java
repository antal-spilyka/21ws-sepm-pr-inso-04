package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallAddDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallplanElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public Hall dtoToEntity(HallAddDto hallAddDto, EventPlace eventPlace, List<HallplanElement> rows) {
        LOGGER.trace("Mapping {}", hallAddDto);
        Hall hall = new Hall();
        hall.setName(hallAddDto.getName());
        hall.setRow(rows);
        hall.setEventPlace(eventPlace);
        return hall;
    }
}
