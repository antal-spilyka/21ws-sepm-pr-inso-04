package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository regarding Event.
 */
public interface EventRepository extends JpaRepository<Event, Long> {
    /**
     * Persist new Event.
     *
     * @param event containing all properties to be persisted
     * @return persisted event (contains id)
     * @throws javax.persistence.PersistenceException when unknown error occurs
     */
    Event save(Event event);

    /**
     * Get event by primary key (id).
     *
     * @param id of the event
     * @return corresponding event
     * @throws javax.persistence.PersistenceException when unknown error occurs
     */
    Event getById(Long id);

    @Query("SELECT a FROM Event a WHERE (:duration is null OR (a.duration <= :duration+30 AND a.duration >= :duration-30)) " +
        "AND (:content is null OR :content='' OR UPPER(a.content) LIKE UPPER(CONCAT( '%', :content, '%'))) AND " +
        "(:categoryName is null OR :categoryName='' OR UPPER(a.category) LIKE UPPER(CONCAT( '%', :categoryName, '%')))" +
        "AND (:description is null OR :description='' OR UPPER(a.description) LIKE UPPER(CONCAT( '%', :description, '%')))")
    List<Event> findEvents(@Param("duration") Integer duration, @Param("content") String content,
                           @Param("categoryName") String categoryName, @Param("description") String description,
                           Pageable pageable);
}
