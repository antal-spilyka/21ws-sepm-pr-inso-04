package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;

/**
 * Service for working with Event.
 */
public interface EventService {

    /**
     * Persists a new event.
     *
     * @param eventInquiryDto containing properties to be persisted
     * @return persisted event
     * @throws org.hibernate.service.spi.ServiceException when unknown error occurs
     * @throws at.ac.tuwien.sepm.groupphase.backend.exception.ContextException when entity already exists
     */
    EventDto createEvent(EventInquiryDto eventInquiryDto);
}
