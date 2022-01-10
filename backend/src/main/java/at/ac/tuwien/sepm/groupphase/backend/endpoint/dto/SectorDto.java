package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

public class SectorDto {
    @Null
    private Long id;

    @NotNull(message = "Color must not be null")
    @Size(min = 7, max = 9, message = "Color must have between 7 and 9 chars")
    private String color;

    @NotNull(message = "Name must not be null")
    @Size(min = 1, message = "Name must not be empty")
    private String name;

    @NotNull(message = "Price must not be null")
    @DecimalMin("0.0")
    private double price;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
