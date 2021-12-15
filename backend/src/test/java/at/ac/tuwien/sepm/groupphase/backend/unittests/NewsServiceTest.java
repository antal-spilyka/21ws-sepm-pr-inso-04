package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.NewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ArtistMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventPlaceMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.PerformanceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.ArtistService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventPlaceService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.HallService;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    private HallService hallService;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private PerformanceMapper performanceMapper;

   @Autowired
   private ArtistMapper artistMapper;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsMapper newsMapper;

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
        Event eventPers = eventService.saveEvent(eventMapper.entityToDto(this.event));

        LocalDateTime date = LocalDateTime.now();

        NewsDto newsDto = new NewsDto();
        newsDto.setEvent(eventMapper.entityToDto(eventPers));
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
        NewsDto newsDto = new NewsDto();
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
        newsDto.setEvent(eventMapper.entityToDto(this.event));
        newsDto.setFsk(18L);
        newsDto.setShortDescription("This is a short Description");
        newsDto.setLongDescription("This is a bit longer Description");
        newsDto.setCreateDate(LocalDateTime.now());
        assertThrows(DataIntegrityViolationException.class, () -> newsService.save(newsDto));
    }

    @Test
    public void insert_news_invalid_compare() {
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
        this.event = eventService.saveEvent(eventMapper.entityToDto(event));

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
        Event eventPers = eventService.saveEvent(eventMapper.entityToDto(this.event));

        // old size of newsTable
        int size = newsService.getNewNews().size();
        NewsDto newsDto = new NewsDto();
        newsDto.setEvent(eventMapper.entityToDto(eventPers));
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
        Event eventPers = eventService.saveEvent(eventMapper.entityToDto(this.event));

        // old size of newsTable
        int size = newsService.getNewNews().size();
        NewsDto newsDto = new NewsDto();
        newsDto.setEvent(eventMapper.entityToDto(eventPers));
        newsDto.setRating(5L);
        newsDto.setFsk(18L);
        newsDto.setShortDescription("This is a short Description");
        newsDto.setLongDescription("This is a bit longer Description");
        newsDto.setCreateDate(LocalDateTime.now().minusDays(6));
        newsService.save(newsDto);

        // there should be not difference
        assertEquals(size + 1, newsService.getNewNews().size());
    }
}
