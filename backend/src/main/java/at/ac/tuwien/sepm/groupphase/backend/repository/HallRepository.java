package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository regarding Hall.
 */
@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {

    /**
     * Finds hall that contains name (LIKE) and has eventPlace (equals).
     *
     * @param name       of the hall to search for
     * @param eventPlace the hall is in (exact)
     * @param pageable   regulates number of results
     * @return List of corresponding halls
     * @throws javax.persistence.PersistenceException when unknown error occurs
     */
    @Query("SELECT r FROM Hall r WHERE UPPER(r.name) LIKE UPPER(CONCAT('%', :name, '%')) " +
        "AND r.eventPlace = :eventPlace")
    List<Hall> findHall(@Param("name") String name, @Param("eventPlace") EventPlace eventPlace, Pageable pageable);

    /**
     * Finds Hall that contains name (LIKE) and has eventPlace (equals).
     *
     * @param name of the hall to search for
     * @return List of corresponding halls
     * @throws javax.persistence.PersistenceException when unknown error occurs
     */
    @Query("SELECT r FROM Hall r WHERE :name is null OR :name='' OR UPPER(r.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    List<Hall> findHall(@Param("name") String name, Pageable pageable);

    /**
     * Gets Hall by primary key (id).
     *
     * @param id of the hall
     * @return corresponding hall
     * @throws javax.persistence.PersistenceException when unknown error occurs
     */
    Hall getById(Long id);

    /**
     * Persists hall.
     *
     * @param hall to be persisted
     * @return persisted hall
     */
    Hall save(Hall hall);

    /**
     * Returns all halls from the database without any filter.
     *
     * @param pageable of the hall
     * @return all halls.
     */
    List<Hall> getAllBy(Pageable pageable);
}
