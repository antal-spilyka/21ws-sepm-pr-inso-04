package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;

@Profile("generateData")
@Component
public class UserDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDataGenerator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void generateMessage() {
        if (userRepository.findAll().size() > 0) {
            LOGGER.debug("users already generated");
        } else {
            userRepository.save(ApplicationUser.ApplicationUserBuilder.aApplicationUser().withEmail("admin@email.com")
                .withPassword(passwordEncoder.encode("password")).withAdmin(true).withId(1L).withCity("Wien")
                .withCountry("AL").withDisabled(false).withFirstName("Gucci").withLastName("King").withPhone("0664 123 456")
                .withSalutation("mr").withStreet("street 1").withZip("1010").withLocked(false).withLockedCounter(0).build());
            userRepository.save(ApplicationUser.ApplicationUserBuilder.aApplicationUser().withEmail("user@email.com")
                .withPassword(passwordEncoder.encode("password")).withAdmin(false).withId(2L).withCity("Wien")
                .withCountry("AL").withDisabled(false).withFirstName("Gucci").withLastName("King").withPhone("0664 123 456")
                .withSalutation("mr").withStreet("street 1").withZip("1010").withLocked(false).withLockedCounter(0).build());
        }
    }

}
