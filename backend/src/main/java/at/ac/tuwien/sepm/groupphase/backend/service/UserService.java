package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserAdminDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserEditDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserRegisterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {

    /**
     * Find a user in the context of Spring Security based on the email address
     * <br>
     * For more information have a look at this tutorial:
     * https://www.baeldung.com/spring-security-authentication-with-a-database
     *
     * @param email the email address
     * @return a Spring Security user
     * @throws UsernameNotFoundException is thrown if the specified user does not exist
     */
    @Override
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    /**
     * Find an application user based on the email address.
     *
     * @param email the email address
     * @return the application user with the given address
     */
    ApplicationUser findApplicationUserByEmail(String email);

    /**
     * Find all application users in the repository with the given email address.
     *
     * @param email address to search for
     * @return list of application users
     */
    List<ApplicationUser> findUsers(String email);

    /**
     * Create an application user.
     *
     * @param user that should be registered
     */
    void createUser(UserRegisterDto user);

    /**
     * Sets the admin attribute of a user.
     *
     * @param request containing the user to be set as admin and the user who sent the request.
     */
    void setAdmin(UserAdminDto request);

    /**
     * Updates an existing user.
     *
     * @param user that should be updated with new/old data
     */
    void updateUser(UserEditDto user);

    /**
     * Increase the lockedCounter of a user.
     *
     * @param email of the user
     */
    void updateLockedCounter(String email);

    /**
     * Reset the lockedCounter of a user.
     *
     * @param email of the user
     */
    void resetLockedCounter(String email);
}
