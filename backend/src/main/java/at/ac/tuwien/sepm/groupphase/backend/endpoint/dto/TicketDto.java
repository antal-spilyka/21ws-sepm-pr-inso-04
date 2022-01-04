package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class TicketDto {
    Integer rowIndex;
    Integer seatIndex;
    String ticketType;
    double price;
    SectorDto sectorDto;
    Long id;

    public Integer getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    public Integer getSeatIndex() {
        return seatIndex;
    }

    public void setSeatIndex(Integer seatIndex) {
        this.seatIndex = seatIndex;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public SectorDto getSectorDto() {
        return sectorDto;
    }

    public void setSectorDto(SectorDto sectorDto) {
        this.sectorDto = sectorDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
