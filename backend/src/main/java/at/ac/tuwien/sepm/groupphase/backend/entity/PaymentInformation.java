package at.ac.tuwien.sepm.groupphase.backend.entity;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaymentInformationDto;

import javax.persistence.*;

@Entity
public class PaymentInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private ApplicationUser user;

    @Column(length = 100)
    private String creditCardName;

    @Column(length = 100)
    private String creditCardNr;

    @Column(length = 100)
    private String creditCardExpirationDate;

    @Column(length = 100)
    private String creditCardCvv;

    public PaymentInformation() {
    }

    public PaymentInformation(ApplicationUser user, PaymentInformationDto paymentInformationDto) {
        this.user = user;
        this.creditCardName = paymentInformationDto.getCreditCardName();
        this.creditCardNr = paymentInformationDto.getCreditCardNr();
        this.creditCardExpirationDate = paymentInformationDto.getCreditCardExpirationDate();
        this.creditCardCvv = paymentInformationDto.getCreditCardCvv();
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

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
        return "PaymentInformation{"
            + "id='" + id + '\''
            + "creditCardName='" + creditCardName + '\''
            + ", creditCardNr='" + creditCardNr + '\''
            + ", creditCardExpirationDate='" + creditCardExpirationDate + '\''
            + ", creditCardCvv='" + creditCardCvv + '\''
            + '}';
    }
}
