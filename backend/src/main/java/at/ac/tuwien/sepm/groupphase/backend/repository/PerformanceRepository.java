package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface PerformanceRepository extends JpaRepository<Performance, Long>, JpaSpecificationExecutor<Performance> {
    /*@Query("SELECT distinct p FROM Performance p INNER JOIN Hall h ON (:hall is NULL  OR :hall='' OR upper(h.name) LIKE " +
        "UPPER(CONCAT( '%', :hall, '%')) ) INNER JOIN Event e ON (:eventName  is null OR :eventName='' OR UPPER(e.name) " +
        "LIKE UPPER(CONCAT( '%', :eventName, '%'))) WHERE ((:dateTimeFrom is null AND :dateTimeTill is null) OR " +
        "(p.startTime <= :dateTimeTill AND p.startTime >= :dateTimeFrom))")*/
    @Query("SELECT distinct p FROM Performance p  WHERE (p.startTime <= :dateTimeTill AND p.startTime >= :dateTimeFrom) " +
        "AND (:hall is null OR :hall='' OR UPPER(p.hall.name) LIKE UPPER(CONCAT( '%', :hall, '%'))) " +
        "AND (:eventName is null OR :eventName='' OR UPPER(p.event.name) LIKE UPPER(CONCAT( '%', :eventName, '%')))")
    List<Performance> findPerformanceByDateTime(@Param("dateTimeFrom") LocalDateTime dateTimeFrom,
                                                @Param("dateTimeTill") LocalDateTime dateTimeTill,
                                                @Param("eventName") String eventName, @Param("hall") String hall,
                                                Pageable pageable);

    @Query("SELECT distinct p FROM Performance p  WHERE (:hall is null OR :hall='' OR " +
        "UPPER(p.hall.name) LIKE UPPER(CONCAT( '%', :hall, '%'))) " +
        "AND (:eventName is null OR :eventName='' OR UPPER(p.event.name) LIKE UPPER(CONCAT( '%', :eventName, '%')))")
    List<Performance> findPerformanceByEventAndHall(@Param("eventName") String eventName, @Param("hall") String hall,
                                                   Pageable pageable);

}