package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.entity.OrderValidation;
import at.ac.tuwien.sepm.groupphase.backend.entity.PaymentInformation;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallplanElementRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.OrderRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.OrderValidationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PaymentInformationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.SectorRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Profile("generateData")
@Component
public class TicketDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final TicketRepository ticketRepository;

    private final OrderRepository orderRepository;

    private final OrderValidationRepository orderValidationRepository;

    private final EventRepository eventRepository;

    private final ArtistRepository artistRepository;

    private final UserRepository userRepository;

    private final HallplanElementRepository hallplanElementRepository;

    private final PerformanceRepository performanceRepository;

    private final PasswordEncoder passwordEncoder;

    private PaymentInformationRepository paymentInformationRepository;

    private SectorRepository sectorRepository;

    private final String[] ticketTypes = {"Seated", "Standing", "Disabled"};

    public TicketDataGenerator(TicketRepository ticketRepository, EventRepository eventRepository,
                               ArtistRepository artistRepository, UserRepository userRepository,
                               HallplanElementRepository hallplanElementRepository,
                               PerformanceRepository performanceRepository,
                               PasswordEncoder passwordEncoder, PaymentInformationRepository paymentInformationRepository,
                               OrderRepository orderRepository, OrderValidationRepository orderValidationRepository,
                               SectorRepository sectorRepository) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.artistRepository = artistRepository;
        this.userRepository = userRepository;
        this.hallplanElementRepository = hallplanElementRepository;
        this.performanceRepository = performanceRepository;
        this.passwordEncoder = passwordEncoder;
        this.paymentInformationRepository = paymentInformationRepository;
        this.orderRepository = orderRepository;
        this.orderValidationRepository = orderValidationRepository;
        this.sectorRepository = sectorRepository;
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

    private void generateUser() {
        if (userRepository.findAll().size() > 0) {
            LOGGER.debug("users already generated");
        } else {
            // Global admin
            LOGGER.debug("generating users");
            userRepository.save(ApplicationUser.ApplicationUserBuilder.aApplicationUser().withEmail("admin@email.com")
                .withPassword(passwordEncoder.encode("password")).withAdmin(true).withId(1L).withCity("admin city")
                .withCountry("country").withDisabled(false).withFirstName("First").withLastName("Last")
                .withPhone("0664 123 450").withSalutation("mr").withStreet("Street").withZip("1099")
                .withLockedCounter(0).build());
            // Further 1000 users
            for (int i = 2; i <= 1001; i++) {
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
                int randomIndex = (int) Math.floor(Math.random() * (max - min + 1) + min);
                String city = this.cities[randomIndex];
                boolean admin = getRandomDecision(this.decision);
                boolean disabled = getRandomDecision(this.decision);
                String salutation = getRandomString(this.salutations);
                String country = this.countries[randomIndex];

                //Saving users
                ApplicationUser user = userRepository.save(ApplicationUser.ApplicationUserBuilder.aApplicationUser().withEmail(baseName + i + "@email.com")
                    .withPassword(passwordEncoder.encode("password" + i)).withAdmin(admin).withId((long) i).withCity(city)
                    .withCountry(country).withDisabled(disabled).withFirstName("First" + i).withLastName("Last" + i)
                    .withPhone("0664 123 45" + i % 9).withSalutation(salutation).withStreet("Street " + i).withZip("100" + (i % 9))
                    .withLockedCounter(0).build());

                // Payment information of users
                PaymentInformation paymentInformation = new PaymentInformation();
                paymentInformation.setUser(user);
                min = 100;
                max = 999;
                randomIndex = (int) Math.floor(Math.random() * (max - min + 1) + min);
                paymentInformation.setCreditCardCvv(String.valueOf(randomIndex));
                int creditCardMin1 = 10000000;
                int creditCardMax1 = 99999999;
                int creditCardMin2 = 10000000;
                int creditCardMax2 = 99999999;
                int randomIndex1 = (int) Math.floor(Math.random() * (creditCardMax1 - creditCardMin1 + 1) + creditCardMin1);
                int randomIndex2 = (int) Math.floor(Math.random() * (creditCardMax2 - creditCardMin2 + 1) + creditCardMin2);
                paymentInformation.setCreditCardNr(String.valueOf(randomIndex1) + String.valueOf(randomIndex2));
                long minDay = Timestamp.valueOf("2022-01-01 11:00:00").getTime();
                long maxDay = Timestamp.valueOf("2050-12-31 23:59:59").getTime();
                long diff = maxDay - minDay + 1;
                final LocalDateTime dateTime = new Timestamp(minDay + (long) (Math.random() * diff)).toLocalDateTime();
                paymentInformation.setCreditCardExpirationDate("" + dateTime.getMonth() + dateTime.getYear());
                paymentInformation.setCreditCardName("First " + i + " Last " + i);
                paymentInformationRepository.save(paymentInformation);
            }
        }
    }

    @PostConstruct
    private void generateTicket() {
        if (ticketRepository.findAll().size() > 0) {
            LOGGER.debug("tickets already generated");
        } else {
            LOGGER.debug("generating tickets");
            generateUser();
            List<Ticket> tickets = new ArrayList<>();
            List<Order> orders = new ArrayList<>();
            for (int i = 1, counter = 0; i <= 1000; i++) {
                long minDay = LocalDate.of(2022, 1, 1).toEpochDay();
                long maxDay = LocalDate.of(2050, 12, 31).toEpochDay();
                long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
                LocalDateTime startTime = LocalDate.ofEpochDay(randomDay).atStartOfDay();

                // Event duration
                final Integer duration = getRandom(this.durations);

                // Artist
                int artistId = (i % 26) == 0 ? 1 : i % 26;
                Artist artist = artistRepository.getById((long) artistId);

                // Price
                int priceMin = 10;
                int priceMax = 5000;
                Long price = (long) Math.floor(Math.random() * (priceMax - priceMin + 1) + priceMin);

                final long id = i % 200 == 0 ? 1 : i % 200;

                boolean bought = getRandomDecision(this.decision);
                ApplicationUser user = userRepository.getById(id);

                Order order = new Order();
                order.setPerformance(performanceRepository.getById(id));
                order.setPrize(Math.round(price * performanceRepository.getById(id).getPriceMultiplicant() * 100) / 100);
                order.setDateOfOrder(LocalDateTime.now());
                order.setBought(bought);
                order.setUser(user);
                if (bought) {
                    List<PaymentInformation> paymentInformations = paymentInformationRepository.findByUser(user);
                    if (!paymentInformations.isEmpty()) {
                        order.setPaymentInformation(paymentInformations.get(0));
                    }
                }
                orderRepository.save(order);

                Random random = new Random();
                OrderValidation orderValidation = new OrderValidation();
                orderValidation.setOrder(order);
                orderValidation.setHash(random.nextInt() + "$2a$10$r5LarLhaASjKH7xQ%2FCZI4OIMoxJoyGGHYbSx9uYTR1YHI0e0Ni0au" + random.nextInt());
                orderValidationRepository.save(orderValidation);

                // TypeOfTicket
                String type = getRandomString(this.ticketTypes);

                List<Performance> performances = performanceRepository.findByOrderByIdAsc();
                for (int j = 0; j < 50 * Math.random() + 100; j++) {
                    Ticket ticket = Ticket.TicketBuilder.aTicket()
                        .withId((long) counter++)
                        .withPerformance(performanceRepository.getById(id))
                        .withTypeOfTicket(type)
                        .withPosition(hallplanElementRepository.getById(id))
                        .withPrice(price)
                        .withUsed(getRandomDecision(this.decision))
                        .withOrder(order)
                        .withSector(sectorRepository.getById((long) (i % 200) + 1L))
                        .build();

                    tickets.add(ticket);
                    ticketRepository.save(ticket);
                }
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
