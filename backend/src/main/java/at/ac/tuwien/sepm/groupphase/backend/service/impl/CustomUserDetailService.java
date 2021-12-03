package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserAdminDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserEditDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserRegisterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.PaymentInformation;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.PaymentInformationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class CustomUserDetailService implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PaymentInformationRepository paymentInformationRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository, PasswordEncoder passwordEncoder, PaymentInformationRepository paymentInformationRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.paymentInformationRepository = paymentInformationRepository;
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
        List<ApplicationUser> users;
        if (email == null || email.length() <= 0 || email.trim().length() == 0 || email.equals("null")) {
            users = userRepository.findAll();
        } else {
            users = userRepository.findByEmailContains(email);
        }
        return users;
    }

    @Override
    public void createUser(UserRegisterDto user) {
        LOGGER.debug("Create application user");
        if (user == null) {
            throw new ServiceException("Please fill out all the mandatory fields");
        }
        ApplicationUser foundUser = userRepository.findUserByEmail(user.getEmail());
        if (foundUser != null) {
            throw new ServiceException("E-mail already used");
        } else {
            userRepository.save(new ApplicationUser(user.getEmail(), passwordEncoder.encode(user.getPassword()),
                false, user.getFirstName(), user.getLastName(), user.getSalutation(), user.getPhone(),
                user.getCountry(), user.getCity(), user.getStreet(), user.getDisabled(), user.getZip(), 0));
        }
    }

    @Override
    @Transactional
    public void setAdmin(UserAdminDto request) {
        if (request.getAdminEmail() == null || userRepository.findUserByEmail(request.getAdminEmail()) == null) {
            throw new ServiceException("No administrator found with the given e-mail");
        } else if (request.getAdmin() == null || userRepository.findUserByEmail(request.getEmail()) == null) {
            throw new ServiceException("No user found with the given e-mail");
        } else if (request.getEmail().equals(request.getAdminEmail())) {
            throw new ServiceException("You can not change your own admin rights");
        } else {
            ApplicationUser currentUser = userRepository.findUserByEmail(request.getEmail());
            currentUser.setAdmin(request.getAdmin());
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
                throw new ServiceException("E-mail already used");
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
            toUpdateUser.setDisabled(updatedUser.getDisabled());
            if (updatedUser.getPaymentInformation() != null) {
                PaymentInformation paymentInformation;
                if (toUpdateUser.getPaymentInformation() == null) {
                    paymentInformation = new PaymentInformation();
                } else {
                    paymentInformation = paymentInformationRepository.findByUser(toUpdateUser);
                }
                paymentInformation.setUser(toUpdateUser);
                paymentInformation.setCreditCardName(updatedUser.getPaymentInformation().getCreditCardName());
                paymentInformation.setCreditCardCvv(updatedUser.getPaymentInformation().getCreditCardCvv());
                paymentInformation.setCreditCardExpirationDate(updatedUser.getPaymentInformation().getCreditCardExpirationDate());
                paymentInformation.setCreditCardNr(updatedUser.getPaymentInformation().getCreditCardNr());
                paymentInformationRepository.save(paymentInformation);
            }
            userRepository.save(toUpdateUser);
        } else {
            throw new ServiceException("No User found");
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
