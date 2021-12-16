package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

    /**
     * Persists Picture Path.
     *
     * @param picture to be persisted
     * @return persisted picture
     * @throws javax.persistence.PersistenceException when unknown error occurs
     */
    Picture save(Picture picture);

    /**
     * finds News for specific event.
     *
     * @param news of which the pictures are
     * @return corresponding picture paths
     */
    @Query("SELECT p FROM Picture p WHERE p.news = :news")
    List<Picture> findByNewsId(@Param("news") News news);
}
