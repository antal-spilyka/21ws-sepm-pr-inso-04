package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallAddDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallplanElementDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SectorDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallplanElement;
import at.ac.tuwien.sepm.groupphase.backend.entity.Sector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.IntStream;

@Component
public class HallMapper {

    EventPlaceMapper eventPlaceMapper;
    private SectorMapper sectorMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public HallMapper(EventPlaceMapper eventPlaceMapper, SectorMapper sectorMapper) {
        this.eventPlaceMapper = eventPlaceMapper;
        this.sectorMapper = sectorMapper;
    }

    public HallDto entityToDto(Hall hall) {
        LOGGER.trace("Mapping {}", hall);
        HallDto hallDto = new HallDto();
        hallDto.setId(hall.getId());
        hallDto.setName(hall.getName());
        hallDto.setEventPlaceDto(eventPlaceMapper.entityToDto(hall.getEventPlace()));
        return hallDto;
    }

    public HallDetailDto entityToDetailDto(Hall hall) {
        LOGGER.trace("Mapping {}", hall);
        HallDetailDto hallDto = new HallDetailDto();
        hallDto.setId(hall.getId());
        hallDto.setName(hall.getName());
        hallDto.setStandingPlaces(hall.getStandingPlaces());
        hallDto.setEventPlaceDto(eventPlaceMapper.entityToDto(hall.getEventPlace()));

        List<HallplanElement> rows = hall.getRows();

        int maxRowIndex = rows.stream().mapToInt(HallplanElement::getRowIndex).max().orElseThrow(NoSuchElementException::new);
        int maxSeatIndex = rows.stream().mapToInt(HallplanElement::getSeatIndex).max().orElseThrow(NoSuchElementException::new);

        HallplanElementDto[][] hallplanElements = new HallplanElementDto[maxRowIndex + 1][maxSeatIndex + 1];
        List<SectorDto> sectors = hall.getSectors().stream().map(sector -> sectorMapper.entityToDto(sector)).toList();
        for (HallplanElement hallplanElement : rows) {
            int indexOpt = IntStream.range(0, sectors.size()).filter(index -> Objects.equals(sectors.get(index).getId(), hallplanElement.getSector().getId())).findFirst().orElse(sectors.size());

            HallplanElementDto dto = new HallplanElementDto();
            dto.setType(hallplanElement.getType());
            dto.setSector(indexOpt);
            dto.setAdded(hallplanElement.isAdded());
            hallplanElements[hallplanElement.getRowIndex()][hallplanElement.getSeatIndex()] = dto;
        }
        hallDto.setRows(hallplanElements);
        hallDto.setSectors(sectors.toArray(SectorDto[]::new));

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

    public Hall dtoToEntity(HallAddDto hallAddDto, EventPlace eventPlace, List<HallplanElement> rows, List<Sector> sectors) {
        LOGGER.trace("Mapping {}", hallAddDto);
        Hall hall = new Hall();
        hall.setName(hallAddDto.getName());
        hall.setStandingPlaces(hallAddDto.getStandingPlaces());
        hall.setRow(rows);
        hall.setEventPlace(eventPlace);
        hall.setSectors(sectors);
        return hall;
    }
}
