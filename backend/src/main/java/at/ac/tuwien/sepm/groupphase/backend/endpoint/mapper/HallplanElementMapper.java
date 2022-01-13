package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallAddDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallplanElementDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallplanElement;
import at.ac.tuwien.sepm.groupphase.backend.entity.Sector;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Component
public class HallplanElementMapper {

    EventPlaceMapper eventPlaceMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public HallplanElementMapper(EventPlaceMapper eventPlaceMapper) {
        this.eventPlaceMapper = eventPlaceMapper;
    }

    public Hall dtoToEntity(HallAddDto hallAddDto, EventPlace eventPlace, List<HallplanElement> rows) {
        LOGGER.trace("Mapping {}", hallAddDto);
        Hall hall = new Hall();
        hall.setName(hallAddDto.getName());
        hall.setRow(rows);
        hall.setEventPlace(eventPlace);
        return hall;
    }

    public List<HallplanElement> dtoToEntity(HallplanElementDto[][] rowsDto, List<Sector> sectors) {
        List<HallplanElement> rows = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < rowsDto.length; rowIndex++) {
            for (int seatIndex = 0; seatIndex < rowsDto[rowIndex].length; seatIndex++) {
                HallplanElement hallplanElement = new HallplanElement();
                hallplanElement.setRowIndex(rowIndex);
                hallplanElement.setSeatIndex(seatIndex);
                hallplanElement.setSector(sectors.get(rowsDto[rowIndex][seatIndex].getSector()));
                hallplanElement.setType(rowsDto[rowIndex][seatIndex].getType());
                hallplanElement.setAdded(rowsDto[rowIndex][seatIndex].isAdded());
                rows.add(hallplanElement);
            }
        }
        return rows;
    }
}
