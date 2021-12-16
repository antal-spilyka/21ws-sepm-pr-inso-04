package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotNull;

public class HallAddDto {
    @NotNull(message = "Name must not be null")
    private String name;

    @NotNull(message = "Rows must not be null")
    private HallplanElementDto[][] rows;

    @NotNull(message = "Sectors must not be null")
    private SectorDto[] sectors;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HallplanElementDto[][] getRows() {
        return rows;
    }

    public void setRows(HallplanElementDto[][] rows) {
        this.rows = rows;
    }

    public SectorDto[] getSectors() {
        return sectors;
    }

    public void setSectors(SectorDto[] sectors) {
        this.sectors = sectors;
    }
}
