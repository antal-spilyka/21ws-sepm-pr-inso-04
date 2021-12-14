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
    List<SeenNews> findByUser(ApplicationUser user);
}
