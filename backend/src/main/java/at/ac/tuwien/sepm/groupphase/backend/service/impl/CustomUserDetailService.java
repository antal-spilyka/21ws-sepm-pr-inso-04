package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailService implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LOGGER.debug("Load all user by email");
        try {
            ApplicationUser applicationUser = findApplicationUserByEmail(email);

            List<GrantedAuthority> grantedAuthorities;
            if (applicationUser.getAdmin()) {
                grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
            } else {
                grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER");
            }

            return new User(applicationUser.getEmail(), applicationUser.getPassword(), grantedAuthorities);
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
    }

    @Override
    public ApplicationUser findApplicationUserByEmail(String email) {
        LOGGER.debug("Find application user by email");
        ApplicationUser applicationUser = userRepository.findUserByEmail(email);
        if (applicationUser == null) {
            throw new NotFoundException(String.format("Could not find the user with the email address %s", email));
        } else {
            userRepository.save(applicationUser);
            return applicationUser;
        }
    }

    @Override
    public List<ApplicationUser> findUsers(String email) {
        LOGGER.debug("Find all application users");
        List<ApplicationUser> users;
        if (email == null || email.length() <= 0 || email.trim().length() == 0 || email.equals("null")) {
            users = userRepository.findAll();
        } else {
            users = userRepository.findByEmailContains(email);
        }
        if (users == null || users.size() <= 0) {
            throw new NotFoundException("No user found in the repository");
        } else {
            userRepository.saveAll(users);
            return users;
        }
    }

    @Override
    public void createUser(UserDto user) {
        LOGGER.debug("Create application user");
        ApplicationUser foundUser = userRepository.findUserByEmail(user.getEmail());
        if (foundUser == null) {
            userRepository.save(new ApplicationUser(user.getEmail(), passwordEncoder.encode(user.getPassword()),
                false, user.getFirstName(), user.getLastName(), user.getSalutation(), user.getPhone(),
                user.getCountry(), user.getCity(), user.getStreet(), user.getDisabled(), user.getZip(), 0));
        } else {
            throw new ServiceException("E-mail already used");
        }
    }

    @Override
    public void updateLockedCounter(String email) {
        LOGGER.debug("Update the locker counter of the user");
        ApplicationUser user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new ServiceException("No user found with the given e-mail address");
        } else {
            user.setLockedCounter(user.getLockedCounter() + 1);
            userRepository.save(user);
        }
    }

    @Override
    public void resetLockedCounter(String email) {
        LOGGER.debug("Reset the locker counter of the user");
        ApplicationUser user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new ServiceException("No user found with the given e-mail address");
        } else {
            user.setLockedCounter(0);
            userRepository.save(user);
        }
    }
}
