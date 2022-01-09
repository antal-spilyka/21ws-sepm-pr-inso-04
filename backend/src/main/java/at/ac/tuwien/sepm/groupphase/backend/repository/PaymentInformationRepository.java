package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.PaymentInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentInformationRepository extends JpaRepository<PaymentInformation, Long> {
    /**
     * Finds all the saved payment information of the user.
     *
     * @param user whose payment information should be found.
     * @return list of all the payment information saved for the user.
     */
    List<PaymentInformation> findByUser(ApplicationUser user);
}
