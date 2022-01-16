package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TopTenEventsDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

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
     * @param duration    of the event
     * @param description of the event
     * @param pageable    of the event
     * @return all matching events.
     */
    @Query("SELECT a FROM Event a WHERE (:duration is null OR (a.duration <= :duration+30 AND a.duration >= :duration-30))" +
        " AND (:description is null OR :description='' OR UPPER(a.description) LIKE UPPER(CONCAT( '%', :description, '%'))) " +
        "AND(:category is null OR :category='' OR UPPER(a.category) LIKE UPPER(CONCAT( '%', :category, '%')))")
    List<Event> findEvents(@Param("duration") Integer duration, @Param("description") String description, @Param("category") String category,
                           Pageable pageable);

    /**
     * Finds all the events which suit the criteria from parameters.
     *
     * @param generalQuery which suits category or description
     * @param pageable     of the event
     * @return all matching events.
     */
    @Query("SELECT e FROM Event e WHERE :generalQuery is null OR :generalQuery='' OR UPPER(e.description) LIKE " +
        "UPPER(CONCAT( '%', :generalQuery, '%')) OR UPPER(e.category) LIKE UPPER(CONCAT( '%', :generalQuery, '%'))")
    List<Event> findGeneralEvents(@Param("generalQuery") String generalQuery,
                                  Pageable pageable);


    /**
     * Finds all the events which suit the criteria from parameters.
     *
     * @param eventName of the event
     * @param hallId    of the event
     * @return all matching events.
     */
    @Query("SELECT a FROM Event a WHERE (:eventName is null OR :eventName='' OR UPPER(a.name) LIKE UPPER(CONCAT( '%', :eventName, '%'))) " +
        "AND (:hallId is null OR :hallId in (a.performances))")
    List<Event> findEventsWithoutDateTime(@Param("eventName") String eventName, @Param("hall") Long hallId);

    /**
     * Finds all the events which suit the criteria from parameters.
     *
     * @param name     of the event
     * @param pageable of the event
     * @return all matching events.
     */
    List<Event> findByNameContainsIgnoreCase(String name, Pageable pageable);

    /**
     * Finds all the events which suit the criteria from parameters.
     *
     * @param id       of the address of the event
     * @param pageable of the event
     * @return all matching events.
     */
    @Query("SELECT e FROM Event e WHERE :id=e.eventPlace.address.id")
    List<Event> findEventsByLocation(@Param("id") Long id, Pageable pageable);

    /**
     * Return true iff event with name already exists.
     *
     * @param name to be searched for
     * @return boolean if event with name exists
     */
    Boolean existsByName(String name);

    /**
     * Returns all categories.
     *
     * @return all matching events.
     */
    @Query("SELECT DISTINCT category FROM Event")
    List<String> findDistinctByOrderByCategoryAsc();

    /**
     * Finds all the events with their number of sold tickets in decreasing order for a specified category.
     *
     * @param category of the event
     * @return all matching events with the number of sold tickets.
     */
    @Query("SELECT e, COUNT(t) FROM Event e JOIN Performance p ON p.event = e JOIN Ticket t ON t.performance = p WHERE " +
        "p.event IS NOT NULL AND e.category = :category AND t.refunded = FALSE GROUP BY p.event ORDER BY COUNT(t) DESC")
    List<Object[]> findByCategoryEquals(@NonNull @Param("category") String category);
}
