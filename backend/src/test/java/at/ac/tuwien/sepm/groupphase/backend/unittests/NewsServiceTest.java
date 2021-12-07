package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.*;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventPlaceMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class NewsServiceTest implements TestData {

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

    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsMapper newsMapper;

    private HallDto hallDto;
    private ArtistDto artistDto;
    private EventDto eventDto;
    private Artist artist;
    private Event event;
    private List<PerformanceDto> performances = new ArrayList<>();

    @BeforeAll
    public void insertNeededContext() {
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
    public void insert_news_valid() {
        AddressDto addressDto = new AddressDto();
        addressDto.setZip("1234");
        addressDto.setState("TestState");
        addressDto.setCountry("TestCountry");
        addressDto.setCity("TestCity");
        addressDto.setStreet("TestStreet");

        EventPlaceDto eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("TestPlace2");
        eventPlaceDto.setAddressDto(addressDto);
        EventPlace eventPlace = eventPlaceService.save(eventPlaceDto);

        Hall hall = new Hall();
        hallDto.setName("TestHall");
        hallDto.setEventPlaceDto(eventPlaceMapper.entityToDto(eventPlace));
        hall = hallService.save(hallDto);

        EventDto eventDto = new EventDto();
        eventDto.setName("testEventNews6");
        eventDto.setStartTime(LocalDateTime.now());
        eventDto.setDuration(120L);
        eventDto.setEventPlace(eventPlaceDto);
        this.event = eventService.saveEvent(eventDto);

        PerformanceDto performance = new PerformanceDto();
        performance.setName("TestPerformance");
        performance.setStartTime(LocalDateTime.now());
        performance.setDuration(50L);
        performance.setEvent(eventDto);
        performance.setArtist(artistDto);
        performance.setHall(hallDto);
        this.performances.add(performance);

        eventDto.setPerformances(this.performances);
        this.event = eventService.saveEvent(eventDto);

        LocalDateTime date = LocalDateTime.now();

        NewsDto newsDto = new NewsDto();
        newsDto.setEvent(eventDto);
        newsDto.setRating(5L);
        newsDto.setFsk(18L);
        newsDto.setShortDescription("This is a short Description");
        newsDto.setLongDescription("This is a bit longer Description");
        newsDto.setCreateDate(date);
        News firstNews = newsService.save(newsDto);

        News secondNews = new News();
        secondNews.setId(firstNews.getId());
        secondNews.setEvent(firstNews.getEvent());
        secondNews.setRating(firstNews.getRating());
        secondNews.setFsk(firstNews.getFsk());
        secondNews.setShortDescription(firstNews.getShortDescription());
        secondNews.setCreateDate(date);
        secondNews.setLongDescription(firstNews.getLongDescription());

        assertEquals(firstNews, secondNews);
    }

    @Test
    public void insert_news_nullValue_event() {
        EventDto invalidEvent = eventDto;
        NewsDto newsDto = new NewsDto();
        newsDto.setEvent(invalidEvent);
        newsDto.setRating(5L);
        newsDto.setFsk(18L);
        newsDto.setShortDescription("This is a short Description");
        newsDto.setLongDescription("This is a bit longer Description");
        newsDto.setCreateDate(LocalDateTime.now());
        assertThrows(NullPointerException.class, () -> newsRepository.save(newsMapper.dtoToEntity(newsDto)));
    }

    @Test
    public void insert_news_nullValue_rating() {
        NewsDto newsDto = new NewsDto();
        newsDto.setEvent(eventDto);
        newsDto.setFsk(18L);
        newsDto.setShortDescription("This is a short Description");
        newsDto.setLongDescription("This is a bit longer Description");
        newsDto.setCreateDate(LocalDateTime.now());
        assertThrows(NullPointerException.class, () -> newsService.save(newsDto));
    }

    @Test
    public void insert_news_invalid_compare() {
        AddressDto addressDto = new AddressDto();
        addressDto.setZip("1234");
        addressDto.setState("TestState");
        addressDto.setCountry("TestCountry");
        addressDto.setCity("TestCity");
        addressDto.setStreet("TestStreet");

        EventPlaceDto eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("TestPlace2");
        eventPlaceDto.setAddressDto(addressDto);
        EventPlace eventPlace = eventPlaceService.save(eventPlaceDto);

        Hall hall = new Hall();
        hallDto.setName("TestHall");
        hallDto.setEventPlaceDto(eventPlaceMapper.entityToDto(eventPlace));
        hall = hallService.save(hallDto);

        EventDto eventDto = new EventDto();
        eventDto.setName("testEventNews6");
        eventDto.setStartTime(LocalDateTime.now());
        eventDto.setDuration(120L);
        eventDto.setEventPlace(eventPlaceDto);
        this.event = eventService.saveEvent(eventDto);

        PerformanceDto performance = new PerformanceDto();
        performance.setName("TestPerformance");
        performance.setStartTime(LocalDateTime.now());
        performance.setDuration(50L);
        performance.setEvent(eventDto);
        performance.setArtist(artistDto);
        performance.setHall(hallDto);
        this.performances.add(performance);

        eventDto.setPerformances(this.performances);
        this.event = eventService.saveEvent(eventDto);

        LocalDateTime date = LocalDateTime.now();

        NewsDto newsDto = new NewsDto();
        newsDto.setEvent(eventDto);
        newsDto.setRating(5L);
        newsDto.setFsk(18L);
        newsDto.setShortDescription("This is a short Description");
        newsDto.setLongDescription("This is a bit longer Description");
        newsDto.setCreateDate(date);
        News firstNews = newsService.save(newsDto);

        News secondNews = new News();
        secondNews.setId(-100000L);
        secondNews.setEvent(firstNews.getEvent());
        secondNews.setRating(firstNews.getRating());
        secondNews.setFsk(firstNews.getFsk());
        secondNews.setShortDescription(firstNews.getShortDescription());
        secondNews.setLongDescription(firstNews.getLongDescription());
        secondNews.setCreateDate(LocalDateTime.now());

        assertNotEquals(firstNews, secondNews);
    }

    @Test
    public void oldNewsShouldNotBeInNewNewsList() {
        AddressDto addressDto = new AddressDto();
        addressDto.setZip("1234");
        addressDto.setState("TestState");
        addressDto.setCountry("TestCountry");
        addressDto.setCity("TestCity");
        addressDto.setStreet("TestStreet");

        EventPlaceDto eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("TestPlace2");
        eventPlaceDto.setAddressDto(addressDto);
        EventPlace eventPlace = eventPlaceService.save(eventPlaceDto);

        Hall hall = new Hall();
        hallDto.setName("TestHall");
        hallDto.setEventPlaceDto(eventPlaceMapper.entityToDto(eventPlace));
        hall = hallService.save(hallDto);

        EventDto eventDto = new EventDto();
        eventDto.setName("testEventNews6");
        eventDto.setStartTime(LocalDateTime.now());
        eventDto.setDuration(120L);
        eventDto.setEventPlace(eventPlaceDto);
        this.event = eventService.saveEvent(eventDto);

        PerformanceDto performance = new PerformanceDto();
        performance.setName("TestPerformance");
        performance.setStartTime(LocalDateTime.now());
        performance.setDuration(50L);
        performance.setEvent(eventDto);
        performance.setArtist(artistDto);
        performance.setHall(hallDto);
        this.performances.add(performance);

        eventDto.setPerformances(this.performances);
        this.event = eventService.saveEvent(eventDto);

        // old size of newsTable
        int size = newsService.getNewNews().size();
        NewsDto newsDto = new NewsDto();
        newsDto.setEvent(eventDto);
        newsDto.setRating(5L);
        newsDto.setFsk(18L);
        newsDto.setShortDescription("This is a short Description");
        newsDto.setLongDescription("This is a bit longer Description");
        newsDto.setCreateDate(LocalDateTime.now().minusDays(8));
        newsService.save(newsDto);

        // there shouldn't be a difference
        assertEquals(size, newsService.getNewNews().size());
    }

    @Test
    public void newNewsShouldBeInNewNewsList() {
        AddressDto addressDto = new AddressDto();
        addressDto.setZip("1234");
        addressDto.setState("TestState");
        addressDto.setCountry("TestCountry");
        addressDto.setCity("TestCity");
        addressDto.setStreet("TestStreet");

        EventPlaceDto eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("TestPlace2");
        eventPlaceDto.setAddressDto(addressDto);
        EventPlace eventPlace = eventPlaceService.save(eventPlaceDto);

        Hall hall = new Hall();
        hallDto.setName("TestHall");
        hallDto.setEventPlaceDto(eventPlaceMapper.entityToDto(eventPlace));
        hall = hallService.save(hallDto);

        EventDto eventDto = new EventDto();
        eventDto.setName("testEventNews6");
        eventDto.setStartTime(LocalDateTime.now());
        eventDto.setDuration(120L);
        eventDto.setEventPlace(eventPlaceDto);
        this.event = eventService.saveEvent(eventDto);

        PerformanceDto performance = new PerformanceDto();
        performance.setName("TestPerformance");
        performance.setStartTime(LocalDateTime.now());
        performance.setDuration(50L);
        performance.setEvent(eventDto);
        performance.setArtist(artistDto);
        performance.setHall(hallDto);
        this.performances.add(performance);

        eventDto.setPerformances(this.performances);
        this.event = eventService.saveEvent(eventDto);

        LocalDateTime date = LocalDateTime.now();

        // old size of newsTable
        int size = newsService.getNewNews().size();
        NewsDto newsDto = new NewsDto();
        newsDto.setEvent(eventDto);
        newsDto.setRating(5L);
        newsDto.setFsk(18L);
        newsDto.setShortDescription("This is a short Description");
        newsDto.setLongDescription("This is a bit longer Description");
        newsDto.setCreateDate(LocalDateTime.now().minusDays(6));
        newsService.save(newsDto);

        // there shouldn't be a difference
        assertEquals(size + 1, newsService.getNewNews().size());
    }
}
