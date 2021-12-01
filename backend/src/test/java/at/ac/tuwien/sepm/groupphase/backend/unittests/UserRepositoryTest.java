package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserEditDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
// This test slice annotation is used instead of @SpringBootTest to load only repository beans instead of
// the entire application context
@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest implements TestData {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void missingEmail_whenSaveUser_shouldReturnDataIntegrityException() {
        ApplicationUser user = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
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
            userRepository.save(user);
            fail("DataIntegrityViolation should occur!");
        } catch (DataIntegrityViolationException e) {
            // Should be the case
        }
    }

    @Test
    public void missingPassword_whenSaveUser_shouldReturnDataIntegrityException() {
        ApplicationUser user = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
            .withEmail("test@mail.com")
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
            userRepository.save(user);
            fail("DataIntegrityViolation should occur!");
        } catch (DataIntegrityViolationException e) {
            // Should be the case
        }
    }

    @Test
    public void missingFirstName_whenSaveUser_shouldReturnDataIntegrityException() {
        ApplicationUser user = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
            .withEmail("test@mail.com")
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
            userRepository.save(user);
            fail("DataIntegrityViolation should occur!");
        } catch (DataIntegrityViolationException e) {
            // Should be the case
        }
    }

    @Test
    public void missingLastName_whenSaveUser_shouldReturnDataIntegrityException() {
        ApplicationUser user = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
            .withEmail("test@mail.com")
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
            userRepository.save(user);
            fail("DataIntegrityViolation should occur!");
        } catch (DataIntegrityViolationException e) {
            // Should be the case
        }
    }

    @Test
    public void missingSalutation_whenSaveUser_shouldReturnDataIntegrityException() {
        ApplicationUser user = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
            .withEmail("test@mail.com")
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
            userRepository.save(user);
            fail("DataIntegrityViolation should occur!");
        } catch (DataIntegrityViolationException e) {
            // Should be the case
        }
    }

    @Test
    public void missingPhone_whenSaveUser_shouldReturnDataIntegrityException() {
        ApplicationUser user = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
            .withEmail("test@mail.com")
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
            userRepository.save(user);
            fail("DataIntegrityViolation should occur!");
        } catch (DataIntegrityViolationException e) {
            // Should be the case
        }
    }

    @Test
    public void missingCountry_whenSaveUser_shouldReturnDataIntegrityException() {
        ApplicationUser user = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
            .withEmail("test@mail.com")
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
            userRepository.save(user);
            fail("DataIntegrityViolation should occur!");
        } catch (DataIntegrityViolationException e) {
            // Should be the case
        }
    }

    @Test
    public void missingCity_whenSaveUser_shouldReturnDataIntegrityException() {
        ApplicationUser user = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
            .withEmail("test@mail.com")
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
            userRepository.save(user);
            fail("DataIntegrityViolation should occur!");
        } catch (DataIntegrityViolationException e) {
            // Should be the case
        }
    }

    @Test
    public void missingStreet_whenSaveUser_shouldReturnDataIntegrityException() {
        ApplicationUser user = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
            .withEmail("test@mail.com")
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
            userRepository.save(user);
            fail("DataIntegrityViolation should occur!");
        } catch (DataIntegrityViolationException e) {
            // Should be the case
        }
    }

    @Test
    public void missingZip_whenSaveUser_shouldReturnDataIntegrityException() {
        ApplicationUser user = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
            .withEmail("test@mail.com")
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
            userRepository.save(user);
            fail("DataIntegrityViolation should occur!");
        } catch (DataIntegrityViolationException e) {
            // Should be the case
        }
    }

    @Test
    public void missingDisabled_whenSaveUser_shouldReturnDataIntegrityException() {
        ApplicationUser user = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
            .withEmail("test@mail.com")
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
            userRepository.save(user);
            fail("DataIntegrityViolation should occur!");
        } catch (DataIntegrityViolationException e) {
            // Should be the case
        }
    }

    @Test ()
    public void givenNothing_whenSaveUser_thenFindListWithOneElementAndFindUserById() {
        userRepository.save(user1);

        assertAll(
            () -> assertEquals(1, userRepository.findAll().size()),
            () -> assertNotNull(userRepository.findById(user1.getId()))
        );
    }

    @Test ()
    public void givenNothing_whenSaveSeveralUsers_thenFindListOfUsers() {
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);

        assertAll(
            () -> assertEquals(4, userRepository.findAll().size())
        );
    }

    @Test ()
    public void givenNothing_whenSaveUser_thenFindListWithOneElementAndFindUserByEmailContains() {
        userRepository.save(user1);

        assertAll(
            () -> assertEquals(1, userRepository.findAll().size()),
            () -> assertNotNull(userRepository.findByEmailContains("test"))
        );
    }

    @Test ()
    public void givenNothing_whenSaveSeveralUsers_thenFindListOfUsersAndProperSearchResults() {
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);

        assertAll(
            () -> assertEquals(4, userRepository.findAll().size()),
            () -> assertEquals(2, userRepository.findByEmailContains("user").size()),
            () -> assertEquals(2, userRepository.findByEmailContains("admin").size())
        );
    }
}
