package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.PaymentInformation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserMappingTest implements TestData {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void givenNothing_whenMapPaymentInformationDtoToEntity_thenEntityHasAllProperties() {
        PaymentInformation paymentInformation = new PaymentInformation();
        paymentInformation.setCreditCardNr("1234123412341234");
        paymentInformation.setCreditCardExpirationDate("202022");
        paymentInformation.setCreditCardCvv("123");
        paymentInformation.setCreditCardName("Test");

        ApplicationUser user = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
            .withEmail("test@email.com")
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
            .withPaymentInformation(paymentInformation)
            .withDisabled(true)
            .build();

        UserDto userDto = userMapper.applicationUserToUserDto(user);
        assertAll(
            () -> assertEquals(user.getEmail(), userDto.getEmail()),
            () -> assertEquals(user.getFirstName(), userDto.getFirstName()),
            () -> assertEquals(user.getLastName(), userDto.getLastName()),
            () -> assertEquals(user.getCity(), userDto.getCity()),
            () -> assertEquals(user.getCountry(), userDto.getCountry()),
            () -> assertEquals(user.getAdmin(), userDto.getAdmin()),
            () -> assertEquals(user.getDisabled(), userDto.getDisabled()),
            () -> assertEquals(user.getZip(), userDto.getZip()),
            () -> assertEquals(user.getStreet(), userDto.getStreet()),
            () -> assertEquals(user.getPaymentInformation().getCreditCardCvv(), userDto.getPaymentInformation().getCreditCardCvv()),
            () -> assertEquals(user.getPaymentInformation().getCreditCardName(), userDto.getPaymentInformation().getCreditCardName()),
            () -> assertEquals(user.getPaymentInformation().getCreditCardExpirationDate(), userDto.getPaymentInformation().getCreditCardExpirationDate()),
            () -> assertEquals(user.getPaymentInformation().getCreditCardNr(), userDto.getPaymentInformation().getCreditCardNr()),
            () -> assertEquals(user.getPhone(), userDto.getPhone()),
            () -> assertEquals(user.getPassword(), userDto.getPassword())
        );
    }
}
