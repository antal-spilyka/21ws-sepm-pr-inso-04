package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaymentInformationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserEditDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserRegisterDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.exception.ConflictException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallplanElementRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.OrderRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PaymentInformationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.SeenNewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.hibernate.annotations.Cascade;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest implements TestData {

    @Autowired
    UserService userService;

    @Autowired
    private PaymentInformationRepository paymentInformationRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeenNewsRepository seenNewsRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private HallplanElementRepository hallplanElementRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventPlaceRepository eventPlaceRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private OrderValidationRepository orderValidationRepository;

    @BeforeAll
    public void beforeAll() {
        seenNewsRepository.deleteAll();
        pictureRepository.deleteAll();
        ticketRepository.deleteAll();
        //test
        orderValidationRepository.deleteAll();
        orderRepository.deleteAll();
        performanceRepository.deleteAll();
        artistRepository.deleteAll();
        hallRepository.deleteAll();
        hallplanElementRepository.deleteAll();
        sectorRepository.deleteAll();
        newsRepository.deleteAll();
        eventRepository.deleteAll();
        eventPlaceRepository.deleteAll();
        paymentInformationRepository.deleteAll();
        addressRepository.deleteAll();
        userRepository.deleteAll();
    }

    @BeforeEach
    public void beforeEach() {
        seenNewsRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void createUserWithDuplicatedEmail_shouldThrowServiceException() {
        UserRegisterDto user = UserRegisterDto.UserRegisterDtoBuilder.aUserRegisterDto()
            .withEmail(newUser1.getEmail())
            .withPassword("testPassword")
            .withFirstName("test")
            .withLastName("person")
            .withSalutation("mr")
            .withPhone("+430101011010")
            .withCountry("Austria")
            .withCity("Vienna")
            .withStreet("Test Street")
            .withZip("12345")
            .build();

        try {
            userService.createUser(newUser1);
            userService.createUser(user);
            fail("ContextException should occur!");
        } catch (ContextException e) {
            // Should be the case
        }
    }

    @Test
    public void createUser_thenFindUser() {
        userService.createUser(newUser1);

        assertAll(
            () -> assertEquals(1, userService.findUsers(null).size()),
            () -> assertNotNull(userRepository.findByEmailContains(user1.getEmail()))
        );
    }

    @Test
    public void createSeveralUsers_thenFindAllUser() {
        userService.createUser(newUser1);
        userService.createUser(newUser2);
        userService.createUser(newUser3);

        assertAll(
            () -> assertEquals(3, userService.findUsers(null).size()),
            () -> assertEquals(3, userService.findUsers("user").size()),
            () -> assertNotNull(userRepository.findByEmailContains(user1.getEmail())),
            () -> assertNotNull(userRepository.findByEmailContains(user2.getEmail())),
            () -> assertNotNull(userRepository.findByEmailContains(user3.getEmail()))

        );
    }

    @Test
    public void changeAdminRights_thenShowCorrectRightsOfUser() {
        userService.createUser(newUser1);
        userService.createUser(newAdminUser1);
        Principal principal = newAdminUser1::getEmail;

        userService.setAdmin(newUser1.getEmail(), principal);

        assertAll(
            () -> assertEquals(2, userService.findUsers(null).size()),
            () -> assertEquals(1, userService.findUsers("user").size()),
            () -> assertEquals(true, userService.findApplicationUserByEmail("user1@email.com").getAdmin())
        );
    }

    @Test
    public void changeOwnRights_shouldThrowServiceException() {
        userService.createUser(newAdminUser1);

        Principal principal = newAdminUser1::getEmail;

        try {
            userService.setAdmin(newAdminUser1.getEmail(), principal);
            fail("ConflictException should occur");
        } catch (ConflictException e) {
            // Should be the case
        }
    }

    @Test
    public void deleteNonExistingUser_shouldThrowNotFoundException() {
        userService.createUser(newUser1);

        try {
            userService.deleteUser(newUser2.getEmail());
        } catch (NotFoundException e) {
            // Should be the case
        }
    }

    @Test
    public void deleteWithEmptyEmail_shouldThrowNotFoundException() {
        userService.createUser(newUser1);

        try {
            userService.deleteUser(null);
        } catch (NotFoundException e) {
            // Should be the case
        }
    }

    @Test
    public void deleteExistingUser_shouldRemoveUser() {
        userService.createUser(newUser1);

        assertAll(
            () -> assertEquals(1, userService.findUsers(null).size()),
            () -> assertEquals(1, userService.findUsers("user").size())
        );

        userService.deleteUser(newUser1.getEmail());

        assertAll(
            () -> assertEquals(0, userService.findUsers(null).size()),
            () -> assertEquals(0, userService.findUsers("user").size())
        );
    }

    @Test
    public void deleteExistingUserWithPaymentInformation_shouldRemoveUserAndPaymentInformation() {
        PaymentInformationDto paymentInformation1 = new PaymentInformationDto();
        paymentInformation1.setCreditCardNr("1234123412341234");
        paymentInformation1.setCreditCardExpirationDate("202022");
        paymentInformation1.setCreditCardCvv("123");
        paymentInformation1.setCreditCardName("Test");

        userService.createUser(newUser1);
        UserEditDto toUpdate = UserEditDto.UserEditDtoBuilder.aUserDto()
            .withEmail("user1@email.com")
            .withNewEmail("user1@email.com")
            .withAdmin(false)
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
            .withPaymentInformation(paymentInformation1)  //add paymentinformation to user
            .build();
        userService.updateUser(toUpdate);

        assertAll(
            () -> assertEquals(1, userService.findUsers(null).size()),
            () -> assertEquals(1, userService.findUsers("user").size())
        );
        ApplicationUser user = userRepository.findUserByEmail("user1@email.com");

        userService.deleteUser(newUser1.getEmail());

        assertAll(
            () -> assertEquals(0, userService.findUsers(null).size()),
            () -> assertEquals(0, userService.findUsers("user").size()),
            () -> assertEquals(0, paymentInformationRepository.findByUser(user).size())
        );
    }

    @Test
    public void sendEmailToResetPasswordWithNotExistingEmail_shouldThrowNotFoundException() {
        try {
            userService.sendEmailToResetPassword("notExistingEmail");
        } catch (NotFoundException e) {
            // Should be the case
        }
    }

    @Test
    public void addUserWithDuplicatedEmail_shouldThrowServiceException() {
        UserEditDto user = UserEditDto.UserEditDtoBuilder.aUserDto()
            .withEmail(newUser1.getEmail())
            .withPassword("testPassword")
            .withFirstName("test")
            .withLastName("person")
            .withSalutation("mr")
            .withPhone("+430101011010")
            .withAdmin(true)
            .withDisabled(false)
            .withCountry("Austria")
            .withCity("Vienna")
            .withStreet("Test Street")
            .withZip("12345")
            .build();

        try {
            userService.createUser(newUser1);
            userService.createUser(user);
            fail("ContextException should occur!");
        } catch (ContextException e) {
            // Should be the case
        }
    }

    @Test
    public void addUser_thenFindUser() {
        UserEditDto user = UserEditDto.UserEditDtoBuilder.aUserDto()
            .withEmail(newUser1.getEmail())
            .withPassword("testPassword")
            .withFirstName("test")
            .withLastName("person")
            .withSalutation("mr")
            .withPhone("+430101011010")
            .withAdmin(true)
            .withDisabled(false)
            .withCountry("Austria")
            .withCity("Vienna")
            .withStreet("Test Street")
            .withZip("12345")
            .build();
        userService.createUser(user);

        assertAll(
            () -> assertEquals(1, userService.findUsers(null).size()),
            () -> assertNotNull(userRepository.findByEmailContains(user1.getEmail()))
        );
    }

    @Test
    public void addSeveralUsers_thenFindAllUser() {
        UserEditDto user1 = UserEditDto.UserEditDtoBuilder.aUserDto()
            .withEmail(newUser1.getEmail())
            .withPassword("testPassword")
            .withFirstName("test")
            .withLastName("person")
            .withSalutation("mr")
            .withPhone("+430101011010")
            .withAdmin(true)
            .withDisabled(false)
            .withCountry("Austria")
            .withCity("Vienna")
            .withStreet("Test Street")
            .withZip("12345")
            .build();
        UserEditDto user2 = UserEditDto.UserEditDtoBuilder.aUserDto()
            .withEmail(newUser2.getEmail())
            .withPassword("testPassword")
            .withFirstName("test")
            .withLastName("person")
            .withSalutation("mr")
            .withPhone("+430101011010")
            .withAdmin(true)
            .withDisabled(false)
            .withCountry("Austria")
            .withCity("Vienna")
            .withStreet("Test Street")
            .withZip("12345")
            .build();
        UserEditDto user3 = UserEditDto.UserEditDtoBuilder.aUserDto()
            .withEmail(newUser3.getEmail())
            .withPassword("testPassword")
            .withFirstName("test")
            .withLastName("person")
            .withSalutation("mr")
            .withPhone("+430101011010")
            .withAdmin(true)
            .withDisabled(false)
            .withCountry("Austria")
            .withCity("Vienna")
            .withStreet("Test Street")
            .withZip("12345")
            .build();
        userService.createUser(user1);
        userService.createUser(user2);
        userService.createUser(user3);

        assertAll(
            () -> assertEquals(3, userService.findUsers(null).size()),
            () -> assertEquals(3, userService.findUsers("user").size()),
            () -> assertNotNull(userRepository.findByEmailContains(user1.getEmail())),
            () -> assertNotNull(userRepository.findByEmailContains(user2.getEmail())),
            () -> assertNotNull(userRepository.findByEmailContains(user3.getEmail()))

        );
    }
}
