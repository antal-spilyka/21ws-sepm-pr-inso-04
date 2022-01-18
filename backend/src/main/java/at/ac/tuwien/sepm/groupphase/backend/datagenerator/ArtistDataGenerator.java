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

    private String[] artistNames = {"Adele", "Taylor Swift", "Ed Sheeran", "Olivia Rodrigo",
        "Nicki Minaj", "The Weeknd", "Michael Buble", "DJ Khaled", "Drake", "Lana Del Rey",
        "Justin Bieber", "Billie Eilish", "Mariah Carey", "Snoop Dogg", "Eminem",
        "Dua Lipa", "Ariana Grande", "Lil Wayne", "Tyga", "Kanye West", "50 Cent",
        "Bruno Mars", "Miley Cyrus", "Shawn Mendes", "Harry Styles"};

    private String[] artistDescriptions = {
        // #1
        "Adele is a British singer-songwriter who has sold millions of " +
            "albums worldwide and won a total of 15 Grammys as well as an Oscar. " +
            "Adele's first two albums, 19 and 21, earned her critical praise and a level " +
            "of commercial success unsurpassed among her peers",
        // #2
        "Taylor Swift is an American pop and country music singer-songwriter. Five of her " +
            "songs, including “Shake It Off” (2014), “Blank Space” (2014), and “Look What You" +
            " Made Me Do” (2017), topped the Billboard Hot 100",
        // #3
        "English artist Ed Sheeran is a Grammy-winning singer/songwriter known for hit songs " +
            "like 'Thinking Out Loud,' 'Photograph,' 'Shape of You' and 'Perfect.'",
        // #4
        "American actress and singer-songwriter Olivia Rodrigo found fame on the TV show " +
            "'High School Musical: The Musical: The Series' before releasing her No. 1 " +
            "hit single 'Drivers License' in 2021.",
        // #5
        "Hip-hop artist Nicki Minaj rocketed to fame with tracks like 'Super Bass', " +
            "'Starships', and later 'Anaconda'. She is the first female solo artist to have " +
            "seven singles simultaneously on the Billboard 100 chart.",
        // #6
        "Grammy-winning R&B singer and performer the Weeknd has created such hits as " +
            "'Can't Feel My Face,' 'The Hills,' 'Starboy' and 'Heartless.'",
        // #7
        "Michael Bublé is a Grammy-winning singer from Canada whose style is inspired by " +
            "the likes of greats Tony Bennett and Frank Sinatra.",
        // #8
        "The larger-than-life Arab-American music producer and DJ is known for working with " +
            "A-list talent such as Jay-Z, Beyoncé, Lil Wayne and Nicki Minaj and producing " +
            "hits like 'I'm the One', 'All I Do Is Win', and 'I'm So Hood'.",
        // #9
        "TV and rap star Drake is best known in Canada for playing wheelchair-bound Jimmy " +
            "Brooks on 'Degrassi: The Next Generation,' and for hit songs like 'Take Care', " +
            "'One Dance' and 'Hotline Bling'.",
        // #10
        "Singer-songwriter Lana Del Rey took the world by storm with her single 'Video Games'" +
            " in 2011. Since then she's built up a body of work that features languid, " +
            "melancholic tunes and fascinating videos.",
        // #11
        "Justin Bieber is a Grammy Award-winning Canadian pop star who was discovered via " +
            "YouTube. His latest hits include 'Let Me Love You', 'Despacito (Remix)' and " +
            "'I'm the One'.",
        // #12
        "American singer and songwriter Billie Eilish became a pop superstar by way of her " +
            "distinctive musical and fashion sensibilities and songs like 'Ocean Eyes', " +
            "'Bad Guy' and 'Therefore I Am'.",
        // #13
        "With hits such as 'Vision of Love' and 'I Don't Wanna Cry', singer Mariah Carey" +
            " holds the record for most No. 1 debuts in Billboard Hot 100 history.",
        // #14
        "Snoop Dogg is a West Coast rapper who evolved under the tutelage of Dr. Dre, and" +
            " has received fame for albums such as 'Doggystyle,' 'Tha Doggfather' and " +
            "'Reincarnated.'",
        // #15
        "Eminem is an American rapper, record producer and actor known as one of the most " +
            "controversial and best-selling artists of the early 21st century.",
        // #16
        "British-Kosovar-Albanian singer-songwriter Dua Lipa is a Grammy Award winner who " +
            "captivated listeners with her hit single 'New Rules' and her album " +
            "'Future Nostalgia.'",
        // #17
        "Ariana Grande is a sitcom-star-turned-pop music sensation, known for such hit " +
            "songs as 'Problem', 'Bang Bang', 'Dangerous Woman' and 'Thank U, " +
            "Next'.",
        // #18
        "Lil Wayne is a Grammy Award-winning rapper known for his hit albums, mixtapes " +
            "and singles, including 'A Milli' and 'Lollipop'.",
        // #19
        "Tyga is an American rapper best known for being signed by Lil Wayne's Young Money" +
            " Entertainment and having dated reality star Kylie Jenner.",
        // #20
        "Kanye West is an outspoken Grammy Award-winning rapper, record producer and " +
            "fashion designer.",
        // #21
        "Curtis Jackson, known as 50 Cent, is a hip hop artist and business mogul who soared " +
            "to fame with his 2003 debut album 'Get Rich or Die Tryin.''",
        // #22
        "Bruno Mars is Grammy-winning singer/songwriter known for such hit songs as " +
            "'Nothin' on You', 'Just the Way You Are', 'Uptown Funk' and 'That's" +
            " What I Like'.",
        // #23
        "Actress and singer Miley Cyrus achieved early fame as the star of the TV series" +
            " 'Hannah Montana' and went on to become a successful pop artist.",
        // #24
        "Shawn Mendes is a Canadian singer and songwriter who is known for hit songs " +
            "like 'Stitches' and 'There's Nothing Holdin' Me Back'.",
        // #25
        "English-born singer Harry Styles rose to fame as one of the five members of the" +
            " boy band One Direction. He launched his solo career in 2016 and made his acting" +
            " debut in the 2017 film 'Dunkirk.'",
    };

    @PostConstruct
    private void generateArtist() {
        if (artistRepository.findAll().size() > 0) {
            LOGGER.debug("artists already generated");
        } else {
            LOGGER.debug("generating artists");
            for (int i = 1; i <= 25; i++) {
                // Different names to test the search
                String name = this.artistNames[i - 1];
                String description = this.artistDescriptions[i - 1];
                artistRepository.save(Artist.ArtistBuilder.anArtist().withId((long) i)
                    .withBandName(name)
                    .withDescription(description).build());
            }
        }
    }
}
