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
import java.util.List;
import java.util.Random;

@Profile("generateData")
@Component
public class UserDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    // Contains a set of cities
    private final String[] cities = {"Vienna", "Budapest", "Paris", "New York", "Berlin", "Tallinn", "Athens",
        "Dublin", "Tel Aviv", "Bratislava", "Singapore", "London", "Rome", "Zagreb"};

    // Contains set of country codes
    private final String[] countries = {"AT", "HU", "FR", "US", "DE", "EE", "GR", "IE",
        "IL", "SK", "SG", "UK", "IT", "HR"};

    // Contains a set of cities
    private final String[] salutations = {"mr", "ms"};

    // Array used to pick a random value for variables, like disabled or admin
    private final boolean[] decision = {true, false};

    public UserDataGenerator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void generateUser() {
        if (userRepository.findAll().size() > 0) {
            LOGGER.debug("users already generated");
        } else {
            for (int i = 1; i <= 1000; i++) {
                // Different names to test the search
                String baseName;
                if (i < 250) {
                    baseName = "user";
                } else if (i < 500) {
                    baseName = "customer";
                } else {
                    baseName = "test";
                }
                String city = getRandom(this.cities);
                boolean admin = getRandomDecision(this.decision);
                boolean disabled = getRandomDecision(this.decision);
                String salutation = getRandom(this.salutations);
                String country = getRandom(this.countries); // countries not related to cities in the test dataset
                userRepository.save(ApplicationUser.ApplicationUserBuilder.aApplicationUser().withEmail(baseName + i + "@email.com")
                    .withPassword(passwordEncoder.encode("password" + i)).withAdmin(admin).withId((long) i).withCity(city)
                    .withCountry(country).withDisabled(disabled).withFirstName("First" + i).withLastName("Last" + i)
                    .withPhone("0664 123 45" + i % 9).withSalutation(salutation).withStreet("Street " + i).withZip("100" + (i % 9))
                    .withLockedCounter(0).build());
            }
        }
    }

    // Pick random city from the list
    public static String getRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    // Pick a boolean value
    public static boolean getRandomDecision(boolean[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

}
