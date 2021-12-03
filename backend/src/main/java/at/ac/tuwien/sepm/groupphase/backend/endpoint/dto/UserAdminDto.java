package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserAdminDto {
    @NotNull(message = "Admin email must not be null")
    @Email
    private String adminEmail;

    @NotNull(message = "Email must not be null")
    @Email
    private String email;

    @NotNull(message = "Admin must not be null")
    @Email
    private Boolean admin;

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "UserDto{"
            + "adminEmail='" + adminEmail + '\''
            + ", email='" + email + '\''
            + ", admin='" + admin + '\''
            + '}';
    }

    public static final class UserAdminDtoBuilder {
        private String adminEmail;
        private String email;
        private Boolean admin;

        private UserAdminDtoBuilder() {
        }

        public static UserAdminDto.UserAdminDtoBuilder anUserAdminDto() {
            return new UserAdminDto.UserAdminDtoBuilder();
        }

        public UserAdminDto.UserAdminDtoBuilder withAdminEmail(String adminEmail) {
            this.adminEmail = adminEmail;
            return this;
        }

        public UserAdminDto.UserAdminDtoBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserAdminDto.UserAdminDtoBuilder withAdmin(Boolean admin) {
            this.admin = admin;
            return this;
        }

        public UserAdminDto build() {
            UserAdminDto userAdminDto = new UserAdminDto();
            userAdminDto.setAdminEmail(adminEmail);
            userAdminDto.setEmail(email);
            userAdminDto.setAdmin(admin);
            return userAdminDto;
        }
    }
}
