package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventLocationSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;

import javax.transaction.Transactional;
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
     */
    List<Address> findEventLocation(EventLocationSearchDto eventLocationSearchDto);


    /**
     * Persists category.
     *
     * @param eventPlaceDto to be persisted
     * @return persisted eventPlace
     */
    EventPlace save(EventPlaceDto eventPlaceDto);
}
