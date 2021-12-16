package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaymentInformationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserEditDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserRegisterDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.PaymentInformation;
import at.ac.tuwien.sepm.groupphase.backend.exception.ConflictException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.PaymentInformationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.SeenNewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.aspectj.lang.annotation.Before;
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

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailService implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final SeenNewsRepository seenNewsRepository;
    private final PaymentInformationRepository paymentInformationRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, PaymentInformationRepository paymentInformationRepository, SeenNewsRepository seenNewsRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.paymentInformationRepository = paymentInformationRepository;
        this.seenNewsRepository = seenNewsRepository;
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
            return applicationUser;
        }
    }

    @Override
    public List<ApplicationUser> findUsers(String email) {
        LOGGER.debug("Find all application users");
        try {
            List<ApplicationUser> users;
            if (email == null || email.length() <= 0 || email.trim().length() == 0 || email.equals("null")) {
                users = userRepository.findAll();
            } else {
                users = userRepository.findByEmailContains(email);
            }
            return users;
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void createUser(UserRegisterDto user) {
        LOGGER.debug("Create application user");
        if (user == null) {
            throw new IllegalArgumentException("Please fill out all the mandatory fields");
        }
        ApplicationUser foundUser = userRepository.findUserByEmail(user.getEmail());
        if (foundUser != null) {
            throw new ContextException("E-mail already used");
        } else {
            userRepository.save(new ApplicationUser(user.getEmail(), passwordEncoder.encode(user.getPassword()),
                false, user.getFirstName(), user.getLastName(), user.getSalutation(), user.getPhone(),
                user.getCountry(), user.getCity(), user.getStreet(), user.getDisabled(), user.getZip(), 0));
        }
    }

    @Override
    @Transactional
    public void setAdmin(String email, Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new ConflictException("No administrator found with the given e-mail");
        } else if (email == null || userRepository.findUserByEmail(email) == null) {
            throw new NotFoundException("No user found with the given e-mail");
        } else if (principal.getName().equals(email)) {
            throw new ConflictException("You can not change your own admin rights");
        } else {
            ApplicationUser currentUser = userRepository.findUserByEmail(email);
            currentUser.setAdmin(!currentUser.getAdmin()); // changing the admin rights of the user
            userRepository.save(currentUser);
        }
    }

    @Override
    @Transactional
    public void updateUser(UserEditDto updatedUser) {
        LOGGER.trace("Update existing user");
        ApplicationUser toUpdateUser = userRepository.findUserByEmail(updatedUser.getEmail());

        if (updatedUser.getNewEmail() != null) {
            if (userRepository.findUserByEmail(updatedUser.getNewEmail()) != null && !updatedUser.getNewEmail().equals(updatedUser.getEmail())) {
                throw new ContextException("E-mail already used");
            }
        }

        if (toUpdateUser != null) {
            toUpdateUser.setCity(updatedUser.getCity());
            toUpdateUser.setCountry(updatedUser.getCountry());
            toUpdateUser.setStreet(updatedUser.getStreet());
            toUpdateUser.setZip(updatedUser.getZip());
            if (!updatedUser.getPassword().equals(toUpdateUser.getPassword())) {
                toUpdateUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
            toUpdateUser.setSalutation(updatedUser.getSalutation());
            toUpdateUser.setFirstName(updatedUser.getFirstName());
            toUpdateUser.setLastName(updatedUser.getLastName());
            toUpdateUser.setAdmin(updatedUser.getAdmin());
            if (updatedUser.getNewEmail() == null) {
                toUpdateUser.setEmail(updatedUser.getEmail());
            } else {
                toUpdateUser.setEmail(updatedUser.getNewEmail());
            }
            this.deletePaymentInformations(updatedUser);
            if (!updatedUser.getPaymentInformation().isEmpty()) {
                List<PaymentInformation> paymentInformationList = new ArrayList<>();
                for (PaymentInformationDto e : updatedUser.getPaymentInformation()) {
                    PaymentInformation p = userMapper.paymentInformationDtoToPaymentInformation(e);
                    p.setUser(toUpdateUser);
                    paymentInformationList.add(p);
                }
                paymentInformationRepository.saveAll(paymentInformationList);
            }
            userRepository.save(toUpdateUser);
        } else {
            throw new NotFoundException("No User found");
        }
    }

    // removes all existing paymentInformations of updatedUser to owerwrite the new data
    @Transactional
    public void deletePaymentInformations(UserEditDto updatedUser) {
        ApplicationUser user = userRepository.findUserByEmail(updatedUser.getEmail());
        if (user == null) {
            throw new NotFoundException("No user found with the given e-mail address");
        }
        List<PaymentInformation> paymentInformationList = paymentInformationRepository.findByUser(user);
        for (PaymentInformation e : paymentInformationList) {
            paymentInformationRepository.deleteById(e.getId());
        }
    }

    @Override
    @Transactional
    public void deleteUser(String email) {
        LOGGER.debug("Delete user with the email {}", email);
        if (email == null || userRepository.findUserByEmail(email) == null) {
            throw new NotFoundException("No user found with the given e-mail address");
        } else {
            ApplicationUser userToDelete = userRepository.findUserByEmail(email);
            seenNewsRepository.deleteByUser(userToDelete);
            userRepository.deleteById(userToDelete.getId());
        }
    }

    @Override
    public void updateLockedCounter(String email) {
        LOGGER.debug("Update the locker counter of the user");
        ApplicationUser user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new NotFoundException("No user found with the given e-mail address");
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
            throw new NotFoundException("No user found with the given e-mail address");
        } else {
            user.setLockedCounter(0);
            userRepository.save(user);
        }
    }
}
