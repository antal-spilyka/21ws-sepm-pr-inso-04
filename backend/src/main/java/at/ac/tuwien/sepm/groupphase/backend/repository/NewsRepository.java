package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {
    /**
     * Persist new Event.
     *
     * @param news containing all properties to be persisted
     * @return persisted event (contains id)
     * @throws javax.persistence.PersistenceException when unknown error occurs
     */
    News save(News news);

    /**
     * Finds all the news which are not older than 7 days.
     *
     * @param beforeSevenDays of the date of news
     * @return all matching news.
     */
    @Query("SELECT n FROM News n WHERE n.createDate >= :beforeSevenDays")
    List<News> findByCreateDateAfter(@Param("beforeSevenDays") LocalDateTime beforeSevenDays);

    /**
     * Get Event by id.
     *
     * @param id of the evnt
     * @return News with corresponding id
     * @throws javax.persistence.PersistenceException when unknown error occurs
     */
    News getById(Long id);
}