package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventPlaceMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventPlaceService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.HallService;
import at.ac.tuwien.sepm.groupphase.backend.service.ArtistService;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import java.lang.invoke.MethodHandles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class NewsEndpointTest implements TestData {

    @Autowired
    EventService eventService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EventPlaceMapper eventPlaceMapper;

    @Autowired
    EventPlaceService eventPlaceService;

    @Autowired
    HallService hallService;
    @Autowired
    ArtistService artistService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    NewsService newsService;

    @Autowired
    NewsRepository newsRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    NewsMapper newsMapper;

    private HallDto hallDto;
    private ArtistDto artistDto;
    private Artist artist;
    private EventDto eventDto;

    @BeforeEach
    public void beforeEach() {
        userRepository.deleteAll();

        AddressDto addressDto = new AddressDto();
        addressDto.setZip("1234");
        addressDto.setState("TestStateNews5");
        addressDto.setCountry("TestCountryNews5");
        addressDto.setCity("TestCityNews5");

        EventPlaceDto eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("TestPlaceNews5");
        eventPlaceDto.setAddressDto(addressDto);
        EventPlace eventPlace = eventPlaceService.save(eventPlaceDto);

        hallDto = new HallDto();
        hallDto.setName("TestHall");
        hallDto.setEventPlaceDto(eventPlaceDto);

        ArtistDto artistDto = new ArtistDto();
        artistDto.setBandName("TestArtistNews5");
        artistDto.setDescription("an artistNews5");
        this.artist = artistService.save(artistDto);
    }


    /*@Test
    public void addNewsWithoutAdminRights_shouldReturnHttpStatusForbidden() throws Exception {
        Event event = new Event();
        eventInquiryDto.setName("testEventNews6");
        eventInquiryDto.setContent("testContentNews6");
        eventInquiryDto.setDateTime(LocalDateTime.now());
        eventInquiryDto.setDuration(120);
        eventInquiryDto.setRoomId(hallDto.getId());
        eventInquiryDto.setArtistId(artistDto.getId());
        this.eventDto = eventService.createEvent(eventInquiryDto);

        LocalDateTime date = LocalDateTime.now();

        NewsDto newsDto = new NewsDto();
        newsDto.setEvent(eventDto);
        newsDto.setRating(5L);
        newsDto.setFsk(18L);
        newsDto.setShortDescription("This is a short Description");
        newsDto.setLongDescription("This is a bit longer Description");
        newsDto.setCreateDate(date);

        String body = objectMapper.writeValueAsString(user1);

        // Register
        MvcResult mvcResult = this.mockMvc.perform(post(USER_BASE_URI)
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
    }*/
}
