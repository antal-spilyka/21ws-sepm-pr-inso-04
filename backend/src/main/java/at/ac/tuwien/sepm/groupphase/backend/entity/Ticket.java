package at.ac.tuwien.sepm.groupphase.backend.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Performance performance;

    @OneToOne(fetch = FetchType.LAZY)
    private ApplicationUser user;

    @Column(nullable = false, length = 100)
    private Long price;

    @Column(nullable = false, length = 100)
    private String typeOfTicket;

    @OneToOne(fetch = FetchType.LAZY)
    private HallplanElement position;

    @Column(nullable = false, length = 100)
    private boolean used;

    @Column(nullable = false, length = 100)
    private boolean bought;

    public Ticket() {}

    public Ticket(Long id, Performance performance, ApplicationUser user, Long price, String typeOfTicket,
                   HallplanElement position, boolean used, boolean bought) {
        this.id = id;
        this.performance = performance;
        this.user = user;
        this.price = price;
        this.typeOfTicket = typeOfTicket;
        this.position = position;
        this.used = used;
        this.bought = bought;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
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

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
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
        return Objects.equals(id, ticket.id)
            && Objects.equals(performance, ticket.performance)
            && Objects.equals(user, ticket.user)
            && Objects.equals(price, ticket.price)
            && Objects.equals(typeOfTicket, ticket.typeOfTicket)
            && Objects.equals(position, ticket.position)
            && Objects.equals(used, ticket.used)
            && Objects.equals(bought, ticket.bought);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, performance, user, price, typeOfTicket, position, used, bought);
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + id +
            ", performance='" + performance + '\'' +
            ", user='" + user +
            ", price=" + price + '\'' +
            ", typeOfTicket='" + typeOfTicket +
            ", position=" + position +
            ", used=" + used + '\'' +
            ", bought=" + bought +
            '}';
    }

    public static final class TicketBuilder {
        private Long id;
        private Performance performance;
        private ApplicationUser user;
        private Long price;
        private String typeOfTicket;
        private HallplanElement position;
        private boolean used;
        private boolean bought;

        private TicketBuilder() {
        }

        public static Ticket.TicketBuilder aTicket() {
            return new Ticket.TicketBuilder();
        }

        public Ticket.TicketBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public Ticket.TicketBuilder withPerformance(Performance performance) {
            this.performance = performance;
            return this;
        }

        public Ticket.TicketBuilder withUser(ApplicationUser user) {
            this.user = user;
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

        public Ticket.TicketBuilder withUsed(boolean used) {
            this.used = used;
            return this;
        }

        public Ticket.TicketBuilder withBought(boolean bought) {
            this.bought = bought;
            return this;
        }

        public Ticket build() {
            Ticket ticket = new Ticket();
            ticket.setId(id);
            ticket.setPerformance(performance);
            ticket.setUser(user);
            ticket.setTypeOfTicket(typeOfTicket);
            ticket.setPosition(position);
            ticket.setPrice(price);
            ticket.setUsed(used);
            ticket.setBought(bought);
            return ticket;
        }
    }
}
