package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.PaymentInformation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class UserEditDto {
    @NotNull(message = "Email must not be null")
    @Email
    private String email;

    @Email
    private String newEmail;

    @NotNull(message = "Password must not be null")
    @Size(min = 8, message = "Password must not be shorter than 8 digits")
    private String password;

    @NotNull(message = "First name must not be null")
    private String firstName;

    @NotNull(message = "Last name must not be null")
    private String lastName;

    @NotNull(message = "Salutation must not be null")
    private String salutation;

    @NotNull(message = "Phone must not be null")
    private String phone;

    @NotNull(message = "Country must not be null")
    private String country;

    @NotNull(message = "City must not be null")
    private String city;

    @NotNull(message = "Zip must not be null")
    private String zip;

    @NotNull(message = "Street must not be null")
    private String street;

    @NotNull(message = "Disabled must not be null")
    private Boolean disabled;

    private PaymentInformationDto paymentInformation;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public PaymentInformationDto getPaymentInformation() {
        return paymentInformation;
    }

    public void setPaymentInformation(PaymentInformationDto paymentInformation) {
        this.paymentInformation = paymentInformation;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRegisterDto)) {
            return false;
        }
        UserEditDto userEditDto = (UserEditDto) o;
        return Objects.equals(email, userEditDto.email)
            && Objects.equals(password, userEditDto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public String toString() {
        return "UserEditDto{"
            + "email='" + email + '\''
            + ", password='" + password + '\''
            + ", street='" + street + '\''
            + ", paymentInfo='" + paymentInformation + '\''
            + '}';
    }

    public static final class UserEditDtoBuilder {
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private String salutation;
        private String phone;
        private String country;
        private String zip;
        private String city;
        private String street;
        private Boolean disabled;

        private UserEditDtoBuilder() {
        }

        public static UserEditDtoBuilder aUserDto() {
            return new UserEditDtoBuilder();
        }

        public UserEditDtoBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserEditDtoBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserEditDtoBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserEditDtoBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserEditDtoBuilder withSalutation(String salutation) {
            this.salutation = salutation;
            return this;
        }

        public UserEditDtoBuilder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserEditDtoBuilder withCountry(String country) {
            this.country = country;
            return this;
        }

        public UserEditDtoBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public UserEditDtoBuilder withStreet(String street) {
            this.street = street;
            return this;
        }

        public UserEditDtoBuilder withZip(String zip) {
            this.zip = zip;
            return this;
        }

        public UserEditDtoBuilder withDisabled(Boolean disabled) {
            this.disabled = disabled;
            return this;
        }

        public UserEditDto build() {
            UserEditDto userEditDto = new UserEditDto();
            userEditDto.setEmail(email);
            userEditDto.setPassword(password);
            userEditDto.setFirstName(firstName);
            userEditDto.setLastName(lastName);
            userEditDto.setSalutation(salutation);
            userEditDto.setPhone(phone);
            userEditDto.setCountry(country);
            userEditDto.setCity(city);
            userEditDto.setStreet(street);
            userEditDto.setZip(zip);
            userEditDto.setDisabled(disabled);
            return userEditDto;
        }
    }
}
