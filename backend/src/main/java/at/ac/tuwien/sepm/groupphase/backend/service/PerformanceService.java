package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.BasketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.GeneralSearchEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Sector;

import java.security.Principal;
import java.util.List;
import java.util.stream.Stream;

public interface PerformanceService {

    /**
     * Persists a performance.
     *
     * @param performanceDto to be saved.
     * @return the persisted Performance.
     */
    Performance save(PerformanceDto performanceDto);

    /**
     * Persists a performance with the corresponding event.
     *
     * @param performanceDto to be saved.
     * @param event          which the performance is assigned to.
     * @return the persisted Perfomance.
     */
    Performance save(PerformanceDto performanceDto, Event event);

    /**
     * Finds all performances by time.
     *
     * @param performanceSearchDto to search for.
     * @return all the performances that meet the search criteria.
     */
    Stream<PerformanceDto> findPerformanceByDateTime(PerformanceSearchDto performanceSearchDto);

    /**
     * Finds all performances by general search.
     *
     * @param generalSearchEventDto to search for.
     * @return all the performances that meet the search criteria.
     */
    Stream<PerformanceDto> findGeneralPerformanceByDateTime(GeneralSearchEventDto generalSearchEventDto);

    /**
     * Finds all performances by performing artist.
     *
     * @param id of the artist to search for.
     * @return all the performances that meet the search criteria.
     */
    Stream<PerformanceDto> findPerformanceForArtist(Long id, Integer page);


    /**
     * Finds the performance by id.
     *
     * @param id of the performance.
     * @return performance with the given id.
     */
    PerformanceDetailDto findPerformanceById(Long id);

    /**
     * Lets users buy seats of a performance.
     *
     * @param basket        of the user.
     * @param performanceId of the performance.
     * @param principal     used in the payment.
     */
    void buySeats(BasketDto basket, Long performanceId, Principal principal);

    List<Sector> testPrice(Integer price);

    /**
     * Lets users reserve seats of a performance.
     *
     * @param basket        of the user.
     * @param performanceId of the performance.
     * @param principal     used in the reservation.
     */
    void reserveSeats(BasketDto basket, Long performanceId, Principal principal);
}
