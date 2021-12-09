package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDateTimeSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;

import java.util.List;

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

    /**
     * Finds all event which meet the criteria from dto.
     *
     * @param eventDateTimeSearchDto dto for storing the search criteria.
     * @return all events that meet the search criteria.
     */
    List<Event> findEventsByDateTime(EventDateTimeSearchDto eventDateTimeSearchDto);

    List<Event> findEvent(String name);
}
