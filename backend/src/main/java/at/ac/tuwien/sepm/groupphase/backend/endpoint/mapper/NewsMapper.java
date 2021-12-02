package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.NewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleMessageDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Message;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Component
public class NewsMapper {
    private final EventMapper eventMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public NewsMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    public News dtoToEntity(NewsDto newsDto) {
        LOGGER.trace("Mapping {}", newsDto);
        News news = new News();
        news.setEvent(eventMapper.dtoToEntity(newsDto.getEvent()));
        news.setRating(newsDto.getRating());
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
        newsDto.setEvent(eventMapper.entityToDto(news.getEvent()));
        newsDto.setRating(news.getRating());
        newsDto.setFsk(news.getFsk());
        newsDto.setShortDescription(news.getShortDescription());
        newsDto.setLongDescription(news.getLongDescription());
        newsDto.setCreateDate(news.getCreateDate());
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
}
