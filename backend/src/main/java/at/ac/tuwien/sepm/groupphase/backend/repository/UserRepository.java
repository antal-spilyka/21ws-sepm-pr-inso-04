package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Long> {

    /**
     * Find the user by the unique mail.
     *
     * @param email of the user
     * @return the found user
     */
    ApplicationUser findUserByEmail(String email);

    /**
     * Find every user in the repository with the given email address.
     *
     * @param email address to search for
     * @return list of users
     */
    List<ApplicationUser> findByEmailContains(String email);
}