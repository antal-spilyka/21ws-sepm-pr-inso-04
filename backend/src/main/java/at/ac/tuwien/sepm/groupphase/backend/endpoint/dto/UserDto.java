package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class UserDto {
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

    @NotNull(message = "Locked must not be null")
    private Boolean locked;

    @NotNull(message = "LockedCounter must not be null")
    private int lockedCounter;

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

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public int getLockedCounter() {
        return lockedCounter;
    }

    public void setLockedCounter(int lockedCounter) {
        this.lockedCounter = lockedCounter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserDto)) {
            return false;
        }
        UserDto userRegisterDto = (UserDto) o;
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

    public static final class UserDtoBuilder {
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

        private UserDtoBuilder() {
        }

        public static UserDto.UserDtoBuilder aUserDto() {
            return new UserDto.UserDtoBuilder();
        }

        public UserDto.UserDtoBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserDto.UserDtoBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserDto.UserDtoBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserDto.UserDtoBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserDto.UserDtoBuilder withSalutation(String salutation) {
            this.salutation = salutation;
            return this;
        }

        public UserDto.UserDtoBuilder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserDto.UserDtoBuilder withCountry(String country) {
            this.country = country;
            return this;
        }

        public UserDto.UserDtoBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public UserDto.UserDtoBuilder withStreet(String street) {
            this.street = street;
            return this;
        }

        public UserDto.UserDtoBuilder withDisabled(Boolean disabled) {
            this.disabled = disabled;
            return this;
        }

        public UserDto.UserDtoBuilder withZip(String zip) {
            this.zip = zip;
            return this;
        }

        public UserDto.UserDtoBuilder withLocked(Boolean locked) {
            this.locked = locked;
            return this;
        }

        public UserDto.UserDtoBuilder withLockedCounter(int lockedCounter) {
            this.lockedCounter = lockedCounter;
            return this;
        }

        public UserDto build() {
            UserDto userDto = new UserDto();
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
            userDto.setLocked(locked);
            userDto.setLockedCounter(lockedCounter);
            return userDto;
        }
    }
}

