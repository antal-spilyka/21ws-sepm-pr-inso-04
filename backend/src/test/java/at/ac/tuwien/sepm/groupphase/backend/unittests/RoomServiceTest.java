package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RoomDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RoomInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RoomSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventPlaceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.service.EventPlaceService;
import at.ac.tuwien.sepm.groupphase.backend.service.RoomService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class RoomServiceTest {

    @Autowired
    RoomService roomService;
    @Autowired
    EventPlaceService eventPlaceService;
    @Autowired
    EventPlaceMapper eventPlaceMapper;

    EventPlace eventPlace;

    @BeforeAll
    public void insertEventPlace() {
        AddressDto addressDto = new AddressDto();
        addressDto.setZip(1234);
        addressDto.setState("TestState");
        addressDto.setCountry("TestCountry");
        addressDto.setCity("TestCity");

        EventPlaceDto eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("TestPlace");
        eventPlaceDto.setAddressDto(addressDto);
        eventPlace = eventPlaceMapper.dtoToEntity(eventPlaceService.save(eventPlaceDto));
    }

    @Test()
    public void missing_eventPlace() {
        RoomInquiryDto roomInquiryDto = new RoomInquiryDto();
        roomInquiryDto.setName("TestRoom");
        assertThrows(InvalidDataAccessApiUsageException.class, () -> roomService.save(roomInquiryDto));
    }

    @Test
    public void insert_room_validate_and_search() {
        RoomInquiryDto roomInquiryDto = new RoomInquiryDto();
        roomInquiryDto.setName("TestRoom");
        roomInquiryDto.setEventPlaceName(eventPlace.getName());
        RoomDto roomDtoPers = roomService.save(roomInquiryDto);

        RoomDto roomDtoExp = new RoomDto();
        roomDtoExp.setEventPlaceDto(eventPlaceMapper.entityToDto(eventPlace));
        roomDtoExp.setName(roomInquiryDto.getName());
        assertEquals(roomDtoExp, roomDtoPers);

        RoomSearchDto roomSearchDto = new RoomSearchDto();
        roomSearchDto.setName(roomInquiryDto.getName());
        roomSearchDto.setEventPlaceName(eventPlace.getName());
        List<RoomDto> roomDto = roomService.findRoom(roomSearchDto);
        assertEquals(roomDto.get(0), roomDtoExp);
    }

    @Test
    public void search_for_notExisting() {
        RoomSearchDto roomSearchDto = new RoomSearchDto();
        roomSearchDto.setName("nothing");
        roomSearchDto.setEventPlaceName(eventPlace.getName());
        List<RoomDto> roomDto = roomService.findRoom(roomSearchDto);
        assertEquals(roomDto.size(), 0);
    }
}
