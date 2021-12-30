package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventLocationSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallAddDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;

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
     * Finds eventLocations with matching properties.
     *
     * @param eventLocationSearchDto dto for storing the search information.
     * @return a list with all the addresses that meet search criteria.
     * @throws org.hibernate.service.spi.ServiceException when unknown error occurs
     */
    List<EventPlace> findEventLocation(EventLocationSearchDto eventLocationSearchDto);


    /**
     * Persists category.
     *
     * @param eventPlaceDto to be persisted
     * @return persisted eventPlace
     * @throws javax.persistence.EntityExistsException when entity already exists
     * @throws org.hibernate.service.spi.ServiceException when unknown error occurs
     */
    EventPlace save(EventPlaceDto eventPlaceDto);

    /**
     * Adds a hall to an event place.
     *
     * @param eventPlaceId the event place to which the hall is added
     * @param hallAddDto to be persisted
     * @throws org.hibernate.service.spi.ServiceException if the eventPLace is not found
     */
    void addHall(String eventPlaceId, HallAddDto hallAddDto);

    /**
     * Finds the address of the eventPlace with the given id.
     *
     * @param id of the eventPlace
     * @return the Address object
     * @throws org.hibernate.service.spi.ServiceException if the eventPlace or the address is not found
     */
    Address findAddress(Long id);
}
