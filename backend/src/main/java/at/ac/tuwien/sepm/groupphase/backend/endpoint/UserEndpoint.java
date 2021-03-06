package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserEditDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserRegisterDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * This endpoint is used for kubernetes health checks.
 */
@RestController
@RequestMapping(UserEndpoint.BASE_URL)
public class UserEndpoint {
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/users";

    private final UserMapper userMapper;

    @Autowired
    public UserEndpoint(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    /**
     * The registration route for the user.
     */
    @PermitAll
    @PostMapping("/register")
    public ResponseEntity<String> create(@RequestBody @Validated UserRegisterDto user, BindingResult bindingResult, HttpServletRequest request) {
        LOGGER.info("POST " + BASE_URL + " " + user.toString());
        if (bindingResult.hasErrors()) {
            ObjectError e = bindingResult.getAllErrors().get(0);
            LOGGER.error(e.getDefaultMessage(), e);
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Validation failed: " + e.getDefaultMessage());
        }
        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    /**
     * Updates an existing user.
     */
    @PermitAll
    @PutMapping("")
    public ResponseEntity<String> update(@RequestBody @Validated UserEditDto user, BindingResult bindingResult) {
        LOGGER.info("PUT /api/v1/users" + user.toString());
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Validation failed: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    /**
     * Updates the admin rights of an existing user.
     */
    @Secured("ROLE_ADMIN")
    @PutMapping("/{email}")
    public ResponseEntity<String> setAdmin(@PathVariable String email, Principal principal) {
        LOGGER.info("PUT /api/v1/users/{}", email);
        userService.setAdmin(email, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Updates the admin rights of an existing user.
     */
    @Secured("ROLE_ADMIN")
    @PutMapping("/{email}/unlock")
    public ResponseEntity<String> unlock(@PathVariable String email, Principal principal) {
        LOGGER.info("PUT /api/v1/users/{}/unlock", email);
        userService.unlock(email, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Finds all users with the given email.
     */
    @Secured("ROLE_ADMIN")
    @GetMapping
    public List<UserDto> findUsers(String email) {
        LOGGER.info("GET " + BASE_URL + "?email=" + email);
        List<ApplicationUser> result = userService.findUsers(email);
        List<UserDto> returnValue = new ArrayList<>();
        for (ApplicationUser user : result) {
            if (user != null) {
                returnValue.add(userMapper.applicationUserToUserDto(user));
            }
        }
        return returnValue;
    }

    /**
     * Finds the user with the given email.
     */
    @PermitAll
    @GetMapping("/{email}")
    public UserDto getUser(@PathVariable String email) {
        LOGGER.info("GET " + BASE_URL + "/{}", email);
        return this.userMapper.applicationUserToUserDto(userService.findApplicationUserByEmail(email));
    }

    /**
     * Deletes the user with the given email.
     */
    @PermitAll
    @DeleteMapping("/{email}")
    public void deleteUser(@PathVariable String email) {
        LOGGER.info("DELETE " + BASE_URL + "/{}", email);
        try {
            this.userService.deleteUser(email);
        } catch (NotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with the given e-mail address: " + e.getLocalizedMessage(), e);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete user: " + e.getLocalizedMessage(), e);
        }
    }

    /**
     * Resets the password of the user with the given email.
     */
    @PermitAll
    @GetMapping("/{email}/resetPassword")
    public void resetPassword(@PathVariable String email) {
        LOGGER.info("GET" + BASE_URL + "/{}/resetPassword", email);
        try {
            this.userService.sendEmailToResetPassword(email);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with the given e-mail address: " + e.getLocalizedMessage(), e);
        }
    }

    /**
     * Feature for admins to add a user.
     */
    @Secured("ROLE_ADMIN")
    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody @Validated UserEditDto user, BindingResult bindingResult) {
        LOGGER.info("POST /api/v1/users/add " + user.toString());
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Validation failed: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}

