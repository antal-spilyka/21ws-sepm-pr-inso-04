package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallAddDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
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
import java.util.List;

@Component
public class SectorMapper {

    EventPlaceMapper eventPlaceMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public SectorMapper(EventPlaceMapper eventPlaceMapper) {
        this.eventPlaceMapper = eventPlaceMapper;
    }

    public List<Sector> dtoToEntity(SectorDto[] sectors) {
        LOGGER.trace("Mapping {}", sectors);
        return Arrays.stream(sectors).map((sectorDto -> {
            Sector sector = new Sector();
            sector.setName(sectorDto.getName());
            sector.setColor(sectorDto.getColor());
            sector.setPrice(sectorDto.getPrice());
            return sector;
        })).toList();
    }
}
