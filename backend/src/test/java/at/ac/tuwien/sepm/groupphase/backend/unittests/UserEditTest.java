package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserEditDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
// This test slice annotation is used instead of @SpringBootTest to load only repository beans instead of
// the entire application context
@SpringBootTest
@ActiveProfiles("test")
public class UserEditTest implements TestData {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /*@BeforeAll
    public void insertNeededContext() {
        AddressDto addressDto = new AddressDto();
        addressDto.setZip(1234);
        addressDto.setState("TestState");
        addressDto.setCountry("TestCountry");
        addressDto.setCity("TestCity");

        EventPlaceDto eventPlaceDto = new EventPlaceDto();
        eventPlaceDto.setName("TestPlace");
        eventPlaceDto.setAddressDto(addressDto);
        EventPlace eventPlace = eventPlaceMapper.dtoToEntity(eventPlaceService.save(eventPlaceDto));

        RoomInquiryDto roomInquiryDto = new RoomInquiryDto();
        roomInquiryDto.setName("TestRoom");
        roomInquiryDto.setEventPlaceName(eventPlace.getName());
        roomDto = roomService.save(roomInquiryDto);

        ArtistDto artistDto = new ArtistDto();
        artistDto.setBandName("TestArtist");
        artistDto.setDescription("an artist");
        this.artistDto = artistService.save(artistDto);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("testCategory");
        this.categoryDto = categoryService.save(categoryDto);
    }*/


    @Test
    public void updateValidUser () {
        ApplicationUser user = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
            .withEmail("hallo@test.com")
            .withPassword("testPassword")
            .withAdmin(false)
            .withFirstName("test")
            .withLastName("person")
            .withSalutation("mr")
            .withPhone("+430101011010")
            .withCountry("Austria")
            .withCity("Vienna")
            .withStreet("Test Street")
            .withZip("12345")
            .withDisabled(true)
            .build();
            userRepository.save(user);

            UserEditDto toUpdate = UserEditDto.UserEditDtoBuilder.aUserDto()
                .withEmail("hallo@test.com")
                .withPassword("testPassword")
                .withFirstName("firstName")
                .withLastName("person")
                .withSalutation("mr")
                .withPhone("+430101011010")
                .withCountry("Austria")
                .withCity("Test City")
                .withStreet("Test Street")
                .withDisabled(true)
                .withZip("12345")
                .build();
            userService.updateUser(toUpdate);

            assertEquals(userService.findApplicationUserByEmail("hallo@test.com").getCity(), toUpdate.getCity());  //edited
            assertEquals(userService.findApplicationUserByEmail("hallo@test.com").getLastName(), toUpdate.getLastName());  //not edited

    }

}
