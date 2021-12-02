package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class UserRegisterDto {
    @NotNull(message = "Email must not be null")
    @Email
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRegisterDto)) {
            return false;
        }
        UserRegisterDto userRegisterDto = (UserRegisterDto) o;
        return Objects.equals(email, userRegisterDto.email)
            && Objects.equals(password, userRegisterDto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public String toString() {
        return "UserRegisterDto{"
            + "email='" + email + '\''
            + ", password='" + password + '\''
            + '}';
    }

    public static final class UserRegisterDtoBuilder {
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
        private Boolean locked;
        private int lockedCounter;

        private UserRegisterDtoBuilder() {
        }

        public static UserRegisterDtoBuilder aUserRegisterDto() {
            return new UserRegisterDto.UserRegisterDtoBuilder();
        }

        public UserRegisterDtoBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserRegisterDtoBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserRegisterDtoBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserRegisterDtoBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserRegisterDtoBuilder withSalutation(String salutation) {
            this.salutation = salutation;
            return this;
        }

        public UserRegisterDtoBuilder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserRegisterDtoBuilder withCountry(String country) {
            this.country = country;
            return this;
        }

        public UserRegisterDtoBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public UserRegisterDtoBuilder withStreet(String street) {
            this.street = street;
            return this;
        }

        public UserRegisterDtoBuilder withDisabled(Boolean disabled) {
            this.disabled = disabled;
            return this;
        }

        public UserRegisterDtoBuilder withZip(String zip) {
            this.zip = zip;
            return this;
        }

        public UserRegisterDtoBuilder withLocked(Boolean locked) {
            this.locked = locked;
            return this;
        }

        public UserRegisterDtoBuilder withLockedCounter(int lockedCounter) {
            this.lockedCounter = lockedCounter;
            return this;
        }

        public UserRegisterDto build() {
            UserRegisterDto userDto = new UserRegisterDto();
            userDto.setEmail(email);
            userDto.setPassword(password);
            userDto.setFirstName(firstName);
            userDto.setLastName(lastName);
            userDto.setSalutation(salutation);
            userDto.setPhone(phone);
            userDto.setCountry(country);
            userDto.setCity(city);
            userDto.setStreet(street);
            userDto.setDisabled(disabled);
            userDto.setZip(zip);
            return userDto;
        }
    }
}

