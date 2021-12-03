package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventPlaceMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.*;
import org.junit.jupiter.api.BeforeAll;
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

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class NewsServiceTest implements TestData {

    @Autowired
    EventService eventService;
    @Autowired
    EventPlaceMapper eventPlaceMapper;
    @Autowired
    EventPlaceService eventPlaceService;
    @Autowired
    RoomService roomService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ArtistService artistService;
    @Autowired
    NewsService newsService;

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    NewsMapper newsMapper;

    RoomDto roomDto;
    CategoryDto categoryDto;
    ArtistDto artistDto;
    EventDto eventDto;

    @BeforeAll
    public void insertNeededContext() {
        AddressDto addressDto = new AddressDto();
        addressDto.setZip(1234);
        addressDto.setState("TestState");
        addressDto.setCountry("TestCountry");
        addressDto.setCity("TestCity");

        EventPlaceDto eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("TestPlace");
        eventPlaceDto.setAddressDto(addressDto);
        EventPlace eventPlace = eventPlaceMapper.dtoToEntity(eventPlaceService.save(eventPlaceDto));

        RoomInquiryDto roomInquiryDto = new RoomInquiryDto();
        roomInquiryDto.setName("TestRoom");
        roomInquiryDto.setEventPlaceName(eventPlace.getName());
        roomDto = roomService.save(roomInquiryDto);

        ArtistDto artistDto = new ArtistDto();
        artistDto.setBandName("TestArtist");
        artistDto.setDescription("an artist");
        this.artistDto = artistService.save(artistDto);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("testCategory");
        this.categoryDto = categoryService.save(categoryDto);
    }

    @Test
    public void insert_news_valid() {
        EventInquiryDto eventInquiryDto = new EventInquiryDto();
        eventInquiryDto.setName("testEvent");
        eventInquiryDto.setContent("testContent");
        eventInquiryDto.setDateTime(LocalDateTime.now());
        eventInquiryDto.setDuration(120);
        eventInquiryDto.setCategoryName(categoryDto.getName());
        eventInquiryDto.setRoomId(roomDto.getId());
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
    }

    @Test
    public void insert_news_invalid_event() {
        EventDto invalidEvent = eventDto;
        invalidEvent.setId(null);
        NewsDto newsDto = new NewsDto();
        newsDto.setEvent(invalidEvent);
        newsDto.setRating(5L);
        newsDto.setFsk(18L);
        newsDto.setShortDescription("This is a short Description");
        newsDto.setLongDescription("This is a bit longer Description");
        newsDto.setCreateDate(LocalDateTime.now());
        assertThrows(InvalidDataAccessApiUsageException.class, () -> newsRepository.save(newsMapper.dtoToEntity(newsDto)));
    }

    @Test
    public void insert_news_nullValue_rating() {
        NewsDto newsDto = new NewsDto();
        newsDto.setEvent(eventDto);
        newsDto.setFsk(18L);
        newsDto.setShortDescription("This is a short Description");
        newsDto.setLongDescription("This is a bit longer Description");
        newsDto.setCreateDate(LocalDateTime.now());
        assertThrows(DataIntegrityViolationException.class, () -> newsService.save(newsMapper.dtoToEntity(newsDto)));
    }

    @Test
    public void insert_news_invalid_compare() {
        EventInquiryDto eventInquiryDto = new EventInquiryDto();
        eventInquiryDto.setName("testEvent");
        eventInquiryDto.setContent("testContent");
        eventInquiryDto.setDateTime(LocalDateTime.now());
        eventInquiryDto.setDuration(120);
        eventInquiryDto.setCategoryName(categoryDto.getName());
        eventInquiryDto.setRoomId(roomDto.getId());
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
        eventInquiryDto.setName("testEvent");
        eventInquiryDto.setContent("testContent");
        eventInquiryDto.setDateTime(LocalDateTime.now());
        eventInquiryDto.setDuration(120);
        eventInquiryDto.setCategoryName(categoryDto.getName());
        eventInquiryDto.setRoomId(roomDto.getId());
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
        eventInquiryDto.setName("testEvent");
        eventInquiryDto.setContent("testContent");
        eventInquiryDto.setDateTime(LocalDateTime.now());
        eventInquiryDto.setDuration(120);
        eventInquiryDto.setCategoryName(categoryDto.getName());
        eventInquiryDto.setRoomId(roomDto.getId());
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
        assertEquals(size+1, newsService.getNewNews().size());
    }
}
