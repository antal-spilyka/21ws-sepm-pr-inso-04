package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventPlaceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.service.ArtistService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventPlaceService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.HallService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    private HallService hallService;

    @Autowired
    private ArtistService artistService;

    private HallDto hallDto;
    private Hall hall;
    private AddressDto addressDto;
    private EventPlaceDto eventPlaceDto;
    private EventPlace eventPlace;
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

        this.eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("TestPlace2");
        eventPlaceDto.setAddressDto(addressDto);
        eventPlace = eventPlaceService.save(eventPlaceDto);

        ArtistDto artistDto = new ArtistDto();
        artistDto.setBandName("TestArtist");
        artistDto.setDescription("an artist");
        this.artist = artistService.save(artistDto);

        this.hallDto = new HallDto();
        hallDto.setName("TestHall");
        hallDto.setEventPlaceDto(eventPlaceMapper.entityToDto(eventPlace));
        this.hall = hallService.save(hallDto);

        this.event = new Event();
        event.setName("TestName");
        event.setStartTime(LocalDateTime.now());
        event.setDuration(710L);
        event.setEventPlace(eventPlace);
        event.setDescription("TestDescription");
        event.setCategory("TestCategory");
        eventService.saveEvent(eventMapper.entityToDto(event));

        Performance performance = new Performance();
        performance.setName("TestPerformance");
        performance.setStartTime(LocalDateTime.now());
        performance.setDuration(50L);
        performance.setEvent(event);
        performance.setArtist(artist);
        performance.setHall(hall);
        performance.setEvent(this.event);
        this.performances.add(performance);
    }

    @Test
    public void insert_valid_event() {
        EventPlaceDto eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("TestPlace2");
        eventPlaceDto.setAddressDto(addressDto);
        eventPlaceService.save(eventPlaceDto);

        Event eventPers = eventService.saveEvent(eventMapper.entityToDto(this.event));

        Event event2 = new Event();
        event2.setId(eventPers.getId());
        event2.setName(eventPers.getName());
        event2.setStartTime(eventPers.getStartTime());
        event2.setDuration(eventPers.getDuration());
        event2.setPerformances(eventPers.getPerformances());
        event2.setEventPlace(eventPers.getEventPlace());
        event2.setDescription(eventPers.getDescription());
        event2.setCategory(eventPers.getCategory());

        assertEquals(eventPers, event2);
    }

    @Test
    public void insert_event_without_name() {
        Event event = new Event();
        event.setStartTime(LocalDateTime.now());
        event.setDuration(120L);
        event.setPerformances(this.performances);
        event.setEventPlace(this.eventPlace);
        event.setDescription("testDescription");
        event.setCategory("testCategory");
        assertThrows(DataIntegrityViolationException.class, () -> eventService.saveEvent(eventMapper.entityToDto(event)));
    }

    @Test
    public void search_for_valid_event_not_found() {
        EventSearchDto eventSearchDto = new EventSearchDto();
        eventSearchDto.setDuration(1000);
        eventSearchDto.setDescription("not found");
        eventSearchDto.setCategory("no category");
        List<Event> events = eventService.findEvents(eventSearchDto);
        assertTrue(events.isEmpty());
    }

    @Test
    public void search_for_valid_event_found() {
        Event result = eventService.saveEvent(eventMapper.entityToDto(this.event));
        EventSearchDto eventSearchDto = new EventSearchDto();
        eventSearchDto.setDuration(result.getDuration().intValue());
        eventSearchDto.setDescription(result.getDescription());
        List<Event> events = eventService.findEvents(eventSearchDto);
        assertFalse(events.isEmpty());
    }

    @Test
    public void search_for_invalid_event() {
        EventSearchDto eventSearchDto = new EventSearchDto();
        eventSearchDto.setDescription("desc");
        eventSearchDto.setDuration(9999999);
        List<Event> events = eventService.findEvents(eventSearchDto);
        //assertThrows(ServiceException.class, () -> eventService.findEvents(eventSearchDto));
        assertTrue(events.isEmpty());
    }
}
