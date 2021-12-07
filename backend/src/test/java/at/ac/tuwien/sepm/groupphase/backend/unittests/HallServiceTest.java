package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventPlaceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.service.EventPlaceService;
import at.ac.tuwien.sepm.groupphase.backend.service.HallService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

        EventPlaceDto eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("TestPlace1");
        eventPlaceDto.setAddressDto(addressDto);
        eventPlace = eventPlaceService.save(eventPlaceDto);
    }

    /*@Test() todo
    public void missing_eventPlace() {
        RoomInquiryDto roomInquiryDto = new RoomInquiryDto();
        roomInquiryDto.setName("TestRoom");
        assertThrows(InvalidDataAccessApiUsageException.class, () -> hallService.save(roomInquiryDto));
    }

    @Test
    public void insert_room_validate_and_search() {
        RoomInquiryDto roomInquiryDto = new RoomInquiryDto();
        roomInquiryDto.setName("TestRoom");
        roomInquiryDto.setEventPlaceName(eventPlace.getName());
        HallDto hallDtoPers = hallService.save(roomInquiryDto);

        HallDto hallDtoExp = new HallDto();
        hallDtoExp.setEventPlaceDto(eventPlaceMapper.entityToDto(eventPlace));
        hallDtoExp.setName(roomInquiryDto.getName());
        assertEquals(hallDtoExp.getName(), hallDtoPers.getName());

        HallSearchDto hallSearchDto = new HallSearchDto();
        hallSearchDto.setName(roomInquiryDto.getName());
        hallSearchDto.setEventPlaceName(eventPlace.getName());
        List<HallDto> hallDto = hallService.findRoom(hallSearchDto);
        assertEquals(hallDto.get(0).getName(), hallDtoExp.getName());
    }

    @Test
    public void search_for_notExisting() {
        HallSearchDto hallSearchDto = new HallSearchDto();
        hallSearchDto.setName("nothing");
        hallSearchDto.setEventPlaceName(eventPlace.getName());
        List<HallDto> hallDto = hallService.findRoom(hallSearchDto);
        assertEquals(hallDto.size(), 0);
    }*/
}
