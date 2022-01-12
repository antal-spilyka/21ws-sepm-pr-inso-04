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
    @Query("SELECT distinct p FROM Performance p, IN(p.hall.sectors) sec  WHERE (p.startTime <= :dateTimeTill AND p.startTime >= :dateTimeFrom) " +
        "AND (:hall is null OR :hall='' OR UPPER(p.hall.name) LIKE UPPER(CONCAT( '%', :hall, '%'))) " +
        "AND (:eventName is null OR :eventName='' OR UPPER(p.event.name) LIKE UPPER(CONCAT( '%', :eventName, '%')))" +
        "AND (:price is null OR (:price+10 >= sec.price AND :price-10 <= sec.price))")
    List<Performance> findPerformanceByDateTime(@Param("dateTimeFrom") LocalDateTime dateTimeFrom,
                                                @Param("dateTimeTill") LocalDateTime dateTimeTill,
                                                @Param("eventName") String eventName, @Param("hall") String hall,
                                                @Param("price") Integer price,
                                                Pageable pageable);


    @Query("SELECT distinct p FROM Performance p, IN(p.hall.sectors) sec  WHERE (:hall is null OR :hall='' OR " +
        "UPPER(p.hall.name) LIKE UPPER(CONCAT( '%', :hall, '%'))) " +
        "AND (:eventName is null OR :eventName='' OR UPPER(p.event.name) LIKE UPPER(CONCAT( '%', :eventName, '%')))" +
        "AND (:price is null OR (:price+10 >= sec.price AND :price-10 <= sec.price))")
    /*@Query("SELECT distinct p FROM Performance p JOIN Sector s ON sec IN(SELECT h.sectors FROM Hall h WHERE h=p.hall) WHERE (:hall is null OR :hall='' OR " +
        "UPPER(p.hall.name) LIKE UPPER(CONCAT( '%', :hall, '%'))) " +
        "AND (:eventName is null OR :eventName='' OR UPPER(p.event.name) LIKE UPPER(CONCAT( '%', :eventName, '%')))" +
        "AND (:price is null OR (:price+10 >= (SELECT sec.price FROM Hall h, IN(h.sectors) sec) AND :price-10 <= sec.price))")*/
    List<Performance> findPerformanceByEventAndHall(@Param("eventName") String eventName, @Param("hall") String hall,
                                                    @Param("price") Integer price, Pageable pageable);

    @Query("SELECT p FROM Performance p WHERE :searchQuery is null OR :searchQuery='' OR UPPER(p.hall.name) " +
        "LIKE UPPER(CONCAT( '%', :searchQuery, '%')) OR UPPER(p.event.name) LIKE UPPER(CONCAT( '%', :searchQuery, '%'))")
    List<Performance> findGeneralPerformanceByEventAndHall(@Param("searchQuery") String searchQuery, Pageable pageable);

    @Query("SELECT p FROM Performance p WHERE p.artist.id=:id")
    List<Performance> findPerformanceForArtist(@Param("id") Long id, Pageable pageable);

    @Query("SELECT CASE WHEN count(p)> 0 THEN true ELSE false END FROM Performance p WHERE p.name = :name AND p.event = :event")
    Boolean existsByNameAndEvent(@Param("name") String name, @Param("event") Event event);

}