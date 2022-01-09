package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeenNews;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeenNewsKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeenNewsRepository extends JpaRepository<SeenNews, SeenNewsKey> {
    /**
     * Finds news seen by a user.
     *
     * @param user whose seen news should be found.
     * @return list of SeenNews which the user has seen.
     */
    List<SeenNews> findByUser(ApplicationUser user);

    /**
     * Deletes the seen news of a user.
     *
     * @param user whose seen news should be deleted.
     * @return id
     */
    Long deleteByUser(ApplicationUser user);
}
