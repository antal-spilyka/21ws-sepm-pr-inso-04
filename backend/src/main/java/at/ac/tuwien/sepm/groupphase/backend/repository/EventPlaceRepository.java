package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository regarding EventPlace.
 */
@Repository
public interface EventPlaceRepository extends JpaRepository<EventPlace, String> {

    /**
     * Finds EventPlace that contains name.
     *
     * @param name     of the eventPlace
     * @param pageable regulates the number of results
     * @return list of fitting eventplaces
     * @throws javax.persistence.PersistenceException when unknown error occurs
     */
    @Query("SELECT e FROM EventPlace e WHERE UPPER(e.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    List<EventPlace> findEventPlace(@Param("name") String name, Pageable pageable);

    /**
     * Gets Category by primary key (name).
     *
     * @param name of the eventPlace
     * @return corresponding eventPlace
     * @throws javax.persistence.PersistenceException when unknown error occurs
     */
    EventPlace getById(String name);

    /**
     * Persists eventPlace.
     *
     * @param eventPlace to be persisted
     * @return persisted eventPlace
     */
    EventPlace save(EventPlace eventPlace);

    /**
     * Returns true if entity with id exists.
     *
     * @param name of the eventplace
     * @return boolean
     */
    boolean existsById(String name);
}
