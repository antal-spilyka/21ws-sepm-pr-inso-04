package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.NewsDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;

import java.util.List;

public interface NewsService {

    /**
     * Persists a new event.
     *
     * @param news containing properties to be persisted
     * @throws org.hibernate.service.spi.ServiceException                      when unknown error occurs
     * @throws at.ac.tuwien.sepm.groupphase.backend.exception.ContextException when entity already exists
     */
    void save(News news);

    /**
     * Persists a new event.
     */
    List<News> getNewNews();

    /**
     * Returns NewsDto with id.
     *
     * @param id of the news
     * @return NewsDto with corresponding id
     * @throws org.hibernate.service.spi.ServiceException                      when unknown error occurs
     */
    NewsDto getById(Long id);

}
