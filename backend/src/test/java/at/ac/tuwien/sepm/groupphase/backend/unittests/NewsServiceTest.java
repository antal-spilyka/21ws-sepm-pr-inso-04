package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.NewsDto;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventPlaceMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

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

    /*@BeforeAll todo
    public void insertNeededContext() {
        AddressDto addressDto = new AddressDto();
        addressDto.setZip("1234");
        addressDto.setState("TestStateNews");
        addressDto.setCountry("TestCountryNews");
        addressDto.setCity("TestCityNews");

        EventPlaceDto eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("TestPlaceNews");
        eventPlaceDto.setAddressDto(addressDto);
        EventPlace eventPlace = eventPlaceMapper.dtoToEntity(eventPlaceService.save(eventPlaceDto));

        RoomInquiryDto roomInquiryDto = new RoomInquiryDto();
        roomInquiryDto.setName("TestRoomNews");
        roomInquiryDto.setEventPlaceName(eventPlace.getName());
        hallDto = roomService.save(roomInquiryDto);

        ArtistDto artistDto = new ArtistDto();
        artistDto.setBandName("TestArtistNews");
        artistDto.setDescription("an artistNews");
        this.artistDto = artistService.save(artistDto);
    }

    @Test
    public void insert_news_valid() {
        EventInquiryDto eventInquiryDto = new EventInquiryDto();
        eventInquiryDto.setName("testEventNews2");
        eventInquiryDto.setContent("testContentNews2");
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
        News firstNews = newsRepository.save(newsMapper.dtoToEntity(newsDto));

        News secondNews = new News();
        secondNews.setId(firstNews.getId());
        secondNews.setEvent(firstNews.getEvent());
        secondNews.setRating(firstNews.getRating());
        secondNews.setFsk(firstNews.getFsk());
        secondNews.setShortDescription(firstNews.getShortDescription());
        secondNews.setCreateDate(date);
        secondNews.setLongDescription(firstNews.getLongDescription());

        assertEquals(firstNews, secondNews);
    }*/

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

    /*@Test todo
    public void insert_news_invalid_compare() {
        EventInquiryDto eventInquiryDto = new EventInquiryDto();
        eventInquiryDto.setName("testEventNews7");
        eventInquiryDto.setContent("testContentNews7");
        eventInquiryDto.setDateTime(LocalDateTime.now());
        eventInquiryDto.setDuration(120);
        eventInquiryDto.setRoomId(hallDto.getId());
        eventInquiryDto.setArtistId(artistDto.getId());
        this.eventDto = eventService.createEvent(eventInquiryDto);

        NewsDto newsDto = new NewsDto();
        newsDto.setEvent(eventDto);
        newsDto.setRating(5L);
        newsDto.setFsk(18L);
        newsDto.setShortDescription("This is a short Description");
        newsDto.setLongDescription("This is a bit longer Description");
        newsDto.setCreateDate(LocalDateTime.now());
        News firstNews = newsRepository.save(newsMapper.dtoToEntity(newsDto));

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
    public void oldNewsShouldntBeInNewNewsList() {
        EventInquiryDto eventInquiryDto = new EventInquiryDto();
        eventInquiryDto.setName("testEventNews8");
        eventInquiryDto.setContent("testContentNews8");
        eventInquiryDto.setDateTime(LocalDateTime.now());
        eventInquiryDto.setDuration(120);
        eventInquiryDto.setRoomId(hallDto.getId());
        eventInquiryDto.setArtistId(artistDto.getId());
        this.eventDto = eventService.createEvent(eventInquiryDto);

        // old size of newsTable
        int size = newsService.getNewNews().size();
        NewsDto newsDto = new NewsDto();
        newsDto.setEvent(eventDto);
        newsDto.setRating(5L);
        newsDto.setFsk(18L);
        newsDto.setShortDescription("This is a short Description");
        newsDto.setLongDescription("This is a bit longer Description");
        newsDto.setCreateDate(LocalDateTime.now().minusDays(8));
        newsService.save(newsMapper.dtoToEntity(newsDto));

        // there shouldn't be a difference
        assertEquals(size, newsService.getNewNews().size());
    }

    @Test
    public void newNewsShouldBeInNewNewsList() {
        EventInquiryDto eventInquiryDto = new EventInquiryDto();
        eventInquiryDto.setName("testEventNews9");
        eventInquiryDto.setContent("testContentNews9");
        eventInquiryDto.setDateTime(LocalDateTime.now());
        eventInquiryDto.setDuration(120);
        eventInquiryDto.setRoomId(hallDto.getId());
        eventInquiryDto.setArtistId(artistDto.getId());
        this.eventDto = eventService.createEvent(eventInquiryDto);

        // old size of newsTable
        int size = newsService.getNewNews().size();
        NewsDto newsDto = new NewsDto();
        newsDto.setEvent(eventDto);
        newsDto.setRating(5L);
        newsDto.setFsk(18L);
        newsDto.setShortDescription("This is a short Description");
        newsDto.setLongDescription("This is a bit longer Description");
        newsDto.setCreateDate(LocalDateTime.now().minusDays(6));
        newsService.save(newsMapper.dtoToEntity(newsDto));

        // there shouldn't be a difference
        assertEquals(size + 1, newsService.getNewNews().size());
    }*/
}
