package at.ac.tuwien.sepm.groupphase.backend.basetest;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserRegisterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface TestData {

    String BASE_URI = "/api/v1";
    String EVENT_BASE_URI = BASE_URI + "/artists";
    String NEWS_BASE_URI = BASE_URI + "/news";
    String USER_BASE_URI = BASE_URI + "/users";
    String AUTHENTICATION_URI = BASE_URI + "/authentication";
    String FILE_URI = BASE_URI + "/files";

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
    String ARTIST_BASE_URI = BASE_URI + "/artists";

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
        .withPassword("password")
        .withAdmin(true)
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

    UserRegisterDto newUser1 = UserRegisterDto.UserRegisterDtoBuilder.aUserRegisterDto()
        .withEmail("user1@email.com")
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

    UserRegisterDto newUser2 = UserRegisterDto.UserRegisterDtoBuilder.aUserRegisterDto()
        .withEmail("user2@email.com")
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

    UserRegisterDto newUser3 = UserRegisterDto.UserRegisterDtoBuilder.aUserRegisterDto()
        .withEmail("user3@email.com")
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

    UserRegisterDto newAdminUser1 = UserRegisterDto.UserRegisterDtoBuilder.aUserRegisterDto()
        .withEmail("admin1@email.com")
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
}
