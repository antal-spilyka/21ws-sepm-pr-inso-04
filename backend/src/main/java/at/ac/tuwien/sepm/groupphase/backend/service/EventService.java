package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.GeneralSearchEventDto;
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
     */
    Event saveEvent(EventDto eventDto);

    /**
     * Finds all events which meet the criteria from dto.
     *
     * @param eventSearchDto dto for storing the search criteria.
     * @return all events that meet the search criteria.
     */
    List<Event> findEvents(EventSearchDto eventSearchDto);

    /**
     * Finds all events which meet the criteria from dto.
     *
     * @param generalSearchEventDto dto for storing the search criteria.
     * @return all events that meet the search criteria.
     */
    List<Event> findGeneralEvents(GeneralSearchEventDto generalSearchEventDto);

    /**
     * Finds all events with the given name.
     *
     * @param name of the events.
     * @return all events that meet the criteria.
     */
    List<Event> findEvent(String name);

    /**
     * Finds the performances of a given event.
     *
     * @param id of the event.
     * @return all performances that meet the criteria.
     */
    Stream<PerformanceDto> getPerformances(Long id);

    /**
     * Finds the performances of a given location id.
     *
     * @param id of the location.
     * @return all performances that meet the criteria.
     */
    Stream<PerformanceDto> getPerformancesByLocation(Long id);

    /**
     * Finds the performances of a specific location.
     *
     * @param id of the location.
     * @return all the performances that meet the criteria.
     */
    Stream<PerformanceDto> getPerformancesByLocation(Long id, Integer page);

    /**
     * Gets all categories from the data store.
     *
     * @return all categories
     */
    List<String> findDistinctByOrderByCategoryAsc();

    /**
     * finds all events with the most tickets sales by category.
     *
     * @param category is the filtering criteria
     * @return a list of events filtered by the criteria
     */
    List<TopTenEventsDto> findByCategoryEquals(String category);
}
