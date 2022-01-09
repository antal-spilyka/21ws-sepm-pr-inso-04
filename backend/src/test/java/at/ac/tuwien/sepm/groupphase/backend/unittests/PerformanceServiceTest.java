package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventPlaceMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.HallMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.PerformanceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.service.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class PerformanceServiceTest {
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

    @Autowired
    private PerformanceService performanceService;

    @Autowired
    private HallMapper hallMapper;

    private HallDto hallDto;
    private Hall hall, testHall;
    private AddressDto addressDto;
    private EventPlaceDto eventPlaceDto;
    private EventPlace eventPlace;
    private Artist artist;
    private Event event, testEvent;
    private Performance performance, testPerformance;
    private List<Performance> performances = new ArrayList<>();

    @BeforeAll
    public void insertNeededContext() {
        this.addressDto = new AddressDto();
        addressDto.setZip("111");
        addressDto.setState("TestPerformanceByDateTimeState");
        addressDto.setCountry("TestPerformanceByDateTimeCountry");
        addressDto.setCity("TestPerformanceByDateTimeCity");
        addressDto.setStreet("TestPerformanceByDateTimeStreet");

        this.eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("TestPerformanceByDateTimePlace");
        eventPlaceDto.setAddressDto(addressDto);
        eventPlace = eventPlaceService.save(eventPlaceDto);

        ArtistDto artistDto = new ArtistDto();
        artistDto.setBandName("TestPerformanceByDateTimeArtist");
        artistDto.setDescription("PerformanceByDateTimeArtist");
        this.artist = artistService.save(artistDto);

        this.hallDto = new HallDto();
        hallDto.setName("TestPerformanceByDateTimeHall");
        hallDto.setEventPlaceDto(eventPlaceMapper.entityToDto(eventPlace));
        this.hall = hallService.save(hallDto);

        this.event = new Event();
        event.setName("TestPerformanceByDateTimeName");
        event.setStartTime(LocalDateTime.of(2022, 12, 12, 11, 11, 11));
        event.setDuration(710L);
        event.setEventPlace(eventPlace);
        event.setDescription("TestPerformanceByDateTimeDescription");
        event.setCategory("TestCategory");


        this.performance = new Performance();
        performance.setName("TestPerformanceByDateTimePerformance");
        performance.setStartTime(LocalDateTime.of(2022, 12, 12, 11, 11, 11));
        performance.setDuration(50L);
        performance.setEvent(event);
        performance.setArtist(artist);
        performance.setHall(this.hall);
        performance.setEvent(this.event);
        this.performances.add(performance);
        event.setPerformances(this.performances);
        eventService.saveEvent(eventMapper.entityToDto(event));
    }

    @Test
    @Transactional
    public void searchByDateTime_findPerformances() {
        PerformanceSearchDto searchParams = new PerformanceSearchDto();
        searchParams.setStartTime(LocalDateTime.of(2022, 12, 12, 11, 11, 11));
        searchParams.setEventName("TestPerformanceByDate");
        Stream<PerformanceDto> performances = performanceService.findPerformanceByDateTime(searchParams);
        assertFalse(performances.toList().isEmpty());
    }

    @Test
    @Transactional
    public void searchByDateTime_findNoPerformances() {
        PerformanceSearchDto searchParams = new PerformanceSearchDto();
        searchParams.setEventName("" + Math.random());
        Stream<PerformanceDto> performances = performanceService.findPerformanceByDateTime(searchParams);
        assertTrue(performances.toList().isEmpty());
    }

    @Test
    @Transactional
    public void getPerformances_forExistingArtist() {
        AddressDto testAddressDto = new AddressDto();
        testAddressDto.setZip("111");
        testAddressDto.setState("TestPerformanceByArtistState");
        testAddressDto.setCountry("TestPerformanceByArtistCountry");
        testAddressDto.setCity("TestPerformanceByArtistCity");
        testAddressDto.setStreet("TestPerformanceByArtistStreet");

        EventPlaceDto testEventPlaceDto = new EventPlaceDto();
        testEventPlaceDto.setName("TestPerformanceByArtistPlace");
        testEventPlaceDto.setAddressDto(addressDto);
        EventPlace testEventPlace = eventPlaceService.save(testEventPlaceDto);

        ArtistDto testArtistDto = new ArtistDto();
        testArtistDto.setBandName("TestPerformanceByArtistArtist");
        testArtistDto.setDescription("PerformanceByArtistArtist");
        Artist testArtist = artistService.save(testArtistDto);

        HallDto testHallDto = new HallDto();
        testHallDto.setName("TestPerformanceByArtistHall");
        testHallDto.setEventPlaceDto(eventPlaceMapper.entityToDto(testEventPlace));
        Hall testHall = hallService.save(testHallDto);

        Event testEvent = new Event();
        testEvent.setName("TestPerformanceByArtistName");
        testEvent.setStartTime(LocalDateTime.of(2022, 12, 12, 11, 11, 11));
        testEvent.setDuration(100L);
        testEvent.setEventPlace(testEventPlace);
        testEvent.setDescription("TestPerformanceByArtistDescription");
        testEvent.setCategory("TestCategory");


        Performance testPerformance = new Performance();
        testPerformance.setName("TestPerformanceByArtistPerformance");
        testPerformance.setStartTime(LocalDateTime.of(2022, 12, 12, 11, 11, 11));
        testPerformance.setDuration(50L);
        testPerformance.setEvent(testEvent);
        testPerformance.setArtist(testArtist);
        testPerformance.setHall(testHall);
        testPerformance.setEvent(testEvent);
        List<Performance> testPerformances = new ArrayList<Performance>();
        testPerformances.add(testPerformance);
        testEvent.setPerformances(testPerformances);
        eventService.saveEvent(eventMapper.entityToDto(testEvent));

        testPerformance.setId(performanceService.findPerformanceForArtist(testArtist.getId()).toList().get(0).getId());
        Stream<PerformanceDto> performances = performanceService.findPerformanceForArtist(testArtist.getId());
        List<PerformanceDto> listPerformances = performances.toList();

        PerformanceDto performanceDto = performanceMapper.entityToDto(testPerformance, null);
        performanceDto.setStartTime(LocalDateTime.of(2022, 12, 12, 11, 16, 11));
        performanceDto.setPriceMultiplicant(1L);

        assertEquals(listPerformances.get(0), performanceDto);
    }

    @Test
    @Transactional
    public void getPerformances_forNotExistingArtist() {
        Stream<PerformanceDto> performances = performanceService.findPerformanceForArtist(-1L);
        assertTrue(performances.toList().isEmpty());
    }

    @Test
    public void getPerformance_byNotExistingId() {
        PerformanceDetailDto performance = performanceService.findPerformanceById(-1L);
        assertNull(performance);
    }

    @Test
    public void getPerformance_byExistingId() {
        /*Performance testPerformance = new Performance(this.performance.getId(), this.performance.getName(), this.performance.getStartTime(),
            this.performance.getDuration(), this.performance.getEvent(), this.performance.getArtist(), this.performance.getHall());
        PerformanceDto testPerformanceDto = performanceMapper.entityToDto(this.performance, eventMapper.entityToDto(this.performance.getEvent()));
        testPerformanceDto.setEventDto(eventMapper.entityToDto(this.event));
        this.event.getPerformances().add(testPerformanceDto);

        Performance receivedPerformance = performanceService.save(testPerformanceDto);
        assertEquals(testPerformance.getName(), receivedPerformance.getName());
        assertEquals(testPerformance.getDuration(), receivedPerformance.getDuration());
        assertEquals(testPerformance.getArtist(), receivedPerformance.getArtist());*/
        PerformanceDetailDto receivedPerformance = performanceService.findPerformanceById(1L);
        assertNotEquals(receivedPerformance, null);
    }

    @Test
    public void getGeneralSearch_for_invalid_String() {
        GeneralSearchEventDto searchEventDto = new GeneralSearchEventDto();
        searchEventDto.setSearchQuery("" + Math.random());
        searchEventDto.setPage(0);
        Stream<PerformanceDto> foundPerformances = performanceService.findGeneralPerformanceByDateTime(searchEventDto);
        assertTrue(foundPerformances.toList().isEmpty());
    }

    @Test
    @Transactional
    public void getGeneralSearch_for_valid_String() {
        GeneralSearchEventDto searchEventDto = new GeneralSearchEventDto();
        searchEventDto.setSearchQuery("TestPerformanceByDateTimeHall");
        searchEventDto.setPage(0);
        Stream<PerformanceDto> foundPerformances = performanceService.findGeneralPerformanceByDateTime(searchEventDto);
        assertFalse(foundPerformances.toList().isEmpty());
    }


}
