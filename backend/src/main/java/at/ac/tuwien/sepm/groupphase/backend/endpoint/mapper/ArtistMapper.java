package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class ArtistMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public ArtistDto entityToDto(Artist artist) {
        LOGGER.trace("Mapping {}", artist);
        ArtistDto artistDto = new ArtistDto();
        artistDto.setId(artist.getId());
        artistDto.setBandName(artist.getBandName());
        artistDto.setDescription(artist.getDescription());
        return artistDto;
    }

    public Artist dtoToEntity(ArtistDto artistDto) {
        LOGGER.trace("Mapping {}", artistDto);
        Artist artist = new Artist();
        artist.setId(artistDto.getId());
        artist.setBandName(artistDto.getBandName());
        artist.setDescription(artistDto.getDescription());
        return artist;
    }
}
