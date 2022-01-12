package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.NewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.Picture;
import at.ac.tuwien.sepm.groupphase.backend.exception.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Component
public class NewsMapper {
    private final EventMapper eventMapper;
    private final PictureMapper pictureMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public NewsMapper(EventMapper eventMapper, PictureMapper pictureMapper) {
        this.eventMapper = eventMapper;
        this.pictureMapper = pictureMapper;
    }

    public News dtoToEntity(NewsDto newsDto) {
        LOGGER.trace("Mapping {}", newsDto);
        News news = new News();
        if (newsDto.getEvent() != null) {
            news.setEvent(eventMapper.dtoToEntity(newsDto.getEvent()));

        }
        if (newsDto.getRating() != null && (newsDto.getRating() < 0 || newsDto.getRating() > 5)) {
            throw new MappingException("Rating has to be between 0 and 5");
        }
        news.setRating(newsDto.getRating());
        if (newsDto.getRating() != null && newsDto.getFsk() < 0) {
            throw new MappingException("Age cannot be under 0!");
        }
        news.setHeadline(newsDto.getHeadline());
        news.setFsk(newsDto.getFsk());
        news.setShortDescription(newsDto.getShortDescription());
        news.setLongDescription(newsDto.getLongDescription());
        news.setCreateDate(newsDto.getCreateDate());
        return news;
    }

    public NewsDto entityToDto(News news) {
        LOGGER.trace("Mapping {}", news);
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        if (news.getEvent() != null) {
            newsDto.setEvent(eventMapper.entityToDto(news.getEvent()));
        }
        newsDto.setHeadline(news.getHeadline());
        newsDto.setRating(news.getRating());
        newsDto.setFsk(news.getFsk());
        newsDto.setShortDescription(news.getShortDescription());
        newsDto.setLongDescription(news.getLongDescription());
        newsDto.setCreateDate(news.getCreateDate());
        return newsDto;
    }

    public NewsDto entityToDto(News news, List<Picture> pictures) {
        LOGGER.trace("Mapping {}", news);
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        if (news.getEvent() != null) {
            newsDto.setEvent(eventMapper.entityToDto(news.getEvent()));
        }
        newsDto.setHeadline(news.getHeadline());
        newsDto.setRating(news.getRating());
        newsDto.setFsk(news.getFsk());
        newsDto.setShortDescription(news.getShortDescription());
        newsDto.setLongDescription(news.getLongDescription());
        newsDto.setCreateDate(news.getCreateDate());
        newsDto.setPictures(pictures == null ? null : pictureMapper.entityToDto(pictures));
        return newsDto;
    }

    public List<NewsDto> entityToDto(List<News> news) {
        LOGGER.trace("entityToDto(List<News>)");
        if (news == null) {
            return null;
        }
        List<NewsDto> all = new ArrayList<>();
        for (int i = 0; i < news.size(); i++) {
            all.add(this.entityToDto(news.get(i)));
        }
        return all;
    }

    public SimpleNewsDto entityToSimpleDto(News news, Picture picture) {
        LOGGER.trace("Mapping {}", news);
        SimpleNewsDto simpleNewsDto = new SimpleNewsDto();
        simpleNewsDto.setId(news.getId());
        if (news.getEvent() != null) {
            simpleNewsDto.setEventDate(news.getEvent().getStartTime());
            simpleNewsDto.setEventName(news.getEvent().getName());
        }
        simpleNewsDto.setHeadline(news.getHeadline());
        simpleNewsDto.setShortDescription(news.getShortDescription());
        simpleNewsDto.setLongDescription(news.getLongDescription());
        simpleNewsDto.setCreateDate(news.getCreateDate());
        simpleNewsDto.setPicture(picture == null ? null : pictureMapper.entityToDto(picture));
        return simpleNewsDto;
    }
}
