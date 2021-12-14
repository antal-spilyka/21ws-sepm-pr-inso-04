package at.ac.tuwien.sepm.groupphase.backend.unittests;

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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EventPlaceServiceTest {

    @Autowired
    private EventPlaceService eventPlaceService;

    @Autowired
    private EventPlaceMapper eventPlaceMapper;

    @Test()
    public void missing_addressDto() {
        EventPlaceDto eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("TestEventPlace1");
        assertThrows(ContextException.class, () -> eventPlaceService.save(eventPlaceDto));
    }

    @Test
    public void insert_eventPlace_and_search() {
        Address address = new Address();
        address.setZip("1234");
        address.setState("TestState");
        address.setCountry("TestCountry");
        address.setCity("TestCity");
        address.setStreet("TestStreet");

        EventPlace eventPlace = new EventPlace();
        eventPlace.setName("TestEventPlace2");
        eventPlace.setAddress(address);

        EventPlace eventPlacePers = eventPlaceService.save(eventPlaceMapper.entityToDto(eventPlace));
        eventPlace.getAddress().setId(eventPlacePers.getAddress().getId());
        eventPlace.setId(eventPlacePers.getId());
        assertEquals(eventPlacePers, eventPlace);
    }

    @Test
    public void search_for_notExisting() {
        EventPlaceSearchDto eventPlaceSearchDto = new EventPlaceSearchDto();
        eventPlaceSearchDto.setName("not existing");
        List<EventPlace> eventPlacesFound = eventPlaceService.findEventPlace(eventPlaceSearchDto);
        assertEquals(eventPlacesFound.size(), 0);
    }

    @Test
    public void search_forLocation_withNoInputs() {
        EventLocationSearchDto eventLocationSearchDto = new EventLocationSearchDto();
        assertEquals(0, eventPlaceService.findEventLocation(eventLocationSearchDto).size());
    }

    @Test
    public void search_forLocation_whichIsPresent() {
        AddressDto addressDto = new AddressDto();
        addressDto.setZip("1234");
        addressDto.setState("SearchState");
        addressDto.setCountry("SearchCountry");
        addressDto.setCity("SearchCity");
        addressDto.setStreet("SearchStreet");

        EventPlaceDto eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("SearchTest");
        eventPlaceDto.setAddressDto(addressDto);

        EventPlace eventPlaceD = eventPlaceService.save(eventPlaceDto);

        EventLocationSearchDto eventLocationSearchDto = new EventLocationSearchDto();
        eventLocationSearchDto.setCity("SearchCity");
        eventLocationSearchDto.setCountry("SearchCountry");
        List<Address> events = eventPlaceService.findEventLocation(eventLocationSearchDto);
        assertEquals(1, events.size());
    }
}
