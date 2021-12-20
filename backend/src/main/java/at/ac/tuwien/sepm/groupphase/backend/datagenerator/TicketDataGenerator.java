package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallplanElementRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Profile("generateData")
@Component
public class TicketDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final TicketRepository ticketRepository;

    private final EventRepository eventRepository;

    private final ArtistRepository artistRepository;

    private final UserRepository userRepository;

    private final HallplanElementRepository hallplanElementRepository;

    private final PerformanceRepository performanceRepository;

    private final PasswordEncoder passwordEncoder;


    private final String[] ticketTypes = {"Seated", "Standing", "Disabled"};

    public TicketDataGenerator(TicketRepository ticketRepository, EventRepository eventRepository,
                               ArtistRepository artistRepository, UserRepository userRepository,
                               HallplanElementRepository hallplanElementRepository,
                               PerformanceRepository performanceRepository,
                               PasswordEncoder passwordEncoder) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.artistRepository = artistRepository;
        this.userRepository = userRepository;
        this.hallplanElementRepository = hallplanElementRepository;
        this.performanceRepository = performanceRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Durations
    private final Integer[] durations = new Integer[150];

    // Array used to pick a random value for boolean variables
    private final boolean[] decision = {true, false};

    // Contains a set of cities
    private final String[] cities = {"Vienna", "Budapest", "Paris", "New York", "Berlin", "Tallinn", "Athens",
        "Dublin", "Tel Aviv", "Bratislava", "Singapore", "London", "Rome", "Zagreb"};

    // Contains set of country codes
    private final String[] countries = {"AT", "HU", "FR", "US", "DE", "EE", "GR", "IE",
        "IL", "SK", "SG", "UK", "IT", "HR"};

    // Contains a set of cities
    private final String[] salutations = {"mr", "ms"};

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
                    baseName = "employee";
                }
                int min = 0;
                int max = 13;
                final int randomIndex = (int) Math.floor(Math.random() * (max - min + 1) + min);
                String city = this.cities[randomIndex];
                boolean admin = getRandomDecision(this.decision);
                boolean disabled = getRandomDecision(this.decision);
                String salutation = getRandomString(this.salutations);
                String country = this.countries[randomIndex];
                userRepository.save(ApplicationUser.ApplicationUserBuilder.aApplicationUser().withEmail(baseName + i + "@email.com")
                    .withPassword(passwordEncoder.encode("password" + i)).withAdmin(admin).withId((long) i).withCity(city)
                    .withCountry(country).withDisabled(disabled).withFirstName("First" + i).withLastName("Last" + i)
                    .withPhone("0664 123 45" + i % 9).withSalutation(salutation).withStreet("Street " + i).withZip("100" + (i % 9))
                    .withLockedCounter(0).build());
            }
        }
    }

    @PostConstruct
    private void generateTicket() {
        if (ticketRepository.findAll().size() > 0) {
            LOGGER.debug("tickets already generated");
        } else {
            for (int i = 1; i <= 1000; i++) {
                long minDay = LocalDate.of(2022, 1, 1).toEpochDay();
                long maxDay = LocalDate.of(2050, 12, 31).toEpochDay();
                long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
                LocalDateTime startTime = LocalDate.ofEpochDay(randomDay).atStartOfDay();

                // Event duration
                final Integer duration = getRandom(this.durations);

                // Artist
                int artistId = (i % 26) == 0 ? 1 : i % 26;
                Artist artist = artistRepository.getById((long) artistId);

                // TypeOfTicket
                String type = getRandomString(this.ticketTypes);

                // Price
                int priceMin = 10;
                int priceMax = 5000;
                Long price = (long) Math.floor(Math.random() * (priceMax - priceMin + 1) + priceMin);

                final long id = i % 200 == 0 ? 1 : i % 200;

                ticketRepository.save(Ticket.TicketBuilder.aTicket()
                    .withId((long) i)
                    .withPerformance(performanceRepository.getById(id))
                    .withTypeOfTicket(type)
                    .withPosition(hallplanElementRepository.getById(id))
                    .withPrice(price)
                    .withUser(userRepository.getById((long) i))
                    .withUsed(getRandomDecision(this.decision))
                    .withBought(getRandomDecision(this.decision))
                    .build());
            }
        }
    }

    // Pick random integer from the given list
    public static Integer getRandom(Integer[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    // Pick random string from the given list
    public static String getRandomString(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    // Pick a boolean value
    public static boolean getRandomDecision(boolean[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }
}
