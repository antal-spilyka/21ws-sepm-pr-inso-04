package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.entity.Room;
import at.ac.tuwien.sepm.groupphase.backend.repository.AddressRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.CategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventPlaceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.RoomRepository;
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

    private final EventPlaceRepository eventPlaceRepository;

    private final AddressRepository addressRepository;

    private final RoomRepository roomRepository;

    private String[] names = {
        // #1
        "LDS Conference Centre",
        // #2
        "Microsoft Theatre",
        // #3
        "Wolf Trap National Park For The Performing Arts",
        // #4
        "Sportpaleis",
        // #5
        "Manchester Arena",
        // #6
        "O.A.C.A. Olympic Indoor Hall",
        // #7
        "Tauron Arena Kraków",
        // #8
        "Accor Arena",
        // #9
        "Wiener Stadthalle",
        // #10
        "Altice Arena",
        // #11
        "The O2 Arena",
        // #12
        "Žalgiris Arena",
        // #13
        "Lanxess Arena",
        // #14
        "O2 Arena",
        // #15
        "Štark Arena",
        // #16
        "Pala Alpitour",
        // #17
        "Palau Sant Jordi",
        // #18
        "Palacio de Deportes de la Comunidad de Madrid",
        // #19
        "Mercedes-Benz Arena (Berlin)",
        // #20
        "Ziggo Dome",
        // #21
        "Gliwice Arena",
        // #22
        "PostFinance Arena",
        // #23
        "Westfalenhallen",
        // #24
        "Rotterdam Ahoy",
        // #25
        "Barclaycard Arena",
    };

    private String[] cities = {
        "Salt Lake City",
        "Los Angeles",
        "Vienna",
        "Antwerp",
        "Manchester",
        "Athens",
        "Kraków",
        "Paris",
        "Vienna",
        "Lisbon",
        "London",
        "Kaunas",
        "Cologne",
        "Prague",
        "Belgrade",
        "Turin",
        "Barcelona",
        "Madrid",
        "Berlin",
        "Amsterdam",
        "Gliwice",
        "Bern",
        "Dortmund",
        "Rotterdam",
        "Hamburg"
    };

    private String[] states = {
        "Utah",
        "California",
        "Virginia",
        "Antwerp",
        "Manchester",
        "Attica",
        "Lesser Poland",
        "Île-de-France",
        "Vienna",
        "Lisbon",
        "London",
        "Kaunas",
        "North Rhine-Westphalia",
        "Prague",
        "New Belgrade",
        "Piedmont",
        "Barcelona",
        "Madrid",
        "Brandenburg",
        "North Holland",
        "Śląskie województwo (Southern Poland)",
        "Bern",
        "North Rhine-Westphalia",
        "South Holland",
        "Hamburg"
    };

    private String[] countries = {
        "USA",
        "USA",
        "USA",
        "Belgium",
        "United Kingdom",
        "Greece",
        "Poland",
        "France",
        "Austria",
        "Portugal",
        "United Kingdom",
        "Lithuania",
        "Germany",
        "Czech Republic",
        "Serbia",
        "Italy",
        "Spain",
        "Spain",
        "Germany",
        "Netherlands",
        "Poland",
        "Switzerland",
        "Germany",
        "Netherlands",
        "Germany"
    };

    private String[] zips = {
        "84150",
        "90015",
        "22182",
        "2170",
        "M3 1AR",
        "151 23",
        "31-571",
        "75012",
        "1150",
        "1990-231",
        "SE10 0DX",
        "44307",
        "50679",
        "190 00",
        "11070",
        "10134",
        "08038",
        "28009",
        "10243",
        "1101",
        "44-100",
        "3014",
        "44139",
        "3084 BA",
        "22525"
    };

    private String[] streets = {
        "60 N Temple",
        "777 Chick Hearn Ct",
        "551 Trap Rd",
        "Schijnpoortweg 119",
        "Victoria Station Approach",
        "Athens Olympic Sports Complex, Marousi",
        "Stanisława Lema 7",
        "8 Boulevard de Bercy",
        "Roland-Rainer-Platz 1",
        "Rossio dos Olivais",
        "Peninsula Square",
        "Karaliaus Mindaugo pr. 50",
        "Willy-Brandt-Platz 3",
        "Českomoravská 2345/17",
        "Bulevar Arsenija Čarnojevića 58",
        "Corso Sebastopoli 123",
        "Passeig Olímpic",
        "Avenida Felipe II",
        "Mercedes-Platz 1",
        "De Passage 100",
        "Akademicka 50",
        "Mingerstrasse 12",
        "Strobelallee 45",
        "Ahoyweg 10",
        "Sylvesterallee 10"
    };

    private String[] descriptions = {
        "The LDS conference center has a seating capacity of 21,000 people, and" +
            " 21,200 if it includes the seating space at the podium",
        "The Microsoft theatre was formerly known as the Nokia Theatre L.A Live, and " +
            "it was renamed on June 7, 2015. The theatre is located in Los Angeles " +
            "California and has a seating capacity of 7,100 people.",
        "The Wolf Trap National Park located in Fairfax, Virginia has performing " +
            "arts centers with a seating capacity of 7,100 people.",
        "The arena was built for sport, especially track cycling, but there is now " +
            "little sport there, an exception being the Diamond Games tennis.",
        "The arena has the highest seating capacity of any indoor venue in the United Kingdom," +
            " and the third largest in Europe with a capacity of 21,000 and is one of the" +
            " world's busiest indoor arenas, hosting music and sporting events such as " +
            "boxing and swimming.",
        "The O.A.C.A. Olympic Indoor Hall completed in 1995, and was the largest indoor " +
            "venue in use for sporting events at the 2004 Summer Olympics in Athens, Greece.",
        "Tauron Arena Kraków has a seating capacity of 15,030 for sporting events. It " +
            "hosted the 2014 FIVB Volleyball Men's World Championship tournament, 2016 " +
            "European League of Legends Championship Finals and 2015 IIHF Ice Hockey World " +
            "Championship Division I.",
        "Accor Arena is an indoor sports arena and concert hall located in the neighborhood of" +
            " Bercy, on boulevard de Bercy in the 12th arrondissement of Paris, France.",
        "The Wiener Stadhalle is a multi-purpose indoor arena and convention center " +
            "located in the 15th district of Vienna, Austria",
        "Altice Arena is a multi-purpose indoor arena in Lisbon, Portugal. The arena is " +
            "among the largest indoor arenas in Europe and the largest in Portugal with a " +
            "capacity of 20,000 people and was built in 1998 for Expo '98.",
        "The O2 Arena is a multi-purpose indoor arena in the centre of The O2 entertainment" +
            " complex on the Greenwich Peninsula in southeast London. It opened in its " +
            "present form in 2007",
        "Žalgiris Arena is a multi-purpose indoor arena in the New Town of Kaunas," +
            " Lithuania. The arena is located on an island of the Nemunas River. It is" +
            " the largest indoor arena in the Baltics.",
        "The Lanxess Arena is an indoor arena, in Cologne, North Rhine-Westphalia, Germany." +
            " It is known as the 18,500-capacity home of the Kölner Haie.",
        "O2 Arena (formerly Sazka Arena, stylised as O2 arena) is a multi-purpose arena," +
            " in Prague, Czech Republic. It is home to HC Sparta Prague of the Czech " +
            "Extraliga and is the second-largest ice hockey arena in Europe.",
        "The Štark Arena is a multi-purpose indoor arena located " +
            "in Belgrade, Serbia.",
        "The Pala Alpitour, " +
            "also known as PalaIsozaki after its architect, is a multi-purpose indoor arena" +
            " located in the Santa Rita district of Turin, Italy.",
        "Palau Sant Jordi is an indoor sporting arena and multi-purpose installation that is " +
            "part of the Olympic Ring complex located in Barcelona, Catalonia, Spain.",
        "Palacio de Deportes de la Comunidad de Madrid, officially WiZink Center since November 2016" +
            " for sponsorship reasons, is an indoor sporting arena located in Madrid, Spain.",
        "Mercedes-Benz Arena is a multipurpose indoor arena in the Friedrichshain " +
            "neighborhood of Berlin, Germany, which opened in 2008.",
        "Ziggo Dome is an indoor arena in Amsterdam, Netherlands. It is named after the Dutch" +
            " cable TV provider Ziggo.",
        "Gliwice Arena is a multi-purpose indoor arena in Gliwice, Poland and is considered" +
            " one of the largest entertainment and sports halls in the country.",
        "The PostFinance-Arena is an indoor arena in Bern, Switzerland. It is primarily " +
            "used for ice hockey and is the home arena of SC Bern.",
        "Westfalenhallen are three multi-purpose venues located in Dortmund, Germany. " +
            "The original building was opened in 1925, but was destroyed during World War II.",
        "Rotterdam Ahoy is a convention centre and multi-purpose indoor arena located " +
            "in Rotterdam, Netherlands.",
        "The Barclays Arena is a multipurpose arena in Hamburg, Germany. It opened in 2002 " +
            "and can hold up to 16,000 people for sporting events)."
    };

    // Test durations
    private final Integer[] durations = new Integer[150];

    // Months
    private final Integer[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    // Days
    private final Integer[] days = new Integer[28];

    // Artist ids
    private final Integer[] ids = new Integer[25];

    public EventDataGenerator(EventRepository eventRepository, ArtistRepository artistRepository,
                              CategoryRepository categoryRepository, EventPlaceRepository eventPlaceRepository,
                              RoomRepository roomRepository, AddressRepository addressRepository) {
        this.eventRepository = eventRepository;
        this.artistRepository = artistRepository;
        this.categoryRepository = categoryRepository;
        this.eventPlaceRepository = eventPlaceRepository;
        this.roomRepository = roomRepository;
        this.addressRepository = addressRepository;
    }

    @PostConstruct
    private void generateEventPlace() {
        if (eventPlaceRepository.findAll().size() > 0) {
            LOGGER.debug("event places already generated");
        } else {
            for (int i = 1; i <= 25; i++) {
                String name = this.names[i - 1];
                Address address = new Address((long) i,
                    this.cities[i - 1],
                    this.states[i - 1],
                    this.zips[i - 1],
                    this.countries[i - 1],
                    this.descriptions[i - 1],
                    this.streets[i - 1]);
                addressRepository.save(address);
                eventPlaceRepository.save(EventPlace.EventPlaceBuilder.anEventPlace()
                    .withName(name).withAddress(address).build());
            }
        }
    }

    @PostConstruct
    private void generateEvent() {
        if (eventRepository.findAll().size() > 0) {
            LOGGER.debug("events already generated");
        } else {
            this.setArray(this.durations, 0, 150);
            this.setArray(this.ids, 0, 25);
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
                final Integer duration = getRandom(this.durations);
                // Event datetime
                final int year = (i * 11) % 2030;
                final int month = getRandom(this.months);
                final int day = getRandom(this.days);
                final int hour = i % 24;
                final int minute = i % 60;
                final int second = (i * 7) % 60;
                final int nanosecond = (i * 3) % 60;
                final LocalDateTime dateTime =
                    LocalDateTime.of(year, month, day, hour, minute, second, nanosecond);
                // Event category
                final long categoryId = i % 25 == 0 ? 1 : i % 25;
                final Category category = new Category("category" + categoryId);
                categoryRepository.save(category);
                // Room
                final Room room = new Room((long) i, "room" + i,
                    eventPlaceRepository.getById(getRandomName(this.names)));
                roomRepository.save(room);
                // Event artist
                final Artist artist = artistRepository.getById((long) getRandom(this.ids));
                // Saving the event
                eventRepository.save(Event.EventBuilder.anEvent().withId((long) i)
                    .withName(eventName + i).withDuration(duration).withContent("content for the event with name " + eventName)
                    .withDateTime(dateTime).withCategory(category).withRoom(room).withArtist(artist)
                    .withDescription("Test description for the event with name " + eventName).build());
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

    // Pick random name from the list
    public static String getRandomName(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }
}
