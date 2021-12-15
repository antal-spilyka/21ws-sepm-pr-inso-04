package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SectorDto {
    @NotNull(message = "Color must not be null")
    @Size(min = 7, max = 9, message = "Color must have between 7 and 9 chars")
    private String color;

    @NotNull(message = "Name must not be null")
    @Size(min = 1, message = "Name must not be empty")
    private String name;

    @NotNull(message = "Price must not be null")
    @DecimalMin("0.0")
    private double price;
}
