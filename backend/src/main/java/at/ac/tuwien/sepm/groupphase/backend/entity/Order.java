package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "performance_Id", referencedColumnName = "id")
    private Performance performance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id", referencedColumnName = "id")
    private ApplicationUser user;

    @OneToMany(cascade = CascadeType.REMOVE,
        fetch = FetchType.EAGER,
        mappedBy = "order")
    private List<Ticket> tickets = new ArrayList<>();

    @Column(nullable = false)
    private double prize;

    @Column(nullable = false)
    private boolean bought;

    @Column(nullable = false)
    private LocalDateTime dateOfOrder;

    @Column()
    private boolean refunded = false;

    @ManyToOne
    @JoinColumn(name = "paymentInformation_Id", referencedColumnName = "id")
    private PaymentInformation paymentInformation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public double getPrize() {
        return prize;
    }

    public void setPrize(double prize) {
        this.prize = prize;
    }

    public LocalDateTime getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(LocalDateTime dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public PaymentInformation getPaymentInformation() {
        return paymentInformation;
    }

    public void setPaymentInformation(PaymentInformation paymentInformation) {
        this.paymentInformation = paymentInformation;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void setRefunded(boolean refunded) {
        this.refunded = refunded;
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + id +
            ", performance=" + performance +
            ", user=" + user +
            ", tickets=" + tickets +
            ", prize=" + prize +
            ", bought=" + bought +
            ", dateOfOrder=" + dateOfOrder +
            ", refunded=" + refunded +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return Double.compare(order.prize, prize) == 0 && bought == order.bought && refunded == order.refunded && id.equals(order.id) && dateOfOrder.equals(order.dateOfOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, prize, bought, dateOfOrder, refunded);
    }
}
