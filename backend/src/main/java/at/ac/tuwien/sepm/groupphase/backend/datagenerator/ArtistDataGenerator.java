package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;

@Profile("generateData")
@Component
public class ArtistDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ArtistRepository artistRepository;

    public ArtistDataGenerator(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @PostConstruct
    private void generateArtist() {
        if (artistRepository.findAll().size() > 0) {
            LOGGER.debug("artists already generated");
        } else {
            for (int i = 1; i <= 25; i++) {
                // Different names to test the search
                String bandName;
                if (i < 10) {
                    bandName = "rock band";
                } else if (i < 20) {
                    bandName = "pop band";
                } else {
                    bandName = "heavy metal band";
                }
                artistRepository.save(Artist.ArtistBuilder.anArtist().withId((long) i)
                    .withBandName(bandName + i)
                    .withDescription("Test description for the artist with band " + bandName).build());
            }
        }
    }
}
