package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventPlaceSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RoomInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.service.EventPlaceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EventPlaceServiceTest {

    @Autowired
    private EventPlaceService eventPlaceService;

    @Test()
    public void missing_addressDto() {
        EventPlaceDto eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("TestEventPlace");
        assertThrows(ContextException.class, () -> eventPlaceService.save(eventPlaceDto));
    }

    @Test
    public void insert_eventPlace_and_search() {
        AddressDto addressDto = new AddressDto();
        addressDto.setZip(1234);
        addressDto.setState("TestState");
        addressDto.setCountry("TestCountry");
        addressDto.setCity("TestCity");

        EventPlaceDto eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("TestEventPlace");
        eventPlaceDto.setAddressDto(addressDto);

        EventPlaceDto eventPlaceDtoPers = eventPlaceService.save(eventPlaceDto);
        eventPlaceDto.getAddressDto().setId(eventPlaceDtoPers.getAddressDto().getId());
        assertEquals(eventPlaceDtoPers, eventPlaceDto);
    }

    @Test
    public void search_for_notExisting() {
        EventPlaceSearchDto eventPlaceSearchDto = new EventPlaceSearchDto();
        eventPlaceSearchDto.setName("not existing");
        List<EventPlaceDto> eventPlacesFound = eventPlaceService.findEventPlace(eventPlaceSearchDto);
        assertEquals(eventPlacesFound.size(), 0);
    }
}
