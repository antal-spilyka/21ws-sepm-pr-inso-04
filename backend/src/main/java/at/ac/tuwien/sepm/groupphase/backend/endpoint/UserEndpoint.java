package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @Autowired
    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    /**
     * The registration route for the user.
     */
    @PermitAll
    @PostMapping("")
    public ResponseEntity<String> create(@RequestBody UserDto user) {
        LOGGER.info("POST /api/v1/users");
        try {
            userService.createUser(user);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Error email already used: " + e.getLocalizedMessage(), e);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PermitAll
    @GetMapping("")
    public ApplicationUser get(@RequestBody String email) {
        return userService.findApplicationUserByEmail(email);
    }
}

