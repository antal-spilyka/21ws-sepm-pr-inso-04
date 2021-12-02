package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;

import java.util.List;

public interface NewsService {

    /**
     * Persists a new event.
     *
     * @param news containing properties to be persisted
     * @throws org.hibernate.service.spi.ServiceException when unknown error occurs
     * @throws at.ac.tuwien.sepm.groupphase.backend.exception.ContextException when entity already exists
     */
    void save(News news);

    /**
     * Persists a new event.
     *
     */
    List<News> getAll();
}
