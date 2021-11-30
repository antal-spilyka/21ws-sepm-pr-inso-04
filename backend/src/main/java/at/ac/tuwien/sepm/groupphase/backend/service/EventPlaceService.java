package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Category;

import java.util.List;

/**
 * Service for working with EventPlace.
 */
public interface EventPlaceService {

    /**
     * Finds eventPlace with matching properties.
     *
     * @param eventPlaceSearchDto containing properties to be searched for
     * @return List of eventPlaces matching properties
     * @throws org.hibernate.service.spi.ServiceException when unknown error occurs
     */
    List<EventPlaceDto> findEventPlace(EventPlaceSearchDto eventPlaceSearchDto);

    /**
     * Persists category.
     *
     * @param eventPlaceDto to be persisted
     * @return persisted eventPlace
     */
    EventPlaceDto save(EventPlaceDto eventPlaceDto);
}
