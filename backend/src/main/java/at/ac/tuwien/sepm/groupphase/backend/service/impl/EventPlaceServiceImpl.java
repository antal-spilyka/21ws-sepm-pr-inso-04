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
import java.util.ArrayList;
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
    public List<EventPlace> findEventPlace(EventPlaceSearchDto eventPlaceSearchDto) {
        LOGGER.debug("Handling in Service {}", eventPlaceSearchDto);
        try {
            return eventPlaceRepository.findEventPlace(eventPlaceSearchDto.getName(), PageRequest.of(0, 2));
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public EventPlace findEventPlace(EventPlaceDto eventPlace) {
        LOGGER.debug("Handling in Service {}", eventPlace);
        try {
            Address address;
            if (eventPlace.getId() != null) {
                address = addressRepository.getById(eventPlace.getAddressDto().getId());
            } else {
                address = addressRepository.save(addressMapper.dtoToEntity(eventPlace.getAddressDto()));
            }
            eventPlace.setAddressDto(addressMapper.entityToDto(address));
            EventPlace newEventPlace = eventPlaceRepository.findByIdEquals(eventPlace.getId());
            if (newEventPlace == null) {
                newEventPlace = eventPlaceRepository.save(eventPlaceMapper.dtoToEntity(eventPlace));
            }
            return newEventPlace;
        } catch (EntityExistsException e) {
            throw new ContextException(e);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Address> findEventLocation(EventLocationSearchDto eventLocationSearchDto) {
        LOGGER.debug("Handling in Service {}", eventLocationSearchDto);
        if (eventLocationSearchDto.getZip() == null && eventLocationSearchDto.getStreet() == null
            && eventLocationSearchDto.getCountry() == null && eventLocationSearchDto.getState() == null
            && eventLocationSearchDto.getCity() == null) {
            return new ArrayList<Address>();
        }
        try {
            List<Address> addresses = addressRepository.findEventLocation(eventLocationSearchDto.getCity(),
                eventLocationSearchDto.getState(), eventLocationSearchDto.getCountry(),
                eventLocationSearchDto.getStreet(), eventLocationSearchDto.getZip(), PageRequest.of(0, 10));
            if (addresses.isEmpty()) {
                throw new NotFoundException("No address was found for this query");
            }
            return addresses;
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public EventPlace save(EventPlaceDto eventPlaceDto) {
        LOGGER.debug("Handling in Service {}", eventPlaceDto);
        try {
            AddressDto addressDto = eventPlaceDto.getAddressDto();
            if (addressDto == null) {
                throw new ContextException("Address invalid");
            }
            Address address = addressRepository.save(addressMapper.dtoToEntity(addressDto));
            return eventPlaceRepository.save(eventPlaceMapper.dtoToEntity(eventPlaceDto, address));
        } catch (EntityExistsException e) {
            throw new ContextException(e);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
