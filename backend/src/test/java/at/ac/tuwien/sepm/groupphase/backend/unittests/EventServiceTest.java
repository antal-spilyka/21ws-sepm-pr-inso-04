package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventPlaceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.ArtistService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventPlaceService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.HallService;
import ch.qos.logback.classic.util.StatusViaSLF4JLoggerFactory;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    private EventMapper eventMapper;

    @Autowired
    private EventPlaceService eventPlaceService;

    @Autowired
    HallService hallService;

    @Autowired
    private ArtistService artistService;

    private HallDto hallDto;
    private Hall hall;
    private AddressDto addressDto;
    private Address address;
    private EventPlaceDto eventPlaceDto;
    private EventPlace eventPlace;
    private ArtistDto artistDto;
    private Artist artist;
    private Event event;
    private List<Performance> performances = new ArrayList<>();

    @BeforeAll
    public void insertNeededContext() {
        this.addressDto = new AddressDto();
        addressDto.setZip("1234");
        addressDto.setState("TestState");
        addressDto.setCountry("TestCountry");
        addressDto.setCity("TestCity");
        addressDto.setStreet("TestStreet");
        this.address = new Address();
        address.setZip("1234");
        address.setState("TestState");
        address.setCountry("TestCountry");
        address.setCity("TestCity");
        address.setStreet("TestStreet");

        this.eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("TestPlace2");
        eventPlaceDto.setAddressDto(addressDto);
        eventPlaceService.save(eventPlaceDto);

        this.eventPlace = new EventPlace();
        eventPlace.setName("TestPlace");
        eventPlace.setAddress(this.address);

        ArtistDto artistDto = new ArtistDto();
        artistDto.setBandName("TestArtist");
        artistDto.setDescription("an artist");
        this.artist = artistService.save(artistDto);

        this.artist = new Artist();
        artist.setBandName("TestArtist");
        artist.setDescription("TestDescription");

        this.hallDto = new HallDto();
        hallDto.setName("TestHall");
        hallDto.setEventPlaceDto(eventPlaceDto);
        this.hall = hallService.save(hallDto);

        this.event = new Event();
        event.setName("TestName");
        event.setStartTime(LocalDateTime.now());
        event.setDuration(710L);

        Performance performance = new Performance();
        performance.setName("TestPerformance");
        performance.setStartTime(LocalDateTime.now());
        performance.setDuration(50L);
        performance.setEvent(event);
        performance.setArtist(artist);
        performance.setHall(hall);
        this.performances.add(performance);

        event.setPerformances(performances);
        event.setEventPlace(eventPlace);
        event.setDescription("TestDescription");

        this.event = eventService.saveEvent(eventMapper.entityToDto(event));
    }

    @Test
    public void insert_valid_event() {
        EventPlaceDto eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("TestPlace2");
        eventPlaceDto.setAddressDto(addressDto);
        EventPlace eventPlace = eventPlaceService.save(eventPlaceDto);


        Event eventPers = eventService.saveEvent(eventMapper.entityToDto(this.event));

        Event event2 = new Event();
        event.setName(event.getName());
        event.setStartTime(event.getStartTime());
        event.setDuration(event.getDuration());
        event.setPerformances(event.getPerformances());
        event.setEventPlace(event.getEventPlace());
        event.setDescription(event.getDescription());

        assertEquals(eventPers.toString(), event2.toString());
    }

    @Test
    public void insert_event_without_name() {
        Event event = new Event();
        event.setStartTime(LocalDateTime.now());
        event.setDuration(120L);
        event.setPerformances(this.performances);
        event.setEventPlace(this.eventPlace);
        event.setDescription("testDescription");
        assertThrows(DataIntegrityViolationException.class, () -> eventService.saveEvent(eventMapper.entityToDto(event)));
    }

    @Test
    public void search_for_valid_event_not_found() {
        EventSearchDto eventSearchDto = new EventSearchDto();
        eventSearchDto.setDuration(1000);
        eventSearchDto.setDescription("not found");
        List<Event> events = eventService.findEvents(eventSearchDto);
        assertTrue(events.isEmpty());
    }

    @Test
    public void search_for_valid_event_found() {
        eventService.saveEvent(eventMapper.entityToDto(this.event));
        EventSearchDto eventSearchDto = new EventSearchDto();
        eventSearchDto.setDuration(this.event.getDuration().intValue());
        eventSearchDto.setDescription(this.event.getDescription());
        List<Event> events = eventService.findEvents(eventSearchDto);
        assertFalse(events.isEmpty());
    }

    @Test
    public void search_for_invalid_event() {
        EventSearchDto eventSearchDto = new EventSearchDto();
        eventSearchDto.setDescription("desc");
        assertThrows(ServiceException.class, () -> eventService.findEvents(eventSearchDto));
    }
}
