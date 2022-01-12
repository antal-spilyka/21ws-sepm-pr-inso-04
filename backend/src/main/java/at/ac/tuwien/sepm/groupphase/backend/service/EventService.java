package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TopTenEventsDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.stream.Stream;

/**
 * Service for working with Event.
 */
public interface EventService {

    /**
     * Persists a new event.
     *
     * @param eventDto containing properties to be persisted
     * @return persisted event
     * @throws org.hibernate.service.spi.ServiceException                      when unknown error occurs
     * @throws at.ac.tuwien.sepm.groupphase.backend.exception.ContextException when entity already exists
     */
    Event saveEvent(EventDto eventDto);

    /**
     * Finds all event which meet the criteria from dto.
     *
     * @param eventSearchDto dto for storing the search criteria.
     * @return all events that meet the search criteria.
     */
    List<Event> findEvents(EventSearchDto eventSearchDto);

    List<Event> findEvent(String name);

    Stream<PerformanceDto> getPerformances(Long id);

    Stream<PerformanceDto> getPerformancesByLocation(Long id);

    List<String> findDistinctByOrderByCategoryAsc();

    List<TopTenEventsDto> findByCategoryEquals(String category);
}
