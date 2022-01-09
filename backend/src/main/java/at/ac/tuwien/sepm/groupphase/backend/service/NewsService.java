package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.NewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleSeenNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;

import java.util.List;

public interface NewsService {

    /**
     * Persists a new event.
     *
     * @param newsDto containing properties to be persisted
     */
    News save(NewsDto newsDto);

    /**
     * Returns a list of all unseen news.
     */
    List<SimpleNewsDto> getNewNews(String email);

    /**
     * Returns a list of all seen news.
     */
    List<SimpleNewsDto> getOldNews(String email);

    /**
     * Returns NewsDto with id.
     *
     * @param simpleSeenNewsDto which contains the id of the user and the news
     * @return NewsDto with corresponding id
     */
    NewsDto readNews(SimpleSeenNewsDto simpleSeenNewsDto);

}
