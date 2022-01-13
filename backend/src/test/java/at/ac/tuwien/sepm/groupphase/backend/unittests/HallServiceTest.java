package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventPlaceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.service.EventPlaceService;
import at.ac.tuwien.sepm.groupphase.backend.service.HallService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class HallServiceTest {

    @Autowired
    HallService hallService;
    @Autowired
    EventPlaceService eventPlaceService;
    @Autowired
    EventPlaceMapper eventPlaceMapper;

    EventPlace eventPlace;

    @BeforeAll
    public void insertEventPlace() {
        AddressDto addressDto = new AddressDto();
        addressDto.setZip("1234");
        addressDto.setState("TestState");
        addressDto.setCountry("TestCountry");
        addressDto.setCity("TestCity");
        addressDto.setStreet("TestStreet");

        EventPlaceDto eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("TestPlace1");
        eventPlaceDto.setAddressDto(addressDto);
        eventPlace = eventPlaceService.save(eventPlaceDto);
    }

    @Test()
    public void missing_eventPlace() {
        HallDto hallDto = new HallDto();
        hallDto.setName("TestHall");
        assertThrows(NullPointerException.class, () -> hallService.save(hallDto));
    }

    @Test()
    public void missing_hallName() {
        HallDto hallDto = new HallDto();
        hallDto.setEventPlaceDto(eventPlaceMapper.entityToDto(eventPlace));
        assertThrows(DataIntegrityViolationException.class, () -> hallService.save(hallDto));
    }

    @Test
    public void insert_room_validate_and_search() {
        HallDto hallDto = new HallDto();
        hallDto.setName("TestName");
        hallDto.setEventPlaceDto(eventPlaceMapper.entityToDto(eventPlace));
        Hall hallPers = hallService.save(hallDto);

        Hall hallExp = new Hall();
        hallExp.setId(hallPers.getId());
        hallExp.setEventPlace(eventPlace);
        hallExp.setName(hallDto.getName());
        assertEquals(hallPers, hallExp);

        List<Hall> list = hallService.findHall(hallPers.getName());
        assertEquals(list.get(0).getName(), hallExp.getName());
    }

    @Test
    public void search_for_notExistingHall() {
        List<Hall> list = hallService.findHall("not existing");
        assertEquals(list.size(), 0);
    }
}
