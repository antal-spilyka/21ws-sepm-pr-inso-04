package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class SetOrderToBoughtDto {
    private Long orderId;
    private Long paymentInformationId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getPaymentInformationId() {
        return paymentInformationId;
    }

    public void setPaymentInformationId(Long paymentInformationId) {
        this.paymentInformationId = paymentInformationId;
    }
}
