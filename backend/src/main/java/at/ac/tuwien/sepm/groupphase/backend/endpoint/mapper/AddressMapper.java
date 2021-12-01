package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class AddressMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public AddressDto entityToDto(Address address) {
        LOGGER.trace("Mapping {}", address);
        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setCity(address.getCity());
        addressDto.setCountry(address.getCountry());
        addressDto.setState(address.getState());
        addressDto.setZip(address.getZip());
        addressDto.setDescription(address.getDescription());
        addressDto.setStreet(address.getStreet());
        return addressDto;
    }

    public Address dtoToEntity(AddressDto addressDto) {
        LOGGER.trace("Mapping {}", addressDto);
        Address address = new Address();
        address.setId(addressDto.getId());
        address.setCity(addressDto.getCity());
        address.setCountry(addressDto.getCountry());
        address.setState(addressDto.getState());
        address.setZip(addressDto.getZip());
        address.setDescription(address.getDescription());
        address.setStreet(address.getStreet());
        return address;
    }
}
