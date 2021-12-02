package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserAdminDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserRegisterDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest implements TestData {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        userRepository.deleteAll();
    }

    @Test
    public void createUserWithoutEmail_shouldThrowServiceException() {
        UserRegisterDto user = UserRegisterDto.UserRegisterDtoBuilder.aUserRegisterDto()
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

        try {
            userService.createUser(user);
            fail("ServiceException should occur!");
        } catch (ServiceException e) {
            // Should be the case
        }
    }

    @Test
    public void createUserWithoutPassword_shouldThrowServiceException() {
        UserRegisterDto user = UserRegisterDto.UserRegisterDtoBuilder.aUserRegisterDto()
            .withEmail("test@email.com")
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

        try {
            userService.createUser(user);
            fail("ServiceException should occur!");
        } catch (ServiceException e) {
            // Should be the case
        }
    }

    @Test
    public void createUserWithoutFirstName_shouldThrowServiceException() {
        UserRegisterDto user = UserRegisterDto.UserRegisterDtoBuilder.aUserRegisterDto()
            .withEmail("test@email.com")
            .withPassword("testPassword")
            .withAdmin(false)
            .withLastName("person")
            .withSalutation("mr")
            .withPhone("+430101011010")
            .withCountry("Austria")
            .withCity("Vienna")
            .withStreet("Test Street")
            .withZip("12345")
            .withDisabled(true)
            .build();

        try {
            userService.createUser(user);
            fail("ServiceException should occur!");
        } catch (ServiceException e) {
            // Should be the case
        }
    }

    @Test
    public void createUserWithoutLastName_shouldThrowServiceException() {
        UserRegisterDto user = UserRegisterDto.UserRegisterDtoBuilder.aUserRegisterDto()
            .withEmail("test@email.com")
            .withPassword("testPassword")
            .withAdmin(false)
            .withFirstName("test")
            .withSalutation("mr")
            .withPhone("+430101011010")
            .withCountry("Austria")
            .withCity("Vienna")
            .withStreet("Test Street")
            .withZip("12345")
            .withDisabled(true)
            .build();

        try {
            userService.createUser(user);
            fail("ServiceException should occur!");
        } catch (ServiceException e) {
            // Should be the case
        }
    }

    @Test
    public void createUserWithoutSalutation_shouldThrowServiceException() {
        UserRegisterDto user = UserRegisterDto.UserRegisterDtoBuilder.aUserRegisterDto()
            .withEmail("test@email.com")
            .withPassword("testPassword")
            .withAdmin(false)
            .withFirstName("test")
            .withLastName("person")
            .withPhone("+430101011010")
            .withCountry("Austria")
            .withCity("Vienna")
            .withStreet("Test Street")
            .withZip("12345")
            .withDisabled(true)
            .build();

        try {
            userService.createUser(user);
            fail("ServiceException should occur!");
        } catch (ServiceException e) {
            // Should be the case
        }
    }

    @Test
    public void createUserWithoutPhone_shouldThrowServiceException() {
        UserRegisterDto user = UserRegisterDto.UserRegisterDtoBuilder.aUserRegisterDto()
            .withEmail("test@email.com")
            .withPassword("testPassword")
            .withAdmin(false)
            .withFirstName("test")
            .withLastName("person")
            .withSalutation("mr")
            .withCountry("Austria")
            .withCity("Vienna")
            .withStreet("Test Street")
            .withZip("12345")
            .withDisabled(true)
            .build();

        try {
            userService.createUser(user);
            fail("ServiceException should occur!");
        } catch (ServiceException e) {
            // Should be the case
        }
    }

    @Test
    public void createUserWithoutCountry_shouldThrowServiceException() {
        UserRegisterDto user = UserRegisterDto.UserRegisterDtoBuilder.aUserRegisterDto()
            .withEmail("test@email.com")
            .withPassword("testPassword")
            .withAdmin(false)
            .withFirstName("test")
            .withLastName("person")
            .withSalutation("mr")
            .withPhone("+430101011010")
            .withCity("Vienna")
            .withStreet("Test Street")
            .withZip("12345")
            .withDisabled(true)
            .build();

        try {
            userService.createUser(user);
            fail("ServiceException should occur!");
        } catch (ServiceException e) {
            // Should be the case
        }
    }

    @Test
    public void createUserWithoutCity_shouldThrowServiceException() {
        UserRegisterDto user = UserRegisterDto.UserRegisterDtoBuilder.aUserRegisterDto()
            .withEmail("test@email.com")
            .withPassword("testPassword")
            .withAdmin(false)
            .withFirstName("test")
            .withLastName("person")
            .withSalutation("mr")
            .withPhone("+430101011010")
            .withCountry("Austria")
            .withStreet("Test Street")
            .withZip("12345")
            .withDisabled(true)
            .build();

        try {
            userService.createUser(user);
            fail("ServiceException should occur!");
        } catch (ServiceException e) {
            // Should be the case
        }
    }

    @Test
    public void createUserWithoutStreet_shouldThrowServiceException() {
        UserRegisterDto user = UserRegisterDto.UserRegisterDtoBuilder.aUserRegisterDto()
            .withEmail("test@email.com")
            .withPassword("testPassword")
            .withAdmin(false)
            .withFirstName("test")
            .withLastName("person")
            .withSalutation("mr")
            .withPhone("+430101011010")
            .withCountry("Austria")
            .withCity("Vienna")
            .withZip("12345")
            .withDisabled(true)
            .build();

        try {
            userService.createUser(user);
            fail("ServiceException should occur!");
        } catch (ServiceException e) {
            // Should be the case
        }
    }

    @Test
    public void createUserWithoutZip_shouldThrowServiceException() {
        UserRegisterDto user = UserRegisterDto.UserRegisterDtoBuilder.aUserRegisterDto()
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
            .withDisabled(true)
            .build();

        try {
            userService.createUser(user);
            fail("ServiceException should occur!");
        } catch (ServiceException e) {
            // Should be the case
        }
    }

    @Test
    public void createUserWithoutDisabled_shouldThrowServiceException() {
        UserRegisterDto user = UserRegisterDto.UserRegisterDtoBuilder.aUserRegisterDto()
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
            .build();

        try {
            userService.createUser(user);
            fail("ServiceException should occur!");
        } catch (ServiceException e) {
            // Should be the case
        }
    }

    @Test
    public void createUserWithDuplicatedEmail_shouldThrowServiceException() {
        UserRegisterDto user = UserRegisterDto.UserRegisterDtoBuilder.aUserRegisterDto()
            .withEmail(newUser1.getEmail())
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
            .build();

        try {
            userService.createUser(newUser1);
            userService.createUser(user);
            fail("ServiceException should occur!");
        } catch (ServiceException e) {
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
        UserAdminDto request = UserAdminDto.UserAdminDtoBuilder.anUserAdminDto()
            .withAdminEmail(newAdminUser1.getEmail())
            .withEmail(newUser1.getEmail())
            .withAdmin(true)
            .build();
        userService.setAdmin(request);

        assertAll(
            () -> assertEquals(2, userService.findUsers(null).size()),
            () -> assertEquals(1, userService.findUsers("user").size()),
            () -> assertEquals(true, userService.findApplicationUserByEmail("user1@email.com").getAdmin())
        );
    }

    @Test
    public void changeOwnRights_shouldThrowServiceException() {
        userService.createUser(newAdminUser1);
        UserAdminDto request = UserAdminDto.UserAdminDtoBuilder.anUserAdminDto()
            .withAdminEmail(newAdminUser1.getEmail())
            .withEmail(newAdminUser1.getEmail())
            .withAdmin(true)
            .build();

        try {
            userService.setAdmin(request);
            fail("ServiceException should occur");
        } catch (ServiceException e) {
            // Should be the case
        }
    }
}
