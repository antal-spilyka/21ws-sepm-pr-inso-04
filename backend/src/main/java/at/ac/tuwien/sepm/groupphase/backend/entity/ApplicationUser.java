package at.ac.tuwien.sepm.groupphase.backend.entity;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false)
    private Boolean admin;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, length = 100)
    private String salutation;

    @Column(nullable = false, length = 100)
    private String phone;

    @Column(nullable = false, length = 100)
    private String country;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, length = 100)
    private String street;

    @Column(nullable = false, length = 100)
    private String zip;

    @Column(nullable = false)
    private Boolean disabled;

    @Column(nullable = false)
    @Value("$lockedCounter:0")
    private int lockedCounter;

    public ApplicationUser() {
    }

    public ApplicationUser(String email, String password, Boolean admin, String firstName, String lastName,
                           String salutation, String phone, String country, String city, String street,
                           Boolean disabled, String zip, int lockedCounter) {
        this.email = email;
        this.password = password;
        this.admin = admin;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salutation = salutation;
        this.phone = phone;
        this.country = country;
        this.city = city;
        this.street = street;
        this.disabled = disabled;
        this.zip = zip;
        this.lockedCounter = lockedCounter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
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

<<<<<<< HEAD
=======
    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

>>>>>>> 39483d0f50e6649848093c38422a8ee64306ec66
    public int getLockedCounter() {
        return lockedCounter;
    }

    public void setLockedCounter(int lockedCounter) {
        this.lockedCounter = lockedCounter;
    }

    @Override
    public String toString() {
        return "ApplicationUser{"
            + "id=" + id
            + ", email='" + email + '\''
            + ", password='" + password + '\''
            + ", admin=" + admin
            + ", firstName='" + firstName + '\''
            + ", lastName='" + lastName + '\''
            + ", salutation='" + salutation + '\''
            + ", phone='" + phone + '\''
            + ", country='" + country + '\''
            + ", city='" + city + '\''
            + ", street='" + street + '\''
            + ", zip='" + zip + '\''
            + ", disabled=" + disabled
            + ", locked=" + locked
            + ", lockedCounter=" + lockedCounter
            + '}';
    }

    public static final class ApplicationUserBuilder {
        private Long id;
        private String email;
        private String password;
        private Boolean admin;
        private String firstName;
        private String lastName;
        private String salutation;
        private String phone;
        private String country;
        private String zip;
        private String city;
        private String street;
        private Boolean disabled;
        private int lockedCounter;

        private ApplicationUserBuilder() {
        }

        public static ApplicationUserBuilder aApplicationUser() {
            return new ApplicationUserBuilder();
        }

        public ApplicationUserBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ApplicationUserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public ApplicationUserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public ApplicationUserBuilder withAdmin(Boolean admin) {
            this.admin = admin;
            return this;
        }

        public ApplicationUserBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ApplicationUserBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ApplicationUserBuilder withSalutation(String salutation) {
            this.salutation = salutation;
            return this;
        }

        public ApplicationUserBuilder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public ApplicationUserBuilder withCountry(String country) {
            this.country = country;
            return this;
        }

        public ApplicationUserBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public ApplicationUserBuilder withStreet(String street) {
            this.street = street;
            return this;
        }

        public ApplicationUserBuilder withDisabled(Boolean disabled) {
            this.disabled = disabled;
            return this;
        }

        public ApplicationUserBuilder withZip(String zip) {
            this.zip = zip;
            return this;
        }

        public ApplicationUserBuilder withLockedCounter(int lockedCounter) {
            this.lockedCounter = lockedCounter;
            return this;
        }

        public ApplicationUser build() {
            ApplicationUser applicationUser = new ApplicationUser();
            applicationUser.setId(id);
            applicationUser.setEmail(email);
            applicationUser.setPassword(password);
            applicationUser.setAdmin(admin);
            applicationUser.setFirstName(firstName);
            applicationUser.setLastName(lastName);
            applicationUser.setSalutation(salutation);
            applicationUser.setPhone(phone);
            applicationUser.setCountry(country);
            applicationUser.setCity(city);
            applicationUser.setStreet(street);
            applicationUser.setDisabled(disabled);
            applicationUser.setZip(zip);
            applicationUser.setLockedCounter(lockedCounter);
            return applicationUser;
        }
    }
}
