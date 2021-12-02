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
    UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
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
