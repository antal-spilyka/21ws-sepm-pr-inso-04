package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotNull;

public class PaymentInformationDto {
    @NotNull(message = "creditCardName must not be null")
    private String creditCardName;

    @NotNull(message = "creditCardNr must not be null")
    private String creditCardNr;

    @NotNull(message = "creditCardExpirationDate must not be null")
    private String creditCardExpirationDate;

    @NotNull(message = "creditCardCvv must not be null")
    private String creditCardCvv;

    public String getCreditCardName() {
        return creditCardName;
    }

    public void setCreditCardName(String creditCardName) {
        this.creditCardName = creditCardName;
    }

    public String getCreditCardNr() {
        return creditCardNr;
    }

    public void setCreditCardNr(String creditCardNr) {
        this.creditCardNr = creditCardNr;
    }

    public String getCreditCardExpirationDate() {
        return creditCardExpirationDate;
    }

    public void setCreditCardExpirationDate(String creditCardExpirationDate) {
        this.creditCardExpirationDate = creditCardExpirationDate;
    }

    public String getCreditCardCvv() {
        return creditCardCvv;
    }

    public void setCreditCardCvv(String creditCardCvv) {
        this.creditCardCvv = creditCardCvv;
    }

    @Override
    public String toString() {
        return "PaymentInformationDto{"
            + "creditCardName='" + creditCardName + '\''
            + ", creditCardNr='" + creditCardNr + '\''
            + ", creditCardExpirationDate='" + creditCardExpirationDate + '\''
            + ", creditCardCvv='" + creditCardCvv + '\''
            + '}';
    }
}
