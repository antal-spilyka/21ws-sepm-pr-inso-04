package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {
    /**
     * Persist new Event.
     *
     * @param news containing all properties to be persisted
     * @return persisted event (contains id)
     * @throws javax.persistence.PersistenceException when unknown error occurs
     */
    News save(News news);
}