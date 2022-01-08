package at.ac.tuwien.sepm.groupphase.backend.entity;

import org.aspectj.weaver.ast.Or;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private double price;

    @Column(nullable = false, length = 100)
    private String typeOfTicket;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private HallplanElement position;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_Id")
    private Order order;

    @Column(nullable = false, length = 100)
    private boolean used;

    @ManyToOne(fetch = FetchType.EAGER)
    private Sector sector;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "performance_id", referencedColumnName = "id")
    private Performance performance;

    public Ticket() {}

    public Ticket(Long id, Performance performance, ApplicationUser user, Long price, String typeOfTicket,
                   HallplanElement position, boolean used, boolean bought) {
        this.id = id;
        this.price = price;
        this.typeOfTicket = typeOfTicket;
        this.position = position;
        this.used = used;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HallplanElement getPosition() {
        return position;
    }

    public void setPosition(HallplanElement position) {
        this.position = position;
    }

    public String getTypeOfTicket() {
        return typeOfTicket;
    }

    public void setTypeOfTicket(String typeOfTicket) {
        this.typeOfTicket = typeOfTicket;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Sector getSector() {
        return sector;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return Double.compare(ticket.price, price) == 0 && used == ticket.used && Objects.equals(id, ticket.id)
            && Objects.equals(typeOfTicket, ticket.typeOfTicket) && Objects.equals(position, ticket.position) && Objects.equals(order, ticket.order)
            && Objects.equals(sector, ticket.sector) && Objects.equals(performance, ticket.performance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, typeOfTicket, position, order, used, sector, performance);
    }

    @Override
    public String toString() {
        return "Ticket{"
            + "id=" + id
            + ", price=" + price
            + ", typeOfTicket='" + typeOfTicket + '\''
            + ", position=" + position
            + ", order=" + order
            + ", used=" + used
            + ", sector=" + sector
            + ", performance=" + performance
            + '}';
    }

    public static final class TicketBuilder {
        private Long id;
        private Long price;
        private String typeOfTicket;
        private HallplanElement position;
        private boolean used;
        private Performance performance;
        private Order order;

        private TicketBuilder() {
        }

        public static Ticket.TicketBuilder aTicket() {
            return new Ticket.TicketBuilder();
        }

        public Ticket.TicketBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public Ticket.TicketBuilder withPrice(Long price) {
            this.price = price;
            return this;
        }

        public Ticket.TicketBuilder withTypeOfTicket(String typeOfTicket) {
            this.typeOfTicket = typeOfTicket;
            return this;
        }

        public Ticket.TicketBuilder withPosition(HallplanElement position) {
            this.position = position;
            return this;
        }

        public Ticket.TicketBuilder withPerformance(Performance performance) {
            this.performance = performance;
            return this;
        }

        public Ticket.TicketBuilder withUsed(boolean used) {
            this.used = used;
            return this;
        }

        public Ticket.TicketBuilder withOrder(Order order) {
            this.order = order;
            return this;
        }


        public Ticket build() {
            Ticket ticket = new Ticket();
            ticket.setId(id);
            ticket.setTypeOfTicket(typeOfTicket);
            ticket.setPosition(position);
            ticket.setPrice(price);
            ticket.setUsed(used);
            ticket.setPerformance(performance);
            ticket.setOrder(order);
            return ticket;
        }
    }
}
