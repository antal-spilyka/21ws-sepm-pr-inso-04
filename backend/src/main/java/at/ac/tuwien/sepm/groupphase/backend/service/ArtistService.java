package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for working with Artist.
 */
@Service
public interface ArtistService {

    /**
     * Finds artists with matching properties.
     *
     * @param artistSearchDto containing properties to be searched for
     * @return List of artists matching properties
     */
    List<Artist> findArtist(ArtistSearchDto artistSearchDto, Integer number);

    /**
     * Gets artist by primary key (id).
     *
     * @param id of the artist
     * @return list of fitting artists
     */
    Artist getById(Long id);

    /**
     * Persists artist.
     *
     * @param artistDto to be persisted
     * @return persisted artist
     */
    Artist save(ArtistDto artistDto);
}
