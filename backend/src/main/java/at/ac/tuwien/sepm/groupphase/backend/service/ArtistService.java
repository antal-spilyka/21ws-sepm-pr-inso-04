package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistSearchDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for working with Artist.
 */
@Service
public interface ArtistService {

    /**
     * Finds artists with mathcing properties.
     *
     * @param artistSearchDto containing properties to be searched for
     * @return List of artists matching properties
     * @throws org.hibernate.service.spi.ServiceException when unknown error occurs
     */
    List<ArtistDto> findArtist(ArtistSearchDto artistSearchDto);

    /**
     * Gets artist by primary key (id).
     *
     * @param id of the artist
     * @return list of fitting artists
     * @throws org.hibernate.service.spi.ServiceException                       when unknown error occurs
     * @throws at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException when artist could not be found
     */
    ArtistDto getById(Long id);

    /**
     * Persists artist.
     *
     * @param artistDto to be persisted
     * @return persisted artist
     * @throws org.hibernate.service.spi.ServiceException                      when unknown error occurs
     * @throws at.ac.tuwien.sepm.groupphase.backend.exception.ContextException when artist already exists
     */
    ArtistDto save(ArtistDto artistDto);
}
