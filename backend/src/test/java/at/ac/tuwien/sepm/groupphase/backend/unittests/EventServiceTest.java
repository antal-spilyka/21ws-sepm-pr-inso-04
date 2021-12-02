package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.ArtistService;
import at.ac.tuwien.sepm.groupphase.backend.service.CategoryService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventPlaceService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.RoomService;
import org.springframework.dao.DataIntegrityViolationException;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventPlaceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EventServiceTest {

    @Autowired
    EventService eventService;
    @Autowired
    EventPlaceMapper eventPlaceMapper;
    @Autowired
    EventPlaceService eventPlaceService;
    @Autowired
    RoomService roomService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ArtistService artistService;

    RoomDto roomDto;
    CategoryDto categoryDto;
    ArtistDto artistDto;
    EventDto eventDto;

    @BeforeAll
    public void insertNeededContext() {
        AddressDto addressDto = new AddressDto();
        addressDto.setZip("1234");
        addressDto.setState("TestState");
        addressDto.setCountry("TestCountry");
        addressDto.setCity("TestCity");

        EventPlaceDto eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("TestPlace");
        eventPlaceDto.setAddressDto(addressDto);
        EventPlace eventPlace = eventPlaceMapper.dtoToEntity(eventPlaceService.save(eventPlaceDto));

        RoomInquiryDto roomInquiryDto = new RoomInquiryDto();
        roomInquiryDto.setName("TestRoom");
        roomInquiryDto.setEventPlaceName(eventPlace.getName());
        roomDto = roomService.save(roomInquiryDto);

        ArtistDto artistDto = new ArtistDto();
        artistDto.setBandName("TestArtist");
        artistDto.setDescription("an artist");
        this.artistDto = artistService.save(artistDto);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("testCategory");
        this.categoryDto = categoryService.save(categoryDto);

        EventInquiryDto eventInquiryDto = new EventInquiryDto();
        eventInquiryDto.setName("testName");
        eventInquiryDto.setContent("testContent1234");
        eventInquiryDto.setDateTime(LocalDateTime.now());
        eventInquiryDto.setDuration(710);
        eventInquiryDto.setCategoryName(this.categoryDto.getName());
        eventInquiryDto.setRoomId(this.roomDto.getId());
        eventInquiryDto.setArtistId(this.artistDto.getId());
        this.eventDto = eventService.createEvent(eventInquiryDto);
    }

    @Test
    public void insert_valid_event() {
        EventInquiryDto eventInquiryDto = new EventInquiryDto();
        eventInquiryDto.setName("testEvent");
        eventInquiryDto.setContent("testContent");
        eventInquiryDto.setDateTime(LocalDateTime.now());
        eventInquiryDto.setDuration(120);
        eventInquiryDto.setCategoryName(categoryDto.getName());
        eventInquiryDto.setRoomId(roomDto.getId());
        eventInquiryDto.setArtistId(artistDto.getId());
        EventDto eventDtoPers = eventService.createEvent(eventInquiryDto);

        EventDto eventDtoExp = new EventDto();
        eventDtoExp.setName(eventInquiryDto.getName());
        eventDtoExp.setContent(eventInquiryDto.getContent());
        eventDtoExp.setDateTime(eventInquiryDto.getDateTime());
        eventDtoExp.setDuration(eventInquiryDto.getDuration());
        eventDtoExp.setCategory(categoryDto);
        eventDtoExp.setRoom(roomDto);
        eventDtoExp.setArtist(artistDto);

        assertEquals(eventDtoPers, eventDtoExp);
    }

    @Test
    public void insert_event_invalid_room() {
        EventInquiryDto eventInquiryDto = new EventInquiryDto();
        eventInquiryDto.setName("testEvent");
        eventInquiryDto.setContent("testContent");
        eventInquiryDto.setDateTime(LocalDateTime.now());
        eventInquiryDto.setDuration(120);
        eventInquiryDto.setCategoryName(categoryDto.getName());
        eventInquiryDto.setRoomId(-123L);
        eventInquiryDto.setArtistId(artistDto.getId());
        assertThrows(DataIntegrityViolationException.class, () -> eventService.createEvent(eventInquiryDto));
    }

    @Test
    public void insert_event_invalid_category() {
        EventInquiryDto eventInquiryDto = new EventInquiryDto();
        eventInquiryDto.setName("testEvent");
        eventInquiryDto.setContent("testContent");
        eventInquiryDto.setDateTime(LocalDateTime.now());
        eventInquiryDto.setDuration(120);
        eventInquiryDto.setCategoryName("not existing");
        eventInquiryDto.setRoomId(roomDto.getId());
        eventInquiryDto.setArtistId(artistDto.getId());
        assertThrows(ContextException.class, () -> eventService.createEvent(eventInquiryDto));
    }

    @Test
    public void insert_event_invalid_artist() {
        EventInquiryDto eventInquiryDto = new EventInquiryDto();
        eventInquiryDto.setName("testEvent");
        eventInquiryDto.setContent("testContent");
        eventInquiryDto.setDateTime(LocalDateTime.now());
        eventInquiryDto.setDuration(120);
        eventInquiryDto.setCategoryName(categoryDto.getName());
        eventInquiryDto.setRoomId(roomDto.getId());
        eventInquiryDto.setArtistId(-123L);
        assertThrows(DataIntegrityViolationException.class, () -> eventService.createEvent(eventInquiryDto));
    }

    @Test
    public void search_for_valid_event(){

        EventSearchDto eventSearchDto = new EventSearchDto();
        eventSearchDto.setContent("testContent123");
        eventSearchDto.setDuration(700);
        List<EventDto> events = eventService.findEvents(eventSearchDto);
        assertFalse(events.isEmpty());
    }

    @Test
    public void search_for_invalid_event(){
        EventSearchDto eventSearchDto = new EventSearchDto();
        eventSearchDto.setDescription("desc");
        eventSearchDto.setContent("dataThatWasSurelyNotInserted");
        eventSearchDto.setDuration(120);
        assertThrows(NotFoundException.class, () -> eventService.findEvents(eventSearchDto));
    }
}
