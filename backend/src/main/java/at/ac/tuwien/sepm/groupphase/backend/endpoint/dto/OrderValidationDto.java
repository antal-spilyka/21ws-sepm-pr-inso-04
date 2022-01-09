package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.List;
import java.util.Objects;

public class OrderValidationDto {
    private boolean valid;
    private List<TicketDto> tickets;
    private String firstName;
    private String lastName;
    private String comment;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public List<TicketDto> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketDto> tickets) {
        this.tickets = tickets;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderValidationDto that = (OrderValidationDto) o;
        return valid == that.valid && Objects.equals(tickets, that.tickets) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valid, tickets, firstName, lastName);
    }

    @Override
    public String toString() {
        return "OrderValidationDto{" +
            "valid=" + valid +
            ", tickets=" + tickets +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            '}';
    }

    public static final class OrderValidationDtoBuilder {
        private boolean valid;
        private List<TicketDto> tickets;
        private String firstName;
        private String lastName;
        private String comment;

        public static OrderValidationDto.OrderValidationDtoBuilder anOrderValidationDto() {
            return new OrderValidationDto.OrderValidationDtoBuilder();
        }

        public OrderValidationDtoBuilder withValid(boolean valid) {
            this.valid = valid;
            return this;
        }

        public OrderValidationDtoBuilder withTickets(List<TicketDto> tickets) {
            this.tickets = tickets;
            return this;
        }

        public OrderValidationDtoBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public OrderValidationDtoBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public OrderValidationDtoBuilder withComment(String comment) {
            this.comment = comment;
            return this;
        }

        public OrderValidationDto build() {
            OrderValidationDto orderValidationDto = new OrderValidationDto();
            orderValidationDto.setValid(this.valid);
            orderValidationDto.setTickets(this.tickets);
            orderValidationDto.setFirstName(this.firstName);
            orderValidationDto.setLastName(this.lastName);
            orderValidationDto.setComment(this.comment);
            return orderValidationDto;
        }
    }
}
