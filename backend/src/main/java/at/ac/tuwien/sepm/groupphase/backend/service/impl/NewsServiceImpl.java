package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.NewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.NewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PictureDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Picture;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PictureRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final PictureRepository pictureRepository;

    public NewsServiceImpl(NewsRepository newsRepository, PictureRepository pictureRepository, NewsMapper newsMapper) {
        this.newsRepository = newsRepository;
        this.pictureRepository = pictureRepository;
        this.newsMapper = newsMapper;
    }

    @Transactional
    @Override
    public News save(NewsDto newsDto) {
        LOGGER.debug("Publish new news {}", newsDto);
        return newsRepository.save(newsMapper.dtoToEntity(newsDto));
    }

    @Transactional
    @Override
    public List<News> getNewNews() {
        LOGGER.debug("Get all News");
        LocalDateTime beforeSevenDays = LocalDateTime.now().minusDays(7);
        List<News> filteredList = newsRepository.findByCreateDateAfter(beforeSevenDays);
        for (News news : filteredList) {
            for (Performance performance : news.getEvent().getPerformances()) {
                performance.setEvent(null);
            }
        }
        return filteredList;
    }

    @Transactional
    @Override
    public NewsDto getById(Long id) {
        LOGGER.debug("Get news by id");
        try {
            News news = newsRepository.getById(id);
            List<Picture> pictures = pictureRepository.findByNewsId(news);
            return newsMapper.entityToDto(news, pictures);
        } catch (EntityNotFoundException e) {
            LOGGER.debug("News with id {} not found", id);
            throw new EntityNotFoundException(e.getMessage());
        }

    }
}
