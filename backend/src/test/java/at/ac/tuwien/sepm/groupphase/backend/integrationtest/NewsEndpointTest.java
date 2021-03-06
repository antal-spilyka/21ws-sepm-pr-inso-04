package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.NewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserLoginDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventPlaceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallplanElementRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.OrderRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PaymentInformationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventPlaceService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.HallService;
import at.ac.tuwien.sepm.groupphase.backend.service.ArtistService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class NewsEndpointTest implements TestData {

    @Autowired
    private EventService eventService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventPlaceMapper eventPlaceMapper;

    @Autowired
    private EventPlaceService eventPlaceService;

    @Autowired
    private HallService hallService;

    @Autowired
    private PaymentInformationRepository paymentInformationRepository;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private UserRepository userRepository;

    private HallDto hallDto;
    private Hall hall;
    private AddressDto addressDto;
    private EventPlaceDto eventPlaceDto;
    private EventPlace eventPlace;
    private Artist artist;
    private Event event;
    private List<Performance> performances = new ArrayList<>();

    @Autowired
    private HallplanElementRepository hallplanElementRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    OrderRepository orderRepository;

    @BeforeAll
    public void beforeAll() {
        performanceRepository.deleteAll();
        hallRepository.deleteAll();
        hallplanElementRepository.deleteAll();
        orderRepository.deleteAll();
        paymentInformationRepository.deleteAll();
    }

    @BeforeEach
    public void beforeEach() {
        userRepository.deleteAll();

        AddressDto addressDto = new AddressDto();
        addressDto.setZip("1234");
        addressDto.setState("TestStateNews5");
        addressDto.setCountry("TestCountryNews5");
        addressDto.setCity("TestCityNews5");
        addressDto.setStreet("TestStreet");

        EventPlaceDto eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("TestPlaceNews5");
        eventPlaceDto.setAddressDto(addressDto);
        eventPlaceService.save(eventPlaceDto);

        hallDto = new HallDto();
        hallDto.setName("TestHall");
        hallDto.setEventPlaceDto(eventPlaceDto);

        ArtistDto artistDto = new ArtistDto();
        artistDto.setBandName("TestArtistNews5");
        artistDto.setDescription("an artistNews5");
        this.artist = artistService.save(artistDto);
    }


    @Test
    public void addNewsWithoutAdminRights_shouldReturnHttpStatusForbidden() throws Exception {
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
        LocalDateTime date = LocalDateTime.now();

        NewsDto newsDto = new NewsDto();
        newsDto.setEvent(eventMapper.entityToDto(this.event));
        newsDto.setRating(5L);
        newsDto.setFsk(18L);
        newsDto.setShortDescription("This is a short Description");
        newsDto.setLongDescription("This is a bit longer Description");
        newsDto.setCreateDate(date);

        String body = objectMapper.writeValueAsString(user1);

        // Register
        MvcResult mvcResult = this.mockMvc.perform(post(USER_BASE_URI + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        String body2 = objectMapper.writeValueAsString(UserLoginDto.UserLoginDtoBuilder.anUserLoginDto()
            .withEmail(user1.getEmail()).withPassword(user1.getPassword()).build());

        // Login
        MvcResult mvcResult2 = this.mockMvc.perform(post(AUTHENTICATION_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body2))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response2 = mvcResult2.getResponse();
        assertEquals(HttpStatus.OK.value(), response2.getStatus());

        String body3 = objectMapper.writeValueAsString(newsDto);

        MvcResult mvcResult3 = this.mockMvc.perform(post(NEWS_BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body3))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response3 = mvcResult3.getResponse();
        assertEquals(HttpStatus.FORBIDDEN.value(), response3.getStatus());
    }
}
