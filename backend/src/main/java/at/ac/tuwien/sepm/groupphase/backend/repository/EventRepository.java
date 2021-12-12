package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
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

    /**
     * Finds all the events which suit the criteria from parameters.
     *
     * @param duration     of the event
     * @param description of the event
     * @param pageable of the event
     * @return all matching events.
     */
    @Query("SELECT a FROM Event a WHERE (:duration is null OR (a.duration <= :duration+30 AND a.duration >= :duration-30))" +
        " AND (:description is null OR :description='' OR UPPER(a.description) LIKE UPPER(CONCAT( '%', :description, '%'))) " +
        "AND(:category is null OR :category='' OR UPPER(a.category) LIKE UPPER(CONCAT( '%', :description, '%')))")
    List<Event> findEvents(@Param("duration") Integer duration, @Param("description") String description, @Param("category") String category,
                           Pageable pageable);

    /*/**
     * Finds all the events which suit the criteria from parameters.
     *
     * @param dateTimeFrom of the event
     * @param dateTimeTill of the event
     * @param eventName        of the event
     * @param hallId       of the event
     * @return all matching events.
     */
    /*@Query("SELECT a FROM Event a WHERE (:startTime is null OR (a.startTime <= :dateTimeTill AND a.startTime >= :dateTimeFrom))" +
        "AND (:eventName is null OR :eventName='' OR UPPER(a.name) LIKE UPPER(CONCAT( '%', :eventName, '%'))) AND (:hallId is null " +
        "OR :hallId in (a.performances))")
    List<Event> findEventsWithDateTime(@Param("dateTimeFrom") LocalDateTime dateTimeFrom, @Param("dateTimeTill") LocalDateTime dateTimeTill,
                                       @Param("eventName") String eventName, @Param("hall") Long hallId);
*/
    /**
     * Finds all the events which suit the criteria from parameters.
     *
     * @param eventName  of the event
     * @param hallId of the event
     * @return all matching events.
     */
    @Query("SELECT a FROM Event a WHERE (:eventName is null OR :eventName='' OR UPPER(a.name) LIKE UPPER(CONCAT( '%', :eventName, '%'))) " +
        "AND (:hallId is null OR :hallId in (a.performances))")
    List<Event> findEventsWithoutDateTime(@Param("eventName") String eventName, @Param("hall") Long hallId);

    List<Event> findByNameContainsIgnoreCase(String name);
}
