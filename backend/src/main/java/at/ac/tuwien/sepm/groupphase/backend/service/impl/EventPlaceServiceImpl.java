package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.AddressMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventPlaceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
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

    @Transactional
    @Override
    public EventPlaceDto save(EventPlaceDto eventPlaceDto) {
        LOGGER.debug("Handeling in Service {}", eventPlaceDto);
        try {
            Address address = addressRepository.save(addressMapper.dtoToEntity(eventPlaceDto.getAddressDto()));
            EventPlace persistedEventPlace = eventPlaceRepository.save(eventPlaceMapper.dtoToEntity(eventPlaceDto, address));
            return eventPlaceMapper.entityToDto(persistedEventPlace);
        } catch (EntityExistsException e) {
            throw new ContextException(e);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
