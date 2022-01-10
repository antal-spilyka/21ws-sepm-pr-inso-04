package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.SectorRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.*;
import org.aspectj.lang.annotation.Before;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.rowset.serial.SerialException;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
    private SectorRepository sectorRepository;

    @Mock
    private Principal principal;

    @Autowired
    private UserService userService;

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

    private String defaultName = "test@email.com";
    private String defaultName2 = "test2@email.com";

    private HallDto hallDto;
    private Hall hall;
    private AddressDto addressDto;
    private EventPlaceDto eventPlaceDto;
    private EventPlace eventPlace;
    private Artist artist;
    private Event event;
    private Performance performance;
    private List<Performance> performances = new ArrayList<>();

    @BeforeAll
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

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
        performance.setHall(hall);
        performance.setEvent(this.event);
        this.performances.add(performance);
        event.setPerformances(this.performances);
        eventService.saveEvent(eventMapper.entityToDto(event));
    }

    @Test
    public void searchByDateTime_findPerformances() {
        PerformanceSearchDto searchParams = new PerformanceSearchDto();
        searchParams.setStartTime(LocalDateTime.of(2022, 12, 12, 11, 11, 11));
        searchParams.setEventName("PerformanceByDateTime");
        Stream<PerformanceDto> performances = performanceService.findPerformanceByDateTime(searchParams);
        assertFalse(performances.toList().isEmpty());
    }

    @Test
    public void searchByDateTime_findNoPerformances() {
        PerformanceSearchDto searchParams = new PerformanceSearchDto();
        searchParams.setEventName("" + Math.random());
        Stream<PerformanceDto> performances = performanceService.findPerformanceByDateTime(searchParams);
        assertTrue(performances.toList().isEmpty());
    }

    @Test
    public void getPerformances_forExistingArtist_findPerformance() {
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
    public void getPerformancesOfNotExistingArtist_findNoPerformances() {
        Stream<PerformanceDto> performances = performanceService.findPerformanceForArtist(-1L);
        assertTrue(performances.toList().isEmpty());
    }

    @Test
    public void getPerformanceByNonExistingId_findNoPerformance() {
        PerformanceDetailDto performance = performanceService.findPerformanceById(123456789L);
        assertNull(performance);
    }

    @Test
    public void getPerformanceByExistingId_findCorrectPerformance() {
        PerformanceDetailDto performance = performanceService.findPerformanceById(1L);
        assertNotNull(performance);
        assertEquals("TestPerformance", performance.getName());
        assertEquals(performance.getDuration(), 50L);
    }

   @Test
   public void buySeatWithoutPrincipal_shouldThrowNullPointerException() {
        BasketSeatDto basketSeatDto = new BasketSeatDto();
        basketSeatDto.setSeatIndex(1);
        basketSeatDto.setRowIndex(2);

        List<BasketSeatDto> seats = new ArrayList<>();
        seats.add(basketSeatDto);

        BasketDto basketDto = new BasketDto();
        basketDto.setSeats(seats);
        basketDto.setStandingPlaces(10);
        basketDto.setPaymentInformationId(null);
        try {
            performanceService.buySeats(basketDto, 1L, null);
            fail("Should not be the case");
        } catch (NullPointerException e) {
            // Should be the case
        }
   }

    @Test
    public void buySeatForNonExistingPerformance_shouldThrowNullPointerException() {
        // Create user
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setEmail(defaultName);
        userRegisterDto.setPassword("password");
        userRegisterDto.setFirstName("TestFirst");
        userRegisterDto.setLastName("TestLast");
        userRegisterDto.setSalutation("mr");
        userRegisterDto.setPhone("1234567");
        userRegisterDto.setCountry("AT");
        userRegisterDto.setCity("TestCity");
        userRegisterDto.setZip("1234");
        userRegisterDto.setStreet("TestStreet");
        userRegisterDto.setDisabled(true);
        userService.createUser(userRegisterDto);

        // Check if the name is correct
        when(principal.getName()).thenReturn(defaultName);
        assertEquals(userService.findApplicationUserByEmail("test@email.com").getEmail(), this.defaultName);

        BasketSeatDto basketSeatDto = new BasketSeatDto();
        basketSeatDto.setSeatIndex(1);
        basketSeatDto.setRowIndex(2);

        List<BasketSeatDto> seats = new ArrayList<>();
        seats.add(basketSeatDto);

        BasketDto basketDto = new BasketDto();
        basketDto.setSeats(seats);
        basketDto.setStandingPlaces(10);
        basketDto.setPaymentInformationId(null);
        try {
            performanceService.buySeats(basketDto, 12345678L, principal);
            fail("Should not be the case");
        } catch (ServiceException e) {
            // Should be the case
        }
    }

    @Test
    public void buySeatWithCorrectData_should() {
        // Create user
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setEmail(defaultName2);
        userRegisterDto.setPassword("password");
        userRegisterDto.setFirstName("TestFirst");
        userRegisterDto.setLastName("TestLast");
        userRegisterDto.setSalutation("mr");
        userRegisterDto.setPhone("1234567");
        userRegisterDto.setCountry("AT");
        userRegisterDto.setCity("TestCity");
        userRegisterDto.setZip("1234");
        userRegisterDto.setStreet("TestStreet");
        userRegisterDto.setDisabled(true);
        userService.createUser(userRegisterDto);

        // Check if the name is correct
        when(principal.getName()).thenReturn(defaultName2);
        assertEquals(userService.findApplicationUserByEmail("test2@email.com").getEmail(), this.defaultName2);

        BasketSeatDto basketSeatDto = new BasketSeatDto();
        basketSeatDto.setSeatIndex(1);
        basketSeatDto.setRowIndex(2);

        List<BasketSeatDto> seats = new ArrayList<>();
        seats.add(basketSeatDto);

        BasketDto basketDto = new BasketDto();
        basketDto.setSeats(seats);
        basketDto.setStandingPlaces(10);
        basketDto.setPaymentInformationId(null);

        SectorDto sector = new SectorDto();
        sector.setName("standing");
        sector.setColor("1234567");
        sector.setPrice(1.1);
        List<SectorDto> sectors = new ArrayList<>();
        sectors.add(sector);

        HallDto hall = performanceService.findPerformanceById(1L).getHall();
        hall.setStandingPlaces(10);
        hall.setSectors(sectors);

        hallService.save(hall);

        try {
            //performanceService.buySeats(basketDto, 1L, this.principal);
        } catch (NullPointerException | ServiceException e) {
            fail("Should not be the case: " + e.getLocalizedMessage());
        }
    }
}
