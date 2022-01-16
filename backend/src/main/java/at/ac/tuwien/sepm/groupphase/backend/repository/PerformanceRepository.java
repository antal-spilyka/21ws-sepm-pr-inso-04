package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long>, JpaSpecificationExecutor<Performance> {
    /**
     * Finds all the performances in ascending order sorted by id.
     *
     * @return all matching performances.
     */
    List<Performance> findByOrderByIdAsc();

    /**
     * Finds all the performances which suit the criteria from parameters.
     *
     * @param dateTimeFrom which is not more than 30 minutes before the performance
     * @param dateTimeTill which is not more than 30 minutes after the performance
     * @param eventName    of the performance
     * @param hall         of the performance
     * @param price        of the performance
     * @param pageable     of the event
     * @return all matching performances.
     */
    @Query("SELECT distinct p FROM Performance p, IN(p.hall.sectors) sec  WHERE (p.startTime <= :dateTimeTill AND p.startTime >= :dateTimeFrom) " +
        "AND (:hall is null OR :hall='' OR UPPER(p.hall.name) LIKE UPPER(CONCAT( '%', :hall, '%'))) " +
        "AND (:eventName is null OR :eventName='' OR UPPER(p.event.name) LIKE UPPER(CONCAT( '%', :eventName, '%')))" +
        "AND (:price is null OR (:price+10 >= sec.price AND :price-10 <= sec.price))")
    List<Performance> findPerformanceByDateTime(@Param("dateTimeFrom") LocalDateTime dateTimeFrom,
                                                @Param("dateTimeTill") LocalDateTime dateTimeTill,
                                                @Param("eventName") String eventName, @Param("hall") String hall,
                                                @Param("price") Integer price,
                                                Pageable pageable);

    /**
     * Finds all the performances which suit the criteria from parameters.
     *
     * @param eventName of the performance
     * @param hall      of the performance
     * @param price     of the performance
     * @param pageable  of the event
     * @return all matching performances.
     */
    @Query("SELECT distinct p FROM Performance p, IN(p.hall.sectors) sec  WHERE (:hall is null OR :hall='' OR " +
        "UPPER(p.hall.name) LIKE UPPER(CONCAT( '%', :hall, '%'))) " +
        "AND (:eventName is null OR :eventName='' OR UPPER(p.event.name) LIKE UPPER(CONCAT( '%', :eventName, '%')))" +
        "AND (:price is null OR (:price+10 >= sec.price AND :price-10 <= sec.price))")
    List<Performance> findPerformanceByEventAndHall(@Param("eventName") String eventName, @Param("hall") String hall,
                                                    @Param("price") Integer price, Pageable pageable);

    /**
     * Finds all the performances which suit the criteria from parameters.
     *
     * @param searchQuery of the performance which is like event name or hall name
     * @param pageable    of the event
     * @return all matching performances.
     */
    @Query("SELECT p FROM Performance p WHERE :searchQuery is null OR :searchQuery='' OR UPPER(p.hall.name) " +
        "LIKE UPPER(CONCAT( '%', :searchQuery, '%')) OR UPPER(p.event.name) LIKE UPPER(CONCAT( '%', :searchQuery, '%'))")
    List<Performance> findGeneralPerformanceByEventAndHall(@Param("searchQuery") String searchQuery, Pageable pageable);

    /**
     * Finds all the performances for the artist.
     *
     * @param id       of the artist performance
     * @param pageable of the event
     * @return all matching performances.
     */
    @Query("SELECT p FROM Performance p WHERE p.artist.id=:id")
    List<Performance> findPerformanceForArtist(@Param("id") Long id, Pageable pageable);

    /**
     * Returns whether a performance exists with a specified name in a specified event.
     *
     * @param name  of the performance
     * @param event of the performance
     * @return true if performance exists, false otherwise.
     */
    @Query("SELECT CASE WHEN count(p)> 0 THEN true ELSE false END FROM Performance p WHERE p.name = :name AND p.event = :event")
    Boolean existsByNameAndEvent(@Param("name") String name, @Param("event") Event event);
}