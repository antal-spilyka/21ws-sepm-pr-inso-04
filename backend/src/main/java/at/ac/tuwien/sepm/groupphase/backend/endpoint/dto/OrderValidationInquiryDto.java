package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.Objects;

public class OrderValidationInquiryDto {
    private Long id;
    private String hash;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderValidationInquiryDto that = (OrderValidationInquiryDto) o;
        return Objects.equals(id, that.id) && Objects.equals(hash, that.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hash);
    }

    @Override
    public String toString() {
        return "OrderValidationInquiryDto{" +
            "id=" + id +
            ", hash='" + hash + '\'' +
            '}';
    }
}
