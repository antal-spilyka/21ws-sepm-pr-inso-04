package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.entity.OrderValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderValidationRepository extends JpaRepository<OrderValidation, Long> {

    /**
     * Saves ordervalidation in the repository.
     *
     * @param orderValidation for the order
     */
    OrderValidation save(OrderValidation orderValidation);

    /**
     * Finds orderValidation for an order.
     *
     * @param order of the orderValidation
     * @return orderValidation for a specific order
     */
    OrderValidation findByOrder(Order order);

    /**
     * Returns whether and orderValidation exists for a specific order.
     *
     * @param order of the orderValidation
     * @return true if exists, else false
     */
    boolean existsByOrder(Order order);
}
