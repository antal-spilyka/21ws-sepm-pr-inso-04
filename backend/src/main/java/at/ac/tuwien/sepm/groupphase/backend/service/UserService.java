package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
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
     * @throws UsernameNotFoundException is thrown if the specified user does not exists
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
     * Find all application users in the repository.
     *
     * @return list of application users
     */
    List<ApplicationUser> findAllUsers();

    /**
     * Create an application user.
     *
     * @param user that should be registered
     */
    void createUser(UserDto user);

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
