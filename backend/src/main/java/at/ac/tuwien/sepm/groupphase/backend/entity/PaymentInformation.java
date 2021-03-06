package at.ac.tuwien.sepm.groupphase.backend.entity;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaymentInformationDto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class PaymentInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String creditCardNr;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private ApplicationUser user;

    @Column(length = 100)
    private String creditCardName;

    @Column(length = 100)
    private String creditCardExpirationDate;

    @Column(length = 100)
    private String creditCardCvv;

    @OneToMany(mappedBy = "paymentInformation")
    private List<Order> orders;

    public PaymentInformation() {
    }

    public PaymentInformation(ApplicationUser user, PaymentInformationDto paymentInformationDto) {
        this.user = user;
        this.creditCardName = paymentInformationDto.getCreditCardName();
        this.creditCardNr = paymentInformationDto.getCreditCardNr();
        this.creditCardExpirationDate = paymentInformationDto.getCreditCardExpirationDate();
        this.creditCardCvv = paymentInformationDto.getCreditCardCvv();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
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
