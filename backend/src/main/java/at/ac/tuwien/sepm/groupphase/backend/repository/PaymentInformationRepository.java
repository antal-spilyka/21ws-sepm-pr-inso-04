package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.PaymentInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentInformationRepository extends JpaRepository<PaymentInformation, Long> {
    /**
     * Find the user by the mail.
     *
     * @return a user
     */
    List<PaymentInformation> findByUser(ApplicationUser user);
}
