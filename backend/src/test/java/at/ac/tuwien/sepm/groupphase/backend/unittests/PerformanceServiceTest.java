package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.BasketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.BasketSeatDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallAddDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallplanElementDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaymentInformationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SectorDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserEditDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserRegisterDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventPlaceMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.PerformanceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.PaymentInformation;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.repository.PaymentInformationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.ArtistService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventPlaceService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.HallService;
import at.ac.tuwien.sepm.groupphase.backend.service.PerformanceService;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

    @Autowired
    private PaymentInformationRepository paymentInformationRepository;

    @Autowired
    private PerformanceRepository performanceRepository;

    private String defaultName = "test@email.com";
    private String defaultName2 = "test2@email.com";
    private String defaultName3 = "test3@email.com";

    private HallDto hallDto;
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
    public void beforeAll() {
        performanceRepository.deleteAll();
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
        hallService.save(hallDto);

        this.event = new Event();
        event.setName("TestPerformanceByDateTimeName");
        event.setStartTime(LocalDateTime.of(2022, 12, 12, 11, 11, 11));
        event.setDuration(710L);
        event.setEventPlace(eventPlace);
        event.setDescription("TestPerformanceByDateTimeDescription");
        event.setCategory("TestCategory");

        SectorDto sector = new SectorDto();
        sector.setName("Standing");
        sector.setColor("1234567");
        sector.setPrice(1.1);
        SectorDto[] sectors = {sector};

        HallplanElementDto hallplanElementDto = new HallplanElementDto();
        hallplanElementDto.setAdded(false);
        hallplanElementDto.setSector(0);
        hallplanElementDto.setType("Standing");

        HallplanElementDto hallplanElementDto2 = new HallplanElementDto();
        hallplanElementDto2.setAdded(false);
        hallplanElementDto2.setSector(0);
        hallplanElementDto2.setType("Standing");

        HallplanElementDto[] hallplanElementDtos = {hallplanElementDto, hallplanElementDto2};
        HallplanElementDto[] [] hallplanElementDtos2 = {hallplanElementDtos};

        HallAddDto hall = new HallAddDto();
        hall.setName("buyTestHall");
        hall.setStandingPlaces(100);
        hall.setSectors(sectors);
        hall.setRows(hallplanElementDtos2);
        eventPlaceService.addHall(String.valueOf(2L), hall);

        this.performance = new Performance();
        performance.setName("TestPerformanceByDateTimePerformance");
        performance.setStartTime(LocalDateTime.of(2022, 12, 12, 11, 11, 11));
        performance.setDuration(50L);
        performance.setEvent(event);
        performance.setArtist(artist);
        performance.setHall(hallService.findHall("buyTestHall").get(0));
        performance.setEvent(this.event);
        this.performances.add(performance);
        event.setPerformances(this.performances);
        eventService.saveEvent(eventMapper.entityToDto(event));

        UserRegisterDto user1 = UserRegisterDto.UserRegisterDtoBuilder.aUserRegisterDto()
            .withEmail(this.defaultName2)
            .withPassword("testPassword")
            .withFirstName("test")
            .withLastName("person")
            .withSalutation("mr")
            .withPhone("+430101011010")
            .withCountry("Austria")
            .withCity("Vienna")
            .withStreet("Test Street")
            .withZip("12345")
            .withDisabled(true)
            .build();
        userService.createUser(user1);

        PaymentInformationDto paymentInformation = new PaymentInformationDto();
        paymentInformation.setCreditCardNr("1234123412341234");
        paymentInformation.setCreditCardExpirationDate("202022");
        paymentInformation.setCreditCardCvv("123");
        paymentInformation.setCreditCardName("Test");

        UserEditDto toUpdate = UserEditDto.UserEditDtoBuilder.aUserDto()
            .withEmail(this.defaultName2)
            .withNewEmail(this.defaultName2)
            .withAdmin(false)
            .withPassword("testPassword")
            .withFirstName("firstName")
            .withLastName("person")
            .withSalutation("mr")
            .withPhone("+430101011010")
            .withCountry("Austria")
            .withCity("Test City")
            .withStreet("Test Street")
            .withDisabled(true)
            .withZip("12345")
            .withPaymentInformation(paymentInformation)
            .build();
        userService.updateUser(toUpdate);
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
        assertEquals("TestPerformanceByDateTimePerformance", performance.getName());
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
        basketDto.setStandingPlaces(1);
        basketDto.setPaymentInformationId(null);
        try {
            performanceService.buySeats(basketDto, 1L, null);
            fail("Should not be the case");
        } catch (NullPointerException e) {
            // Should be the case
        }
   }

    @Test
    public void reserveSeatWithoutPrincipal_shouldThrowNullPointerException() {
        BasketSeatDto basketSeatDto = new BasketSeatDto();
        basketSeatDto.setSeatIndex(1);
        basketSeatDto.setRowIndex(2);

        List<BasketSeatDto> seats = new ArrayList<>();
        seats.add(basketSeatDto);

        BasketDto basketDto = new BasketDto();
        basketDto.setSeats(seats);
        basketDto.setStandingPlaces(1);
        basketDto.setPaymentInformationId(null);
        try {
            performanceService.reserveSeats(basketDto, 1L, null);
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
        basketDto.setStandingPlaces(1);
        basketDto.setPaymentInformationId(null);
        try {
            performanceService.buySeats(basketDto, 12345678L, principal);
            fail("Should not be the case");
        } catch (ServiceException e) {
            // Should be the case
        }
    }

    @Test
    public void reserveSeatForNonExistingPerformance_shouldThrowNullPointerException() {
        // Create user
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setEmail(defaultName3);
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
        when(principal.getName()).thenReturn(defaultName3);
        assertEquals(userService.findApplicationUserByEmail("test3@email.com").getEmail(), this.defaultName3);

        BasketSeatDto basketSeatDto = new BasketSeatDto();
        basketSeatDto.setSeatIndex(1);
        basketSeatDto.setRowIndex(2);

        List<BasketSeatDto> seats = new ArrayList<>();
        seats.add(basketSeatDto);

        BasketDto basketDto = new BasketDto();
        basketDto.setSeats(seats);
        basketDto.setStandingPlaces(1);
        basketDto.setPaymentInformationId(null);
        try {
            performanceService.reserveSeats(basketDto, 12345678L, principal);
            fail("Should not be the case");
        } catch (ServiceException e) {
            // Should be the case
        }
    }

    @Test
    public void buySeatWithCorrectData_successfullyBuysTicket() {
        // Check if the name is correct
        when(principal.getName()).thenReturn(defaultName2);
        assertEquals(userService.findApplicationUserByEmail("test2@email.com").getEmail(), this.defaultName2);

        BasketSeatDto basketSeatDto = new BasketSeatDto();
        basketSeatDto.setSeatIndex(0);
        basketSeatDto.setRowIndex(0);

        List<BasketSeatDto> seats = new ArrayList<>();
        seats.add(basketSeatDto);

        BasketDto basketDto = new BasketDto();
        basketDto.setSeats(seats);
        basketDto.setStandingPlaces(1);

        PaymentInformation paymentInformation = new PaymentInformation();
        paymentInformation.setCreditCardNr("1234123412341234");
        paymentInformation.setCreditCardExpirationDate("202022");
        paymentInformation.setCreditCardCvv("123");
        paymentInformation.setCreditCardName("Test");
        ApplicationUser cardHolder = userService.findApplicationUserByEmail("test2@email.com");

        basketDto.setPaymentInformationId(paymentInformationRepository.findByUser(cardHolder).get(0).getId());
        try {
            performanceService.buySeats(basketDto, 1L, this.principal);
        } catch (NullPointerException | ServiceException e) {
            fail("Should not be the case: " + e.getLocalizedMessage());
        }
    }

    @Test
    public void reserveWithCorrectData_successfullyBuysTicket() {
        // Check if the name is correct
        when(principal.getName()).thenReturn(defaultName2);
        assertEquals(userService.findApplicationUserByEmail("test2@email.com").getEmail(), this.defaultName2);

        BasketSeatDto basketSeatDto = new BasketSeatDto();
        basketSeatDto.setSeatIndex(1);
        basketSeatDto.setRowIndex(0);

        List<BasketSeatDto> seats = new ArrayList<>();
        seats.add(basketSeatDto);

        BasketDto basketDto = new BasketDto();
        basketDto.setSeats(seats);
        basketDto.setStandingPlaces(1);

        PaymentInformation paymentInformation = new PaymentInformation();
        paymentInformation.setCreditCardNr("1234123412341234");
        paymentInformation.setCreditCardExpirationDate("202022");
        paymentInformation.setCreditCardCvv("123");
        paymentInformation.setCreditCardName("Test");
        ApplicationUser cardHolder = userService.findApplicationUserByEmail("test2@email.com");

        basketDto.setPaymentInformationId(paymentInformationRepository.findByUser(cardHolder).get(0).getId());
        try {
            performanceService.reserveSeats(basketDto, 1L, this.principal);
        } catch (NullPointerException | ServiceException e) {
            fail("Should not be the case: " + e.getLocalizedMessage());
        }
    }
}
