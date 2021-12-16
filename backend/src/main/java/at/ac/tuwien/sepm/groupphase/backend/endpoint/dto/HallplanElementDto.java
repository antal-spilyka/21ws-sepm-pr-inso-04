package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotNull;

public class HallplanElementDto {
    @NotNull(message = "Added must not be null")
    private boolean added;

    @NotNull(message = "Sector must not be null")
    private int sector;

    @NotNull(message = "Type must not be null")
    private String type;

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public int getSector() {
        return sector;
    }

    public void setSector(int sector) {
        this.sector = sector;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
