package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Get bought Order by user.
     *
     * @param user of the user
     * @return List of orders with corresponding user
     * @throws javax.persistence.PersistenceException when unknown error occurs
     */
    List<Order> getOrderByUserAndBoughtTrue(ApplicationUser user);

    /**
     * Get reserved Order by user.
     *
     * @param user of the user
     * @return List of orders with corresponding user
     * @throws javax.persistence.PersistenceException when unknown error occurs
     */
    List<Order> getOrderByUserAndBoughtFalse(ApplicationUser user);

    /**
     * Get all Order by user.
     *
     * @param user of the user
     * @return List of orders with corresponding user
     * @throws javax.persistence.PersistenceException when unknown error occurs
     */
    List<Order> getOrderByUser(ApplicationUser user);

    /**
     * Sets order to refunded.
     *
     * @param orderId of the order
     */
    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.refunded = true WHERE o.id = :orderId")
    void refund(@Param("orderId") Long orderId);
}
