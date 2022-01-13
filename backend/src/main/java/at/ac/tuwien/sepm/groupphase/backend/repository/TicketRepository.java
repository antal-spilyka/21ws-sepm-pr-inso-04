package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    /**
     * Persists ticket.
     *
     * @param ticket to be persisted
     * @return persisted ticket
     */
    Ticket save(Ticket ticket);

    /**
     * Sets ticket of order to refunded.
     *
     * @param orderId of the order
     */
    @Transactional
    @Modifying
    @Query("UPDATE Ticket t SET t.refunded = true WHERE t.order.id = :orderId")
    void refund(@Param("orderId") Long orderId);

    /**
     * returns all tickets of an order.
     *
     * @param order to be searched for
     * @return list of ticket
     */
    List<Ticket> findTicketsByOrder(Order order);
}
