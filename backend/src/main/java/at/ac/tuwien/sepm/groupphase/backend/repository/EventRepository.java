package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
