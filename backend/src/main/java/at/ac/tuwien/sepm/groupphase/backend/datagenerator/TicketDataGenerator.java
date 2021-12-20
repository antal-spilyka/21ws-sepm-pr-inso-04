package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicketDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final TicketRepository ticketRepository;

    private final EventRepository eventRepository;

    private final ArtistRepository artistRepository;

    private final UserRepository userRepository;

    private List<Event> events;

    private final String[] ticketTypes = {"Seated", "Standing", "Disabled"};

    public TicketDataGenerator(TicketRepository ticketRepository, EventRepository eventRepository,
                               ArtistRepository artistRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.artistRepository = artistRepository;
        this.userRepository = userRepository;
        this.events = eventRepository.findAll();
    }

    // Durations
    private final Integer[] durations = new Integer[150];

    // Months
    private final Integer[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    // Days
    private final Integer[] days = new Integer[28];

    // Artist ids
    private final Integer[] ids = new Integer[25];

    // Array used to pick a random value for boolean variables
    private final boolean[] decision = {true, false};

    @PostConstruct
    private void generateTicket() {
        if (ticketRepository.findAll().size() > 0) {
            LOGGER.debug("tickets already generated");
        } else {
            for (int i = 1; i <= 1000; i++) {
                // Performance
                int min = 2022;
                int max = 2050;
                final int year = (int) Math.floor(Math.random() * (max - min + 1) + min);
                final int month = getRandom(this.months);
                final int day = getRandom(this.days);
                final int hour = i % 24;
                final int minute = i % 60;
                final int second = (i * 7) % 60;
                final int nanosecond = (i * 7) % 60;
                final LocalDateTime startTime =
                    LocalDateTime.of(year, month, day, hour, minute, second, nanosecond);

                // Event duration
                final Integer duration = getRandom(this.durations);

                // User
                ApplicationUser user = userRepository.getById((long) i);

                // Artist
                int artistId = (i % 26) == 0 ? 1 : i % 26;
                Artist artist = artistRepository.getById((long) artistId);

                // TypeOfTicket
                String type = getRandomString(this.ticketTypes);

                // Price
                int priceMin = 10;
                int priceMax = 5000;
                Long price = (long) Math.floor(Math.random() * (priceMax - priceMin + 1) + priceMin);

                Performance performance = Performance.PerformanceBuilder.aPerformance().withId((long) i)
                    .withName("Live concert of " + artist.getBandName())
                    .withStartTime(startTime).build();
                ticketRepository.save(Ticket.TicketBuilder.aTicket()
                    .withId((long) i)
                    .withPerformance(null)
                    .withTypeOfTicket(type)
                    .withPosition(null)
                    .withPrice(price)
                    .withUser(user)
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
