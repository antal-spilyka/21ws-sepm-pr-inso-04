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

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long>, JpaSpecificationExecutor<Performance> {
    /**
     * Finds all performances in a given order.
     *
     * @return list of all performances in ascending order.
     */
    List<Performance> findByOrderByIdAsc();

    /**
     * Finds all performances by the given date time.
     *
     * @param dateTimeFrom start time of the performance.
     * @param dateTimeTill end time of the performance.
     * @param eventName name of the event.
     * @param hall name of the hall.
     * @param price price of the performance.
     * @param pageable number of pages.
     * @return list of all found performances.
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
     * Finds all the performances by the given event and hall.
     *
     * @param eventName name of the event.
     * @param hall name of the hall.
     * @param price price of the performance.
     * @param pageable number of pages.
     * @return list of all found performances.
     */
    @Query("SELECT distinct p FROM Performance p, IN(p.hall.sectors) sec  WHERE (:hall is null OR :hall='' OR " +
        "UPPER(p.hall.name) LIKE UPPER(CONCAT( '%', :hall, '%'))) " +
        "AND (:eventName is null OR :eventName='' OR UPPER(p.event.name) LIKE UPPER(CONCAT( '%', :eventName, '%')))" +
        "AND (:price is null OR (:price+10 >= sec.price AND :price-10 <= sec.price))")
    List<Performance> findPerformanceByEventAndHall(@Param("eventName") String eventName, @Param("hall") String hall,
                                                    @Param("price") Integer price, Pageable pageable);

    /**
     * Finds performances by a general search query.
     *
     * @param searchQuery to search for performances.
     * @param pageable number of pages.
     * @return list of all found performances.
     */
    @Query("SELECT p FROM Performance p WHERE :searchQuery is null OR :searchQuery='' OR UPPER(p.hall.name) " +
        "LIKE UPPER(CONCAT( '%', :searchQuery, '%')) OR UPPER(p.event.name) LIKE UPPER(CONCAT( '%', :searchQuery, '%'))")
    List<Performance> findGeneralPerformanceByEventAndHall(@Param("searchQuery") String searchQuery, Pageable pageable);

    /**
     * Finds performances for a certain artist.
     *
     * @param id of the artist.
     * @param pageable number of pages.
     * @return list of all found performances.
     */
    @Query("SELECT p FROM Performance p WHERE p.artist.id=:id")
    List<Performance> findPerformanceForArtist(@Param("id") Long id, Pageable pageable);

    /**
     * Checks if a performance exists with the given name and event.
     *
     * @param name of the performance to find.
     * @param event assigned to the performance.
     * @return boolean value indicating whether the performance was found or not.
     */
    @Query("SELECT CASE WHEN count(p)> 0 THEN true ELSE false END FROM Performance p WHERE p.name = :name AND p.event = :event")
    Boolean existsByNameAndEvent(@Param("name") String name, @Param("event") Event event);
}