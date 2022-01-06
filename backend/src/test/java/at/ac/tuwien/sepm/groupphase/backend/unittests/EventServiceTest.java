package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventPlaceMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.PerformanceMapper;
import org.junit.jupiter.api.BeforeAll;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Stream;

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

    @Autowired
    private PerformanceMapper performanceMapper;

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
        event.setName("TestNameX");
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

        Event event2 = new Event();
        event2.setId(this.event.getId());
        event2.setName(this.event.getName());
        event2.setStartTime(this.event.getStartTime());
        event2.setDuration(this.event.getDuration());
        event2.setPerformances(this.event.getPerformances());
        event2.setEventPlace(this.event.getEventPlace());
        event2.setDescription(this.event.getDescription());
        event2.setCategory(this.event.getCategory());

        assertEquals(this.event, event2);
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
        System.out.println(event);
        event.setName("eventX");
        event = eventService.saveEvent(eventMapper.entityToDto(event));
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
        eventSearchDto.setDuration(9999999);
        List<Event> events = eventService.findEvents(eventSearchDto);
        //assertThrows(ServiceException.class, () -> eventService.findEvents(eventSearchDto));
        assertTrue(events.isEmpty());
    }

    @Test
    public void getPerformances_for_event_withPerformances(){
        Event testEvent = new Event();
        testEvent.setName("TestPerformances");
        testEvent.setStartTime(LocalDateTime.now());
        testEvent.setDuration(710L);
        testEvent.setEventPlace(eventPlace);
        testEvent.setDescription("TestPerformancesDesc");
        testEvent.setCategory("TestPerformancesCategory");


        Performance performance = new Performance();
        performance.setName("TestPerformance");
        performance.setStartTime(LocalDateTime.now());
        performance.setDuration(50L);
        performance.setEvent(testEvent);
        performance.setArtist(artist);
        performance.setHall(hall);
        List<Performance> testPerformances = new ArrayList<>();
        testPerformances.add(performance);
        testEvent.setPerformances(testPerformances);
        testEvent.setName("event9");
        Event copyEvent = eventService.saveEvent(eventMapper.entityToDto(testEvent));

        Stream<PerformanceDto> performances = eventService.getPerformances(copyEvent.getId());
        List<Performance> perfList = performances.map(performanceDto -> performanceMapper.dtoToEntity(performanceDto, null)).toList();
        assertFalse(perfList.isEmpty());
    }

    @Test
    public void getPerformances_for_event_withoutPerformances(){
        Event testEvent = new Event();
        testEvent.setName("TestPerformances");
        testEvent.setStartTime(LocalDateTime.now());
        testEvent.setDuration(690L);
        testEvent.setEventPlace(eventPlace);
        testEvent.setDescription("TestPerformancesDesc");
        testEvent.setCategory("TestPerformancesCategory");
        Event copyEvent = eventService.saveEvent(eventMapper.entityToDto(testEvent));
        Stream<PerformanceDto> performances = eventService.getPerformances(copyEvent.getId());
        List<Performance> perfList = performances.map(performanceDto -> performanceMapper.dtoToEntity(performanceDto, null)).toList();
        assertTrue(perfList.isEmpty());
    }

    @Test
    public void getPerformances_for_Location_withPerformances(){
        Event testEvent = new Event();
        testEvent.setName("TestPerformances");
        testEvent.setStartTime(LocalDateTime.now());
        testEvent.setDuration(710L);
        testEvent.setEventPlace(eventPlace);
        testEvent.setDescription("TestPerformancesDesc");
        testEvent.setCategory("TestPerformancesCategory");


        Performance performance = new Performance();
        performance.setName("TestPerformance");
        performance.setStartTime(LocalDateTime.now());
        performance.setDuration(50L);
        performance.setEvent(testEvent);
        performance.setArtist(artist);
        performance.setHall(hall);
        List<Performance> testPerformances = new ArrayList<>();
        testPerformances.add(performance);
        testEvent.setPerformances(testPerformances);
        testEvent.setName("event10");
        Event copyEvent = eventService.saveEvent(eventMapper.entityToDto(testEvent));

        Stream<PerformanceDto> performances = eventService.getPerformancesByLocation(eventPlace.getAddress().getId());
        List<Performance> perfList = performances.map(performanceDto -> performanceMapper.dtoToEntity(performanceDto, null)).toList();
        assertFalse(perfList.isEmpty());
    }

    @Test
    public void getPerformances_for_Location_withoutPerformances(){

        AddressDto testAddressDto = new AddressDto();
        testAddressDto.setStreet("testLocationStreet");
        testAddressDto.setCity("testLocationCity");
        testAddressDto.setCountry("testLocationCountry");
        testAddressDto.setState("testLocationState");
        testAddressDto.setZip("0000");

        EventPlaceDto testEventPlaceDto = new EventPlaceDto();
        testEventPlaceDto.setAddressDto(addressDto);
        testEventPlaceDto.setName("testLocationEventPlace");
        EventPlace testEventPlace = eventPlaceService.save(testEventPlaceDto);
        Event testEvent = new Event();
        testEvent.setName("TestPerformances8");
        testEvent.setStartTime(LocalDateTime.now());
        testEvent.setDuration(710L);
        testEvent.setEventPlace(testEventPlace);
        testEvent.setDescription("TestPerformancesDesc");
        testEvent.setCategory("TestPerformancesCategory");
        Event copyEvent = eventService.saveEvent(eventMapper.entityToDto(testEvent));
        Stream<PerformanceDto> performances = eventService.getPerformancesByLocation(testEventPlace.getAddress().getId());
        List<Performance> perfList = performances.map(performanceDto -> performanceMapper.dtoToEntity(performanceDto, null)).toList();
        assertTrue(perfList.isEmpty());
    }

    @Test
    public void getGeneralSearch_for_invalid_String(){
        List<Event> foundEvents = eventService.findGeneralEvents("" + Math.random());
        assertTrue(foundEvents.isEmpty());
    }

    @Test
    public void getGeneralSearch_for_valid_String(){
        List<Event> foundEvents = eventService.findGeneralEvents("TestCategory");
        assertFalse(foundEvents.isEmpty());
    }
}
