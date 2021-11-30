package at.ac.tuwien.sepm.groupphase.backend.basetest;

import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface TestData {

    Long ID = 1L;
    String TEST_NEWS_TITLE = "Title";
    String TEST_NEWS_SUMMARY = "Summary";
    String TEST_NEWS_TEXT = "TestMessageText";
    LocalDateTime TEST_NEWS_PUBLISHED_AT =
        LocalDateTime.of(2019, 11, 13, 12, 15, 0, 0);

    String BASE_URI = "/api/v1";
    String MESSAGE_BASE_URI = BASE_URI + "/messages";
    String USER_BASE_URI = BASE_URI + "/users";
    String AUTHENTICATION_URI = BASE_URI + "/authentication";

    String ADMIN_USER = "admin@email.com";
    List<String> ADMIN_ROLES = new ArrayList<>() {
        {
            add("ROLE_ADMIN");
            add("ROLE_USER");
        }
    };
    String DEFAULT_USER = "admin@email.com";
    List<String> USER_ROLES = new ArrayList<>() {
        {
            add("ROLE_USER");
        }
    };

    ApplicationUser user1 = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
        .withEmail("user1@email.com")
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

    ApplicationUser user2 = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
        .withEmail("user2@email.com")
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

    ApplicationUser user3 = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
        .withEmail("admin1@email.com")
        .withPassword("testPassword")
        .withAdmin(true)
        .withFirstName("admin")
        .withLastName("test")
        .withSalutation("mr")
        .withPhone("+430101011010")
        .withCountry("Austria")
        .withCity("Vienna")
        .withStreet("Test Street")
        .withZip("12345")
        .withDisabled(true)
        .build();

    ApplicationUser user4 = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
        .withEmail("admin4@email.com")
        .withPassword("testPassword")
        .withAdmin(true)
        .withFirstName("admin")
        .withLastName("test")
        .withSalutation("mr")
        .withPhone("+430101011010")
        .withCountry("Austria")
        .withCity("Vienna")
        .withStreet("Test Street")
        .withZip("12345")
        .withDisabled(true)
        .build();

    ApplicationUser lockedUser = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
        .withEmail("test@email.com")
        .withPassword("password").
        withAdmin(true)
        .withId(1L)
        .withCity("Wien")
        .withCountry("AL")
        .withDisabled(false)
        .withFirstName("Gucci")
        .withLastName("King")
        .withPhone("0664 123 456")
        .withSalutation("mr")
        .withStreet("street 1")
        .withZip("1010")
        .withLockedCounter(5)
        .build();
}
