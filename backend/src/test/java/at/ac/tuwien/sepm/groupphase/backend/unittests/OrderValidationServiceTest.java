package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OrderValidationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OrderValidationInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SetOrderToBoughtDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallplanElement;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.entity.OrderValidation;
import at.ac.tuwien.sepm.groupphase.backend.entity.PaymentInformation;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Sector;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallplanElementRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.OrderRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.OrderValidationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PaymentInformationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.SectorRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.ArtistService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventPlaceService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.OrderService;
import at.ac.tuwien.sepm.groupphase.backend.service.OrderValidationService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class OrderValidationServiceTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderValidationRepository orderValidationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArtistService artistService;
    @Autowired
    private EventPlaceService eventPlaceService;
    @Autowired
    private EventService eventService;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    PerformanceRepository performanceRepository;
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    HallplanElementRepository hallplanElementRepository;
    @Autowired
    SectorRepository sectorRepository;
    @Autowired
    PaymentInformationRepository paymentInformationRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderValidationService orderValidationService;
    @Autowired
    PasswordEncoder passwordEncoder;

    private Hall hallPers;

    private Order orderPers;

    @AfterAll
    public void afterAll() {
        hallPers.setRow(null);
        hallPers.setRows(null);
        hallPers.setSectors(null);
        hallRepository.save(hallPers);
        orderValidationRepository.deleteAll();
    }

    @BeforeAll
    public void beforeAll() {
        orderValidationRepository.deleteAll();
        orderRepository.deleteAll();
        ApplicationUser user = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
            .withEmail("random1@test.com")
            .withPassword("$2a$10$j7wfZax80mVaqnqJX.m5vO9wmBtjJbFsm73cKvFE96H2VaM6QxROm")
            .withAdmin(true)
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
        user = userRepository.save(user);

        PaymentInformation paymentInformation = new PaymentInformation();
        paymentInformation.setCreditCardCvv("424");
        paymentInformation.setCreditCardExpirationDate("12/2025");
        paymentInformation.setCreditCardName("Some USer");
        paymentInformation.setUser(user);
        paymentInformation.setCreditCardNr("424242424242424242");
        paymentInformation = paymentInformationRepository.save(paymentInformation);

        AddressDto addressDto = new AddressDto();
        addressDto.setZip("1234");
        addressDto.setState("TestState");
        addressDto.setCountry("TestCountry");
        addressDto.setCity("TestCity");
        addressDto.setStreet("TestStreet");

        EventPlaceDto eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("TestPlace123");
        eventPlaceDto.setAddressDto(addressDto);
        EventPlace eventPlace = eventPlaceService.save(eventPlaceDto);

        ArtistDto artistDto = new ArtistDto();
        artistDto.setBandName("someGuy");
        artistDto.setDescription("an artist");
        Artist artist = artistService.save(artistDto);

        Sector sector = new Sector();
        sector.setId(123L);
        sector.setName("Sector " + 123);
        sector.setColor("#ffffff");
        sector.setPrice(23);
        sector = sectorRepository.save(sector);

        HallplanElement hallplanElement = new HallplanElement();
        hallplanElement.setId(123L);
        hallplanElement.setRowIndex(0);
        hallplanElement.setSeatIndex(0);
        hallplanElement.setSector(sector);
        hallplanElement.setAdded(false);
        hallplanElement.setType("Hall plan element for sector " + sector.getName());
        hallplanElement = hallplanElementRepository.save(hallplanElement);

        Hall hall = new Hall();
        hall.setId(123L);
        hall.setName("testHall123");
        hall.setStandingPlaces(0);
        hall.setEventPlace(eventPlace);
        hall.setSectors(List.of(sector));
        List<HallplanElement> hallplanElements = new ArrayList<>();
        hallplanElements.add(hallplanElement);
        hall.setRows(hallplanElements);
        hall = hallRepository.save(hall);
        this.hallPers = hall;

        Event event = new Event();
        event.setName("TestNameX");
        event.setStartTime(LocalDateTime.now());
        event.setDuration(710L);
        event.setEventPlace(eventPlace);
        event.setDescription("TestDescription");
        event.setCategory("TestCategory");
        EventDto eventDto = eventMapper.entityToDto(event);
        event = eventService.saveEvent(eventDto);

        Performance performance = new Performance();
        performance.setName("TestPerformance");
        performance.setStartTime(LocalDateTime.now());
        performance.setDuration(50L);
        performance.setEvent(event);
        performance.setArtist(artist);
        performance.setHall(hall);
        performance.setPriceMultiplicant(1D);
        performance = performanceRepository.save(performance);

        Order order = new Order();
        order.setRefunded(false);
        order.setDateOfOrder(LocalDateTime.now());
        order.setBought(false);
        order.setUser(user);
        order.setPerformance(performance);
        order.setPrize(4321);
        order.setId(123L);
        order.setPaymentInformation(paymentInformation);
        order = orderRepository.save(order);

        SetOrderToBoughtDto setOrderToBoughtDto = new SetOrderToBoughtDto();
        setOrderToBoughtDto.setOrderId(order.getId());
        setOrderToBoughtDto.setPaymentInformationId(paymentInformation.getId());
        orderService.setOrderToBought(setOrderToBoughtDto);

        this.orderPers = order;
    }

    @Test
    public void positiveCreateHashAndValidate() {
        OrderValidation orderValidation = orderValidationRepository.findByOrder(orderPers);
        OrderValidationInquiryDto orderValidationInquiryDto = new OrderValidationInquiryDto();
        orderValidationInquiryDto.setHash(passwordEncoder.encode(orderValidation.getHash()));
        orderValidationInquiryDto.setId(orderPers.getId());
        OrderValidationDto orderValidationDto = orderValidationService.validate(orderValidationInquiryDto);
        assertTrue(orderValidationDto.isValid());
    }

    @Test
    public void negativeCreateHashValidateWrongHash() {
        OrderValidationInquiryDto orderValidationInquiryDto = new OrderValidationInquiryDto();
        orderValidationInquiryDto.setHash("wrongHash");
        orderValidationInquiryDto.setId(orderPers.getId());
        OrderValidationDto orderValidationDto = orderValidationService.validate(orderValidationInquiryDto);
        assertFalse(orderValidationDto.isValid());
    }

    @Test
    public void negativeValidateRefundedOrder() {
        orderPers.setRefunded(true);
        orderPers = orderRepository.save(orderPers);
        OrderValidation orderValidation = orderValidationRepository.findByOrder(orderPers);
        OrderValidationInquiryDto orderValidationInquiryDto = new OrderValidationInquiryDto();
        orderValidationInquiryDto.setHash(passwordEncoder.encode(orderValidation.getHash()));
        orderValidationInquiryDto.setId(orderPers.getId());
        OrderValidationDto orderValidationDto = orderValidationService.validate(orderValidationInquiryDto);
        assertFalse(orderValidationDto.isValid());
    }

}
