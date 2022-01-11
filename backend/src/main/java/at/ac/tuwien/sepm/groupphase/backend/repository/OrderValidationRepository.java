package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.entity.OrderValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderValidationRepository extends JpaRepository<OrderValidation, Long> {

    OrderValidation save(OrderValidation orderValidation);

    OrderValidation findByOrder(Order order);

    boolean existsByOrder(Order order);
}
