package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventLocationSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallAddDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.AddressMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventPlaceMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.HallMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.HallplanElementMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.SectorMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallplanElement;
import at.ac.tuwien.sepm.groupphase.backend.entity.Sector;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.repository.AddressRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventPlaceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallplanElementRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.SectorRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventPlaceService;
import org.hibernate.service.spi.ServiceException;
import org.hibernate.tool.hbm2ddl.UniqueConstraintSchemaUpdateStrategy;
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
    private SectorRepository sectorRepository;
    HallRepository hallRepository;
    private HallplanElementRepository hallplanElementRepository;
    HallMapper hallMapper;
    private SectorMapper sectorMapper;
    private HallplanElementMapper hallplanElementMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public EventPlaceServiceImpl(EventPlaceRepository eventPlaceRepository, EventPlaceMapper eventPlaceMapper,
                                 AddressMapper addressMapper, AddressRepository addressRepository, SectorRepository sectorRepository,
                                 HallRepository hallRepository, HallplanElementRepository hallplanElementRepository, HallMapper hallMapper,
                                 SectorMapper sectorMapper, HallplanElementMapper hallplanElementMapper) {
        this.eventPlaceRepository = eventPlaceRepository;
        this.eventPlaceMapper = eventPlaceMapper;
        this.addressMapper = addressMapper;
        this.addressRepository = addressRepository;
        this.sectorRepository = sectorRepository;
        this.hallRepository = hallRepository;
        this.hallplanElementRepository = hallplanElementRepository;
        this.hallMapper = hallMapper;
        this.sectorMapper = sectorMapper;
        this.hallplanElementMapper = hallplanElementMapper;
    }

    @Transactional
    @Override
    public List<EventPlaceDto> findEventPlace(EventPlaceSearchDto eventPlaceSearchDto) {
        LOGGER.debug("Handling in Service {}", eventPlaceSearchDto);
        try {
            List<EventPlace> eventPlaces = eventPlaceRepository.findEventPlace(eventPlaceSearchDto.getName(), PageRequest.of(0, 2));
            return eventPlaces.stream().map(eventPlace -> eventPlaceMapper.entityToDto(eventPlace)).collect(Collectors.toList());
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<EventPlace> findEventLocation(EventLocationSearchDto eventLocationSearchDto) {
        LOGGER.debug("Handling in Service {}", eventLocationSearchDto);
        try {
            List<Address> addresses = addressRepository.findEventLocation(eventLocationSearchDto.getCity(),
                eventLocationSearchDto.getState(), eventLocationSearchDto.getCountry(),
                eventLocationSearchDto.getStreet(), eventLocationSearchDto.getZip(), PageRequest.of(0, 10));

            List<EventPlace> eventPlaces = new ArrayList<>();
            for (Address address : addresses) {
                if (address != null) {
                    List<EventPlace> found = eventPlaceRepository.findEventPlaceByAddress(address);
                    eventPlaces.addAll(found);
                }
            }
            return eventPlaces;
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

    @Override
    public void addHall(String eventPlaceId, HallAddDto hallAddDto) {
        LOGGER.debug("Handling in Service {}", hallAddDto);
        try {
            EventPlace eventPlace = eventPlaceRepository.findByIdEquals(Long.parseLong(eventPlaceId));
            if (eventPlace == null) {
                throw new ServiceException("Event Place not found");
            }

            List<Sector> sectors = sectorMapper.dtoToEntity(hallAddDto.getSectors());
            sectorRepository.saveAll(sectors);

            List<HallplanElement> rows = hallplanElementMapper.dtoToEntity(hallAddDto.getRows(), sectors);
            hallplanElementRepository.saveAll(rows);
            hallRepository.save(hallMapper.dtoToEntity(hallAddDto, eventPlace, rows, sectors));
        } catch (EntityExistsException e) {
            throw new ContextException(e);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Address findAddress(Long id) {
        EventPlace eventPlace = eventPlaceRepository.findByIdEquals(id);
        if (eventPlace == null) {
            throw new ServiceException("Event place not found");
        }

        Address address = eventPlace.getAddress();
        if (address == null) {
            throw new ServiceException("Address not found");
        }

        return address;
    }
}
