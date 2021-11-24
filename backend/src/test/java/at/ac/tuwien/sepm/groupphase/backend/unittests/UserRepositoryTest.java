package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
// This test slice annotation is used instead of @SpringBootTest to load only repository beans instead of
// the entire application context
@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest implements TestData {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void missingEmail_whenSaveUser_thenFindListWithOneElementAndFindUserById() {
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
    public void missingPassword_whenSaveUser_thenFindListWithOneElementAndFindUserById() {
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
    public void missingFirstName_whenSaveUser_thenFindListWithOneElementAndFindUserById() {
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
    public void missingLastName_whenSaveUser_thenFindListWithOneElementAndFindUserById() {
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
    public void missingSalutation_whenSaveUser_thenFindListWithOneElementAndFindUserById() {
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

    @Test ()
    public void givenNothing_whenSaveUser_thenFindListWithOneElementAndFindUserById() {
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
            .withDisabled(true)
            .build();

        userRepository.save(user);

        assertAll(
            () -> assertEquals(1, userRepository.findAll().size()),
            () -> assertNotNull(userRepository.findById(user.getId()))
        );


    }

}
