package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Room;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.CategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.Random;

@Profile("generateData")
@Component
public class EventDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EventRepository eventRepository;

    private final ArtistRepository artistRepository;

    private final CategoryRepository categoryRepository;

    // Test durations
    private final Integer[] durations = new Integer[150];

    // Months
    private final Integer[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    // Days
    private final Integer[] days = new Integer[28];

    // Artist ids
    private final Integer[] artistIds = new Integer[25];

    public EventDataGenerator(EventRepository eventRepository, ArtistRepository artistRepository,
                              CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.artistRepository = artistRepository;
        this.categoryRepository = categoryRepository;
    }

    @PostConstruct
    private void generateEvent() {
        if (eventRepository.findAll().size() > 0) {
            LOGGER.debug("events already generated");
        } else {
            this.setArray(this.durations, 0, 150);
            this.setArray(this.artistIds, 0, 25);
            this.setArray(this.days, 0, 28);

            for (int i = 1; i <= 200; i++) {
                // Different names to test the search
                String eventName;
                if (i < 75) {
                    eventName = "event";
                } else if (i < 150) {
                    eventName = "concert";
                } else {
                    eventName = "meeting";
                }
                // Event duration
                Integer duration = getRandom(this.durations);
                // Event datetime
                int year = (i * 11) % 2030;
                int month = getRandom(this.months);
                int day = getRandom(this.days);
                int hour = i % 24;
                int minute = i % 60;
                int second = (i * 7) % 60;
                int nanosecond = (i * 3) % 60;
                LocalDateTime dateTime =
                    LocalDateTime.of(year, month, day, hour, minute, second, nanosecond);
                // Event category
                long artistId = i % 25 == 0 ? 1 : i % 25;
                Category category = new Category("category" + artistId);
                categoryRepository.save(category);
                // Room
                //Room room = new Room()
                // Event artist
                Artist artist = artistRepository.getById((long) getRandom(this.artistIds));
                eventRepository.save(Event.EventBuilder.anEvent().withId((long) i)
                        .withName(eventName + i).withDuration(duration).withContent("content for the event with name " + eventName)
                        .withDateTime(dateTime).withCategory(category).withRoom(null).withArtist(artist)
                    .withDescription("Test description for the artist with band " + eventName).build());
            }
        }
    }

    public void setArray(Integer[] array, int start, int limit) {
        for (int i = start; i < limit; i++) {
            if (i == 0) {
                array[i] = 1;
            } else {
                array[i] = i;
            }
        }
    }

    // Pick random duration from the list
    public static Integer getRandom(Integer[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }
}
