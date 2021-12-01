package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaymentInformationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserEditDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserRegisterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.PaymentInformation;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
// This test slice annotation is used instead of @SpringBootTest to load only repository beans instead of
// the entire application context
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
public class UserEditTest implements TestData {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeAll
    public void insertNeededContext() {
        UserRegisterDto user1 = UserRegisterDto.UserRegisterDtoBuilder.aUserRegisterDto()
            .withEmail("hallo@test.com")
            .withPassword("testPassword")
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
        userService.createUser(user1);

        UserRegisterDto user2 = UserRegisterDto.UserRegisterDtoBuilder.aUserRegisterDto()
            .withEmail("test@test.com")
            .withPassword("testPassword")
            .withFirstName("test2")
            .withLastName("person2")
            .withSalutation("mr")
            .withPhone("+430101011010")
            .withCountry("USA")
            .withCity("Vienna")
            .withStreet("Test Street")
            .withZip("12345")
            .withDisabled(true)
            .build();
        userService.createUser(user2);
    }


    @Test
    public void updateValidUser () {
        PaymentInformationDto paymentInformation = new PaymentInformationDto();
        paymentInformation.setCreditCardNr("1234123412341234");
        paymentInformation.setCreditCardExpirationDate("202022");
        paymentInformation.setCreditCardCvv("123");
        paymentInformation.setCreditCardName("Test");

        UserEditDto toUpdate = UserEditDto.UserEditDtoBuilder.aUserDto()
            .withEmail("hallo@test.com")
            .withNewEmail("hallo@test.com")
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
            .withPaymentInformation(paymentInformation)
            .build();
        userService.updateUser(toUpdate);

        assertEquals(userService.findApplicationUserByEmail("hallo@test.com").getCity(), toUpdate.getCity());  //edited
        assertEquals(userService.findApplicationUserByEmail("hallo@test.com").getLastName(), toUpdate.getLastName());  //not edited
    }

    @Test
    public void updateUserWithExistingEmail () {
        UserEditDto toUpdate = UserEditDto.UserEditDtoBuilder.aUserDto()
            .withEmail("hallo@test.com")
            .withNewEmail("test@test.com")
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

        assertThrows(ServiceException.class, () ->  userService.updateUser(toUpdate));
    }

    @Test
    public void updateNotExistingUser () {
        UserEditDto toUpdate = UserEditDto.UserEditDtoBuilder.aUserDto()
            .withEmail("notExitsing@hallo.com")
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

        assertThrows(ServiceException.class, () ->  userService.updateUser(toUpdate));
    }

    @Test
    public void updateUserWithMissingData () {
        UserEditDto toUpdate = UserEditDto.UserEditDtoBuilder.aUserDto()
            .withEmail("hallo@test.com")
            .withPassword("testPassword")
            .withSalutation("mr")
            .withPhone("+430101011010")
            .withCountry("Austria")
            .withCity("Test City")
            .withStreet("Test Street")
            .withDisabled(true)
            .withZip("12345")
            .build();

        assertThrows(DataIntegrityViolationException.class, () -> userService.updateUser(toUpdate));
    }
}
