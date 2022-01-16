package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.entity.OrderValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderValidationRepository extends JpaRepository<OrderValidation, Long> {

    /**
     * Persists a new order validation.
     *
     * @param orderValidation to save.
     * @return OrderValidation entity saved in the database.
     */
    OrderValidation save(OrderValidation orderValidation);

    /**
     * Finds the order validation that belongs to a given order.
     *
     * @param order to find the validation for.
     * @return OrderValidation entity found.
     */
    OrderValidation findByOrder(Order order);

    /**
     * Find out if a validation exists for a given order.
     *
     * @param order to find the validation for.
     * @return boolean value indicating whether the corresponding validation exists.
     */
    boolean existsByOrder(Order order);
}
