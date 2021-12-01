package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class EventPlaceDto {
    String name;
    AddressDto addressDto;

    @NotNull()
    @NotBlank
    public String getName() {
        return name;
    }

    @NotNull()
    public AddressDto getAddressDto() {
        return addressDto;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddressDto(AddressDto addressDto) {
        this.addressDto = addressDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventPlaceDto that = (EventPlaceDto) o;
        return Objects.equals(name, that.name) && Objects.equals(addressDto, that.addressDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, addressDto);
    }

    @Override
    public String toString() {
        return "EventPlaceDto{" +
            "name='" + name + '\'' +
            ", addressDto=" + addressDto +
            '}';
    }

}
