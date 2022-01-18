package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.HallplanElement;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Sector;
import at.ac.tuwien.sepm.groupphase.backend.repository.AddressRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventPlaceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallplanElementRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.SectorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Profile("generateData")
@Component
public class EventDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EventRepository eventRepository;

    private final ArtistRepository artistRepository;

    private final EventPlaceRepository eventPlaceRepository;

    private final AddressRepository addressRepository;

    private final PerformanceRepository performanceRepository;

    private final HallRepository hallRepository;

    private final SectorRepository sectorRepository;

    private final HallplanElementRepository hallplanElementRepository;

    public EventDataGenerator(EventRepository eventRepository, ArtistRepository artistRepository,
                              EventPlaceRepository eventPlaceRepository, AddressRepository addressRepository,
                              PerformanceRepository performanceRepository, HallRepository hallRepository,
                              SectorRepository sectorRepository, HallplanElementRepository hallplanElementRepository) {
        this.eventRepository = eventRepository;
        this.artistRepository = artistRepository;
        this.eventPlaceRepository = eventPlaceRepository;
        this.addressRepository = addressRepository;
        this.performanceRepository = performanceRepository;
        this.hallRepository = hallRepository;
        this.sectorRepository = sectorRepository;
        this.hallplanElementRepository = hallplanElementRepository;
    }


    // region categories data

    private String[] categories = {
        "Pop", "Hip Hop", "Rock", "Reggae", "Drum'n'Bass", "R&B", "Rap", "Heavy Metal", "Vocals", "Hard Style"
    };

    // endregion

    // region roomNames

    private String[] roomNames = {
        "Ideas Hall",
        "Decisions HQ",
        "The Conference Space",
        "Kickstart Meetings",
        "Leaders Think Space",
        "Banding Together",
        "The Capital City",
        "Chromatic Commons",
        "Collective IQ",
        "Convening Space",
        "Cranium Focus",
        "Creative Arena",
        "Crowd Pleaser",
        "Crucial Conference",
        "Edge Conference",
        "Energy Boost",
        "Engagement Center",
        "Fellowship Hall",
        "Gathering Place",
        "Gig Gallery",
        "The Steam Room",
        "Alpha Room",
        "All Arrears",
        "Assembly Hall",
        "Away Bleachers",
        "Banding Together",
        "The Batcave",
        "Be Biggie",
        "Boardroom Bebop",
        "Bookworm badge",
        "Bored Room",
        "Break Up Room",
        "Bromance Chamber",
        "Carnegie Room",
        "Chromatic Commons",
        "Collective IQ Room",
        "Community Spot",
        "Convening Space",
        "Cranium Focus",
        "Creative Arena",
        "Crowd Pleaser",
        "Crown Down",
        "Dakota Room",
        "Dam Square",
        "Death Star",
        "Decision Accelerator",
        "Disappointment Beach",
        "Dragonstone",
        "Engagement Center",
        "Executive Playground",
        "Fellowship Hall",
        "First World Problems",
        "Flatiron Room",
        "Gathering Field",
        "Gathering Place",
        "Germination Potential",
        "Gig Gallery",
        "Classic Talk",
        "Designated Area",
        "Organizational Space",
        "Local Landmarks",
        "Digital Signage",
        "Amber",
        "Entire Company",
        "Strategic Thought",
        "Traditional Approach",
        "Meeting Spaces",
        "Office Space",
        "Ginsberg",
        "City Hall",
        "City Lights",
        "Fourth Floor",
        "Office Culture",
        "Core Values",
        "Learning Space",
        "Art Director",
        "Page One",
        "Modern Workspace",
        "Interactive Wayfinding",
        "Potential Themes",
        "Space Utilization",
        "Space Travel",
        "Tech Leaders",
        "Hall of Justice",
        "Harmony Place",
        "Heroes Square",
        "Hospitality Hub",
        "Harmony Studio",
        "Mind Mansion",
        "Inspiration Station",
        "Motivational Space",
        "Huddle Up",
        "Ideas Pressure",
        "Jammin’ Junction",
        "Learning Loft",
        "Meet Up & Coffee",
        "Harmony Palace",
        "Melody Chamber",
        "National Lodges",
        "Noggin Chamber",
        "Noodlin’ Space",
        "Options Chamber",
        "Romping Room",
        "Ronaldo",
        "Room for Jello",
        "Social Sphere",
        "Socialite badge",
        "Soul Station",
        "Spider Skull Island",
        "Swarm badge",
        "Team Territory",
        "Tempo Territory",
        "The Fountain from ‘Friends’",
        "The Kitchen Sync",
        "The Link",
        "The Meet & Greet",
        "The Sync",
        "This Used To Be a Forest",
        "Thunderdome",
        "Tiger Woods",
        "Tiny House for Big Ideas",
        "Toe Tapping Territory",
        "Tune Arena",
        "Tupac",
        "Unavailable",
        "Unite Site",
        "Unlimited Salad and Breadsticks",
        "Vaudeville",
        "Vibrato Valley",
        "Facilitated Learning Space",
        "Vocal Arena",
        "Golden Gate",
        "Little Things",
        "The Earth",
        "Red Velvet",
        "London",
        "Einstein",
        "Popular Items",
        "Famous Squares",
        "Batcave",
        "Creative Conference",
        "Craft Beer",
        "Outlook",
        "Davinci",
        "Fortune",
        "Creative Ideas",
        "Space Travel",
        "Stark",
        "United Nations",
        "Food Network",
        "Meeting Room",
        "Peak Performance",
        "Ostrich",
        "Bluebird",
        "Famous People",
        "Small Detail",
        "Dumbledore",
        "Work Culture",
        "Weather Formations",
        "Central Park",
        "Oakland",
        "Popular Cities",
        "Wizard",
        "Giant",
        "John Glenn",
        "Cheese",
        "Potential Space",
        "Torque Meeting Room",
        "Equilibrium Space",
        "Soul Station",
        "Specialist Meetings",
        "Pitch Place Toe",
        "Pop In",
        "Prefrontal Engagement",
        "Que Conference",
        "Rally Scope",
        "Picture Sessions",
        "Rappin’ Range",
        "Revolution Studio",
        "Rockin’ Rocks",
        "Social Sphere",
        "The Springfield",
        "Stars Vault",
        "Huddle Up",
        "Ideation Zone",
        "Indoctrination Location",
        "Inspiration Station",
        "Jamming Junction",
        "John McEnroe",
        "King’s Landing",
        "King’s Landing",
        "Lafayette Square",
        "Learning Loft",
        "Liberty Square",
        "Meet Up",
        "Buzz Aldrin",
        "Washington Heights",
        "Easy Orientation",
        "Red Velvet",
        "Better Meetings"
    };

    // endregion

    // region eventNames

    private String[] eventNames = {
        "Fearless Festival",
        "Come One Come Festivall",
        "Festival Facilitators",
        "Fixed Fests",
        "Fest Pros",
        "Festival Victories",
        "The Victory Fest",
        "Success Fest",
        "Success - Val Fest",
        "Festival For All",
        "Festival Formen",
        "Oasis Makers",
        "Field Fests",
        "Field Vision",
        "In The Field",
        "Out In The Field",
        "Field Of Dreams",
        "Party Fest",
        "Field Of Friends",
        "Friendly Fields",
        "Friendly Fests",
        "Feast On Festivals",
        "Valley Festivals",
        "Wide Open Festivals",
        "Open Field Fests",
        "Tent Terrific",
        "Single Tent Times",
        "Under The Tent",
        "Tent Terrific",
        "Follow Us Festival",
        "Followers Fest",
        "Open Lands Fest",
        "Fest Nests",
        "Best Fests",
        "Come All Festivals",
        "Freelance Festival",
        "Freedom Festival",
        "Fortunate Fest",
        "Fortune Festival",
        "Fever Fest",
        "Fervent Fest",
        "Fire Festivals",
        "Flame Fests",
        "Festivals For Good",
        "Greatness Festival",
        "Forever Festival",
        "Forest Fests",
        "Frequent Fest",
        "Fun Fest",
        "Outdoor Oasis",  // 50
        "The Local Festival",
        "The Festival Crowd",
        "Happy Hippie",
        "Fresh Fest",
        "Glow Fest",
        "River Float Fest",
        "The Flashy Fest",
        "Best Of The Fest",
        "Festival People",
        "The Gathering",
        "The Yearly Fest",
        "Blues Fest On Main",
        "Downtown Disco",
        "By The Water",
        "By The Bay",
        "On The Coast",
        "Beachside Fest",
        "Hillside Fest",
        "Up In The Hills",
        "Local Food Fest",
        "The Cornucopia",
        "The Big Fiesta",
        "Dance Fest",
        "The Dance-Off",
        "Fans Of The Fest",
        "Sound Fest",
        "Move To The Rhythm",
        "The Vibe Fest",
        "Good Vibrations",
        "Only Good Vibes",
        "All Smiles Fest",
        "Mr. Happy’S Fest",
        "Rhythm Of Life Fest",
        "Better Than The Rest Fest",
        "Sunset Fest",
        "Silver Sun Fest",
        "Moonlight Festival",
        "Summer Moon",
        "Stars Align",
        "Full Moon Fest",
        "Song And Dance Fest",
        "Music Note Fest",
        "Gathering Of The Bands",
        "Down In The Valley",
        "Trickle Of The Creek",
        "Family Fest",
        "Positive Vibes Fest",
        "Go With The Flow Fest",
        "New Year New Fest",
        "The Circus Festival", // 100
        "Always Festive",
        "Let’s Get Festive",
        "Feeling Festive",
        "Festive Makers",
        "Festive Mood",
        "Be Our Fest",
        "Best of Festive",
        "Family Fest",
        "Bless This Fest",
        "Best Fest",
        "Fun Fest",
        "Festival Fun",
        "Party Planners",
        "Party On",
        "Let’s Party",
        "Festive Party",
        "Party Favors",
        "Party Girl",
        "After Party",
        "Party Hearty",
        "Grand gala",
        "Gala gang",
        "Gala Day",
        "Gala for a Day",
        "Festival day",
        "A Gala Affair",
        "That’s Entertainment",
        "let us Entertain You",
        "Entertainment Purposes",
        "All Entertainment",
        "Full Entertainment",
        "Entertain the idea",
        "We Entertain",
        "Party Planners",
        "Event Planners",
        "Gala Events",
        "All Events",
        "What’s The Plan",
        "That’s a Plan",
        "That’s the Plan",
        "Master Plan",
        "Master Planners",
        "Plan of Action",
        "Plan on It",
        "Plan of Attack",
        "Game Plan",
        "Party Plan",
        "Plan Ahead",
        "Golden Jubilee",
        "Diamond Jubilee", // 150
        "Cherries Jubilee",
        "In The Event",
        "Blessed Event",
        "Happy Event",
        "The New Star Festival",
        "All Headline Music Festival",
        "Annual Edition Festival",
        "The Expansion Art Festival",
        "The Next Lineup Music Festival",
        "Circle Of Love Festival",
        "The Unravelled Art Festival",
        "Plenty Real Cultural Festival",
        "Sunrise Place Festival",
        "Summer-Set Music Festival",
        "Springtime Isle Music Festival",
        "The Grand Classic Art Festival",
        "The First Journey Music Festival",
        "Lineup Roots Music Festival",
        "Paints And Science Art Festival",
        "Pop Take Over Festival",
        "The Connected Music Festival",
        "Center Park Festival",
        "Elevation",
        "Blue Culture Festival",
        "Chilled Out Music Festival",
        "Nature And Music",
        "Open Dance Festival",
        "Cultural Gathering",
        "Beachview Festival",
        "Set In Abundance Festival",
        "All Edition Music Festival",
        "Mountain Vibe Festival",
        "Wired And Wonderful",
        "Blended Acts Festival",
        "Double Bill Music Festival",
        "Meadows And Mountains Festival",
        "Dance Field Festival",
        "Youthful Love Festival",
        "Leading Stages",
        "Festival For Cure",
        "The Proof Art Festival",
        "Seaside Town Festival",
        "Journey Begins Festival",
        "Tug And War Festival",
        "Primary Scene Festival",
        "Green Setting Festivals",
        "Beach Shore Isle Festival",
        "Color Of Love Festival",
        "Access Summer Festival",
        "Before October Festival",  // 200
    };

    // endregion

    // region names

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

    // endregion

    // region cities

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

    // endregion

    // region states

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

    // endregion

    // region countries

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

    // endregion

    // region zips

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

    // endregion

    // region streets

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

    // endregion

    // region descriptions

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

    // endregion

    // region other values

    // Durations
    private final Integer[] durations = new Integer[150];

    // Months
    private final Integer[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    // Days
    private final Integer[] days = new Integer[28];

    // Artist ids
    private final Integer[] ids = new Integer[25];

    // Sector colors
    private final String[] colors = {
        "red", "green", "blue", "yellow", "purple", "orange",
        "black", "white", "grey", "brown"
    };

    // Sector prices
    private final Integer[] prices = {
        10, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500
    };

    // endregion

    private void generateEventPlace() {
        if (eventPlaceRepository.findAll().size() > 0) {
            LOGGER.debug("event places already generated");
        } else {
            LOGGER.debug("generating event places");
            for (int i = 1; i <= 25; i++) {
                final String name = this.names[i - 1];
                Address address = new Address((long) i,
                    this.cities[i - 1],
                    this.states[i - 1],
                    this.zips[i - 1],
                    this.countries[i - 1],
                    this.streets[i - 1]);
                addressRepository.save(address);
                EventPlace eventPlace = new EventPlace();
                eventPlace.setId((long) i);
                eventPlace.setAddress(address);
                eventPlace.setName(name);
                eventPlaceRepository.save(eventPlace);
            }
        }
    }

    @PostConstruct
    private void generateEvent() {
        Long uniqueIdentifier = 0L;
        generateEventPlace();
        if (eventRepository.findAll().size() > 0) {
            LOGGER.debug("events already generated");
        } else {
            LOGGER.debug("generating events");
            this.setArray(this.durations, 0, 150);
            this.setArray(this.ids, 0, 25);
            this.setArray(this.days, 0, 28);
            List<Performance> performances = new ArrayList<>();
            List<Event> events = new ArrayList<>();

            // creating all events and performances.
            for (int i = 1; i <= 200; i++) {

                // Event name
                final String eventName = this.eventNames[i - 1];

                // Event datetime
                long minDay = Timestamp.valueOf("2022-01-01 11:00:00").getTime();
                long maxDay = Timestamp.valueOf("2050-12-31 23:59:59").getTime();
                long diff = maxDay - minDay + 1;
                final LocalDateTime dateTime = new Timestamp(minDay + (long) (Math.random() * diff)).toLocalDateTime();


                // Artist
                final long artistId = (long) getRandom(this.ids);
                final Artist artist = artistRepository.getById(artistId);

                // Sector
                final Sector sector = new Sector();
                sector.setId((long) i);
                sector.setName("Sector " + i);
                sector.setColor(getRandomName(this.colors));
                sector.setPrice((int) getRandom(this.prices));
                sectorRepository.save(sector);

                // HallPlanElement
                HallplanElement hallplanElement = new HallplanElement();
                hallplanElement.setId((long) i);
                int rowMin = 1;
                int rowMax = 100;
                final int rowIndex = (int) Math.floor(Math.random() * (rowMax - rowMin + 1) + rowMin);
                hallplanElement.setRowIndex(rowIndex);
                int seatMin = 1;
                int seatMax = 100;
                final int seatIndex = (int) Math.floor(Math.random() * (seatMax - seatMin + 1) + seatMin);
                hallplanElement.setSeatIndex(seatIndex);
                hallplanElement.setSector(sector);
                boolean[] added = {true, false};
                hallplanElement.setAdded(getRandomDecision(added));
                hallplanElement.setType("Hall plan element for sector " + sector.getName());
                hallplanElementRepository.save(hallplanElement);

                // Hall
                final Hall hall = new Hall();
                hall.setId((long) i);
                hall.setName(getRandomName(this.roomNames));
                int standingPlacesMin = 0;
                int standingPlacesMax = 100;
                hall.setStandingPlaces((int) Math.floor(Math.random() * (standingPlacesMax - standingPlacesMin + 1) + standingPlacesMin));
                int id = i % 25 == 0 ? 1 : i % 25;
                hall.setEventPlace(eventPlaceRepository.findByIdEquals((long) id));
                hall.setSectors(List.of(sector));
                List<HallplanElement> hallplanElements = new ArrayList<>();
                hallplanElements.add(hallplanElement);
                hall.setRows(hallplanElements);
                hallRepository.save(hall);

                // Performance
                Performance performance = new Performance();
                performance.setId((long) i);
                performance.setName("Live concert of");
                performance.setStartTime(dateTime);
                performance.setDuration((long) getRandom(this.durations));
                performance.setArtist(artist);
                performance.setHall(hall);
                performance.setPriceMultiplicant(1D);
                //performanceRepository.save(performance);

                // Saving the event
                final EventPlace eventPlace = eventPlaceRepository.findByIdEquals((long) id);
                Event event = new Event();
                event.setId((long) i);
                event.setName(eventName);
                event.setStartTime(dateTime);
                performances.add(performance);
                //event.setPerformances(performances);
                // Event duration
                int duration = 0;
                for (Performance perf : performances) {
                    duration += perf.getDuration();
                }
                event.setDuration((long) duration);
                event.setDescription("Live event of the " + eventName + " with exciting performances taking place at " +
                    eventPlace.getName() + ".");
                event.setEventPlace(eventPlace);
                event.setCategory(getRandomName(this.categories));
                //eventRepository.save(event);
                events.add(event);

                // Saving the event of the performance
                //performance.setEvent(event);
                //performanceRepository.save(performance);
            }

            // adding additional performances to events
            for (Event event : events) {
                List<Performance> currentPerformances = new ArrayList<>();
                for (int i = 0; i <= Math.random() * 20 + 50; i++) {
                    // create new Instance
                    Performance tempPerformance = performances.get(i);

                    Performance performance = new Performance();
                    performance.setName(tempPerformance.getName());
                    performance.setStartTime(tempPerformance.getStartTime());
                    performance.setDuration(tempPerformance.getDuration());
                    performance.setArtist(tempPerformance.getArtist());
                    performance.setHall(tempPerformance.getHall());
                    performance.setPriceMultiplicant(tempPerformance.getPriceMultiplicant());
                    performance.setEvent(event);
                    performance.setId(uniqueIdentifier++);
                    performanceRepository.save(performance);
                    currentPerformances.add(tempPerformance);
                }
                event.setPerformances(currentPerformances);
                eventRepository.save(event);
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

    // Pick random integer from the given list
    public static Integer getRandom(Integer[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    // Pick random string from the given list
    public static String getRandomName(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    // Pick a boolean value
    public static boolean getRandomDecision(boolean[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }
}
