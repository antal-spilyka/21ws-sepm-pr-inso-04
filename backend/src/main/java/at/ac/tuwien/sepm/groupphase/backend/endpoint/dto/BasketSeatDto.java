package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class BasketSeatDto {
    @NotNull(message = "Row Index places must not be null")
    int rowIndex;

    @NotNull(message = "Seat Index places must not be null")
    int seatIndex;

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getSeatIndex() {
        return seatIndex;
    }

    public void setSeatIndex(int seatIndex) {
        this.seatIndex = seatIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BasketSeatDto that = (BasketSeatDto) o;
        return Objects.equals(rowIndex, that.rowIndex) && Objects.equals(seatIndex, that.seatIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowIndex, seatIndex);
    }

    @Override
    public String toString() {
        return "BasketSeatDto{" +
            "rowIndex=" + rowIndex +
            ", seatIndex=" + seatIndex +
            '}';
    }
}
