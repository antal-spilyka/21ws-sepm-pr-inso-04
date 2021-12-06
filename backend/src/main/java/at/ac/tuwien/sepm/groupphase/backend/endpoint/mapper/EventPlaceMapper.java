package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class EventPlaceMapper {

    AddressMapper addressMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public EventPlaceMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public EventPlaceDto entityToDto(EventPlace eventPlace) {
        LOGGER.trace("Mapping {}", eventPlace);
        EventPlaceDto eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setId(eventPlace.getId());
        eventPlaceDto.setName(eventPlace.getName());
        eventPlaceDto.setAddressDto(addressMapper.entityToDto(eventPlace.getAddress()));
        return eventPlaceDto;
    }

    public EventPlace dtoToEntity(EventPlaceDto eventPlaceDto) {
        LOGGER.trace("Mapping {}", eventPlaceDto);
        EventPlace eventPlace = new EventPlace();
        eventPlace.setId(eventPlaceDto.getId());
        eventPlace.setName(eventPlaceDto.getName());
        eventPlace.setAddress(addressMapper.dtoToEntity(eventPlaceDto.getAddressDto()));
        return eventPlace;
    }

    public EventPlace dtoToEntity(EventPlaceDto eventPlaceDto, Address address) {
        LOGGER.trace("Mapping {}", eventPlaceDto);
        EventPlace eventPlace = new EventPlace();
        eventPlace.setId(eventPlace.getId());
        eventPlace.setName(eventPlaceDto.getName());
        eventPlace.setAddress(address);
        return eventPlace;
    }
}
