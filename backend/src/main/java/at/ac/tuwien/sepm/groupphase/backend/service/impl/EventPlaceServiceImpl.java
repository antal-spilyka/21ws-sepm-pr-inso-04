package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventLocationSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.AddressMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventPlaceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.AddressRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventPlaceRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventPlaceService;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventPlaceServiceImpl implements EventPlaceService {

    EventPlaceRepository eventPlaceRepository;
    EventPlaceMapper eventPlaceMapper;
    AddressMapper addressMapper;
    AddressRepository addressRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public EventPlaceServiceImpl(EventPlaceRepository eventPlaceRepository, EventPlaceMapper eventPlaceMapper, AddressMapper addressMapper, AddressRepository addressRepository) {
        this.eventPlaceRepository = eventPlaceRepository;
        this.eventPlaceMapper = eventPlaceMapper;
        this.addressMapper = addressMapper;
        this.addressRepository = addressRepository;
    }

    @Transactional
    @Override
    public List<EventPlaceDto> findEventPlace(EventPlaceSearchDto eventPlaceSearchDto) {
        LOGGER.debug("Handeling in Service {}", eventPlaceSearchDto);
        try {
            List<EventPlace> eventPlaces = eventPlaceRepository.findEventPlace(eventPlaceSearchDto.getName(), PageRequest.of(0, 2));
            return eventPlaces.stream().map(eventPlace ->
                eventPlaceMapper.entityToDto(eventPlace)
            ).collect(Collectors.toList());
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<AddressDto> findEventLocation(EventLocationSearchDto eventLocationSearchDto) {
        LOGGER.debug("Handeling in Service {}", eventLocationSearchDto);
        if (eventLocationSearchDto.getZip() == null && eventLocationSearchDto.getStreet() == null
            && eventLocationSearchDto.getCountry() == null && eventLocationSearchDto.getDescription() == null
            && eventLocationSearchDto.getState() == null && eventLocationSearchDto.getCity() == null) {
            throw new NotFoundException("No address was found for this query");
        }
        try {
            List<Address> addresses = addressRepository.findEventLocation(eventLocationSearchDto.getCity(),
                eventLocationSearchDto.getState(), eventLocationSearchDto.getCountry(), eventLocationSearchDto.getDescription(),
                eventLocationSearchDto.getStreet(), eventLocationSearchDto.getZip(), PageRequest.of(0, 10));
            if (addresses.isEmpty()) {
                throw new NotFoundException("No address was found for this query");
            }
            return addresses.stream().map(eventLocation ->
                addressMapper.entityToDto(eventLocation)
            ).collect(Collectors.toList());
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public EventPlaceDto save(EventPlaceDto eventPlaceDto) {
        LOGGER.debug("Handeling in Service {}", eventPlaceDto);
        try {
            if (eventPlaceRepository.existsById(eventPlaceDto.getName())) {
                throw new ContextException("Event with same name already exists.");
            }
            AddressDto addressDto = eventPlaceDto.getAddressDto();
            if (addressDto == null) {
                throw new ContextException("Address invalid");
            }
            Address address = addressRepository.save(addressMapper.dtoToEntity(addressDto));
            EventPlace persistedEventPlace = eventPlaceRepository.save(eventPlaceMapper.dtoToEntity(eventPlaceDto, address));
            return eventPlaceMapper.entityToDto(persistedEventPlace);
        } catch (EntityExistsException e) {
            throw new ContextException(e);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
