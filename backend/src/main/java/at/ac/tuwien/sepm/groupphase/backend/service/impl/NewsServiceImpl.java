package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.NewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleSeenNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Picture;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeenNews;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PictureRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.SeenNewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final PictureRepository pictureRepository;
    private final SeenNewsRepository seenNewsRepository;
    private final UserRepository userRepository;

    public NewsServiceImpl(NewsRepository newsRepository, PictureRepository pictureRepository, NewsMapper newsMapper, SeenNewsRepository seenNewsRepository, UserRepository userRepository) {
        this.newsRepository = newsRepository;
        this.pictureRepository = pictureRepository;
        this.newsMapper = newsMapper;
        this.seenNewsRepository = seenNewsRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public News save(NewsDto newsDto) {
        LOGGER.debug("Publish new news {}", newsDto);
        return newsRepository.save(newsMapper.dtoToEntity(newsDto));
    }

    @Transactional
    @Override
    public List<News> getNewNews(String email) {
        LOGGER.debug("Get all News");
        /*LocalDateTime beforeSevenDays = LocalDateTime.now().minusDays(7);
        List<News> filteredList = newsRepository.findByCreateDateAfter(beforeSevenDays);
        for (News news : filteredList) {
            for (Performance performance : news.getEvent().getPerformances()) {
                performance.setEvent(null);
            }
        }*/
        ApplicationUser user = userRepository.findUserByEmail(email);
        List<SeenNews> seenNewsList = seenNewsRepository.findByUser(user);
        List<News> newsList = newsRepository.findAll();
        for (SeenNews e : seenNewsList) {
            newsList.remove(e.getNews());
        }
        for (News news : newsList) {
            for (Performance performance : news.getEvent().getPerformances()) {
                performance.setEvent(null);
            }
        }
        LOGGER.info("" + newsList.size());

        return newsList;
    }

    @Transactional
    @Override
    public List<News> getOldNews(String email) {
        ApplicationUser user = userRepository.findUserByEmail(email);
        List<SeenNews> seenNewsList = seenNewsRepository.findByUser(user);
        List<News> newsList = new ArrayList<>();
        for (SeenNews e : seenNewsList) {
            newsList.add(e.getNews());
        }
        for (News news : newsList) {
            for (Performance performance : news.getEvent().getPerformances()) {
                performance.setEvent(null);
            }
        }
        LOGGER.info("" + newsList.size());
        return newsList;
    }

    @Transactional
    @Override
    public NewsDto readNews(SimpleSeenNewsDto simpleSeenNewsDto) {
        LOGGER.debug("Get news by id");
        try {
            News news = newsRepository.getById(simpleSeenNewsDto.getNewsId());
            ApplicationUser user = userRepository.findUserByEmail(simpleSeenNewsDto.getUserEmail());
            SeenNews seenNews = new SeenNews();
            seenNews.setNews(news);
            seenNews.setUser(user);
            seenNewsRepository.save(seenNews);

            List<Picture> pictures = pictureRepository.findByNewsId(news);
            return newsMapper.entityToDto(news, pictures);
        } catch (EntityNotFoundException e) {
            LOGGER.debug("News with id {} not found", simpleSeenNewsDto.getNewsId());
            throw new EntityNotFoundException(e.getMessage());
        }

    }
}
