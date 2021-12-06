package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CategoryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RoomDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RoomInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventPlaceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.service.ArtistService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventPlaceService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.HallService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.time.LocalDateTime;
import java.util.List;

import java.time.LocalDateTime;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventPlaceMapper eventPlaceMapper;

    @Autowired
    private EventPlaceService eventPlaceService;

    @Autowired
    HallService hallService;
    @Autowired
    private ArtistService artistService;

    private HallDto hallDto;
    private ArtistDto artistDto;
    private EventDto eventDto;

    /*@BeforeAll todo
    public void insertNeededContext() {
        AddressDto addressDto = new AddressDto();
        addressDto.setZip("1234");
        addressDto.setState("TestState");
        addressDto.setCountry("TestCountry");
        addressDto.setCity("TestCity");

        EventPlaceDto eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("TestPlace2");
        eventPlaceDto.setAddressDto(addressDto);
        EventPlace eventPlace = eventPlaceMapper.dtoToEntity(eventPlaceService.save(eventPlaceDto));

        RoomInquiryDto roomInquiryDto = new RoomInquiryDto();
        roomInquiryDto.setName("TestRoom");
        roomInquiryDto.setEventPlaceName(eventPlace.getName());
        hallDto = roomService.save(roomInquiryDto);

        ArtistDto artistDto = new ArtistDto();
        artistDto.setBandName("TestArtist");
        artistDto.setDescription("an artist");
        this.artistDto = artistService.save(artistDto);

        EventInquiryDto eventInquiryDto = new EventInquiryDto();
        eventInquiryDto.setName("testName");
        eventInquiryDto.setContent("testContent1234");
        eventInquiryDto.setDateTime(LocalDateTime.now());
        eventInquiryDto.setDuration(710);
        eventInquiryDto.setCategoryName(this.categoryDto.getName());
        eventInquiryDto.setRoomId(this.hallDto.getId());
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
        eventInquiryDto.setRoomId(hallDto.getId());
        eventInquiryDto.setArtistId(artistDto.getId());
        EventDto eventDtoPers = eventService.createEvent(eventInquiryDto);

        EventDto eventDtoExp = new EventDto();
        eventDtoExp.setName(eventInquiryDto.getName());
        eventDtoExp.setContent(eventInquiryDto.getContent());
        eventDtoExp.setDateTime(eventInquiryDto.getDateTime());
        eventDtoExp.setDuration(eventInquiryDto.getDuration());
        eventDtoExp.setRoom(hallDto);
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
        eventInquiryDto.setRoomId(hallDto.getId());
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
        eventInquiryDto.setRoomId(hallDto.getId());
        eventInquiryDto.setArtistId(-123L);
        assertThrows(DataIntegrityViolationException.class, () -> eventService.createEvent(eventInquiryDto));
    }*/

    @Test
    public void search_for_valid_event() {

        EventSearchDto eventSearchDto = new EventSearchDto();
        eventSearchDto.setDuration(700);
        List<EventDto> events = eventService.findEvents(eventSearchDto);
        assertFalse(events.isEmpty());
    }

    @Test
    public void search_for_invalid_event() {
        EventSearchDto eventSearchDto = new EventSearchDto();
        eventSearchDto.setDescription("desc");
        eventSearchDto.setDuration(120);
        assertThrows(NotFoundException.class, () -> eventService.findEvents(eventSearchDto));
    }
}
