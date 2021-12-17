package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository regarding Artist.
 */
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    /**
     * Finds artist that contains name in first-, lastname or bandName.
     *
     * @param name     of the artist
     * @param pageable regulates the number of results
     * @return list of fitting artists
     * @throws javax.persistence.PersistenceException when unknown error occurs
     */
    @Query("SELECT a FROM Artist a WHERE UPPER(a.bandName) LIKE UPPER(CONCAT( '%', :name, '%'))")
    List<Artist> findArtist(@Param("name") String name, Pageable pageable);

    @Query("SELECT a FROM Artist a WHERE :name is null OR :name='' OR UPPER(a.bandName) LIKE UPPER(CONCAT( '%', :name, '%'))")
    List<Artist> searchArtist(@Param("name") String name, Pageable pageable);

    /**
     * Gets artist by primary key (id).
     *
     * @param id of the artist
     * @return list of fitting artists
     * @throws javax.persistence.PersistenceException when unknown error occurs
     */
    Artist getById(Long id);

    /**
     * Persists artist.
     *
     * @param artist to be persisted
     * @return persisted Artist
     */
    Artist save(Artist artist);

}
