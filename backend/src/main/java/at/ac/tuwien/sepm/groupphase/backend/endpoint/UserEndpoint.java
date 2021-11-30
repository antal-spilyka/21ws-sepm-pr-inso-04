package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserEditDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserRegisterDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.MessageMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import javax.annotation.security.PermitAll;
import java.lang.invoke.MethodHandles;

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
    @PostMapping("")
    public ResponseEntity<String> create(@RequestBody @Validated UserRegisterDto user, BindingResult bindingResult) {
        LOGGER.info("POST /api/v1/users" + user.toString());
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Validation failed: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        try {
            userService.createUser(user);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Error email already used: " + e.getLocalizedMessage(), e);
        }
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

        try {
            userService.updateUser(user);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Error email already used: " + e.getLocalizedMessage(), e);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PermitAll
    @GetMapping("")
    public UserDto getUsers(@RequestParam String email) {
        LOGGER.info("GET /api/v1/user " + email);
        try {
            return this.userMapper.applicationUserToUserDto(userService.findApplicationUserByEmail(email));
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No user found with the given e-mail address: " + e.getLocalizedMessage(), e);
        }
    }
}

