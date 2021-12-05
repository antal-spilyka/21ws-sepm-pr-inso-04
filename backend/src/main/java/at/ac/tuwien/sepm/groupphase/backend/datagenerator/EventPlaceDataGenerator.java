package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.repository.AddressRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventPlaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;

@Profile("generateData")
@Component
public class EventPlaceDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EventPlaceRepository eventPlaceRepository;

    private final AddressRepository addressRepository;

    public EventPlaceDataGenerator(EventPlaceRepository eventPlaceRepository, AddressRepository addressRepository) {
        this.eventPlaceRepository = eventPlaceRepository;
        this.addressRepository = addressRepository;
    }

    @PostConstruct
    private void generateEvent() {
        if (eventPlaceRepository.findAll().size() > 0) {
            LOGGER.debug("event places already generated");
        } else {
            for (int i = 1; i <= 25; i++) {
                // Different names to test the search
                String eventPlaceName;
                if (i < 10) {
                    eventPlaceName = "place";
                } else if (i < 15) {
                    eventPlaceName = "location";
                } else {
                    eventPlaceName = "event place";
                }
                Address address = new Address((long) i,
                    "city" + i,
                    "state" + i,
                    "zip" + i,
                    "country" + i,
                    "description" + i,
                    "street" + i);
                addressRepository.save(address);
                eventPlaceRepository.save(EventPlace.EventPlaceBuilder.anEventPlace()
                    .withName(eventPlaceName + i).withAddress(address).build());
            }
        }
    }
}
