package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

public class BasketDto {
    @NotNull(message = "Standing places must not be null")
    int standingPlaces;

    @NotNull(message = "Seats places must not be null")
    List<BasketSeatDto> seats;

    Long paymentInformationId;

    public int getStandingPlaces() {
        return standingPlaces;
    }

    public void setStandingPlaces(int standingPlaces) {
        this.standingPlaces = standingPlaces;
    }

    public List<BasketSeatDto> getSeats() {
        return seats;
    }

    public void setSeats(List<BasketSeatDto> seats) {
        this.seats = seats;
    }

    public Long getPaymentInformationId() {
        return paymentInformationId;
    }

    public void setPaymentInformationId(Long paymentInformationId) {
        this.paymentInformationId = paymentInformationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BasketDto basketDto = (BasketDto) o;
        return Objects.equals(standingPlaces, basketDto.standingPlaces) && Objects.equals(seats, basketDto.seats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(standingPlaces, seats);
    }

    @Override
    public String toString() {
        return "BasketDto{" +
            "standingPlaces=" + standingPlaces +
            ", seats=" + seats +
            '}';
    }
}
