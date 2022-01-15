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
import javax.persistence.OneToOne;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private Long duration;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;

    @OneToMany(mappedBy = "performance", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Order> orders;

    @OneToMany(mappedBy = "performance", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Ticket> tickets;

    @OneToOne(fetch = FetchType.EAGER)
    private Artist artist;

    @OneToOne(fetch = FetchType.EAGER)
    private Hall hall;

    @Column(nullable = false)
    private Double priceMultiplicant;

    public Performance() {}

    public Performance(Long id, String name, LocalDateTime startTime, Long duration,
                       Event event, Artist artist, Hall hall) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.event = event;
        this.artist = artist;
        this.hall = hall;
    }

    public Performance(Long id, String name, LocalDateTime startTime, Long duration,
                       Event event, Artist artist, Hall hall, Double priceMultiplicant) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.event = event;
        this.artist = artist;
        this.hall = hall;
        this.priceMultiplicant = priceMultiplicant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Artist getArtist() {
        return artist;
    }

    @Transactional
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Double getPriceMultiplicant() {
        return priceMultiplicant;
    }

    public void setPriceMultiplicant(Double priceMultiplicant) {
        this.priceMultiplicant = priceMultiplicant;
    }

    @Override
    public String toString() {
        return "Performance{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", startTime=" + startTime +
            ", duration=" + duration +
            ", artist=" + artist +
            ", hall=" + hall +
            ", tickets=" + tickets +
            ", orders=" + orders +
            ", priceMultiplicant=" + priceMultiplicant +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Performance)) {
            return false;
        }
        Performance that = (Performance) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
            && Objects.equals(startTime, that.startTime) && Objects.equals(duration, that.duration)
            && Objects.equals(event, that.event) && Objects.equals(artist, that.artist)
            && Objects.equals(hall, that.hall) && Objects.equals(orders, that.orders)
            && Objects.equals(tickets, that.tickets)
            && Objects.equals(priceMultiplicant, that.priceMultiplicant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startTime, duration, event, orders, tickets, artist, hall);
    }

    public static final class PerformanceBuilder {
        private Long id;
        private String name;
        private LocalDateTime startTime;
        private Long duration;
        private Event event;
        private Artist artist;
        private Hall hall;
        private List<Order> orders;
        private List<Ticket> tickets;
        private Double priceMultiplicant;

        private PerformanceBuilder() {
        }

        public static Performance.PerformanceBuilder aPerformance() {
            return new Performance.PerformanceBuilder();
        }

        public Performance.PerformanceBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public Performance.PerformanceBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public Performance.PerformanceBuilder withStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Performance.PerformanceBuilder withDuration(Long duration) {
            this.duration = duration;
            return this;
        }

        public Performance.PerformanceBuilder withEvent(Event event) {
            this.event = event;
            return this;
        }

        public Performance.PerformanceBuilder withArtist(Artist artist) {
            this.artist = artist;
            return this;
        }

        public Performance.PerformanceBuilder withHall(Hall hall) {
            this.hall = hall;
            return this;
        }

        public Performance.PerformanceBuilder withTickets(List<Ticket> tickets) {
            this.tickets = tickets;
            return this;
        }

        public Performance.PerformanceBuilder withOrders(List<Order> orders) {
            this.orders = orders;
            return this;
        }

        public Performance.PerformanceBuilder withPriceMultiplicant(Double priceMultiplicant) {
            this.priceMultiplicant = priceMultiplicant;
            return this;
        }

        public Performance build() {
            Performance performance = new Performance();
            performance.setId(id);
            performance.setName(name);
            performance.setStartTime(startTime);
            performance.setDuration(duration);
            performance.setEvent(event);
            performance.setDuration(duration);
            performance.setArtist(artist);
            performance.setHall(hall);
            performance.setTickets(tickets);
            performance.setOrders(orders);
            performance.setPriceMultiplicant(priceMultiplicant);
            return performance;
        }
    }
}
