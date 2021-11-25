package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserLoginDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserEndpointTest implements TestData {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    private final ApplicationUser user = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
        .withEmail("test@email.com")
        .withPassword("password").withAdmin(true).withId(1L).withCity("Wien")
        .withCountry("AL").withDisabled(false).withFirstName("Gucci").withLastName("King").withPhone("0664 123 456")
        .withSalutation("mr").withStreet("street 1").withZip("1010").withLockedCounter(0).build();

    @BeforeEach
    public void beforeEach() {
        userRepository.deleteAll();
    }

    @Test
    public void createUser_shouldEnableLoginAndBeAbleToAccessRoute() throws Exception {
        ApplicationUser user = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
            .withEmail("test@email.com")
            .withPassword("password").withAdmin(true).withId(1L).withCity("Wien")
            .withCountry("AL").withDisabled(false).withFirstName("Gucci").withLastName("King").withPhone("0664 123 456")
            .withSalutation("mr").withStreet("street 1").withZip("1010").withLockedCounter(0).build();

        String body = objectMapper.writeValueAsString(user);

        MvcResult mvcResult = this.mockMvc.perform(post(USER_BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        String body2 = objectMapper.writeValueAsString(UserLoginDto.UserLoginDtoBuilder.anUserLoginDto()
            .withEmail(user.getEmail()).withPassword(user.getPassword()).build());

        MvcResult mvcResult2 = this.mockMvc.perform(post(AUTHENTICATION_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body2))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response2 = mvcResult2.getResponse();
        assertEquals(HttpStatus.OK.value(), response2.getStatus());

        // this should be changed to the event list route in the future
        MvcResult mvcResult3 = this.mockMvc.perform(get(MESSAGE_BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .header(securityProperties.getAuthHeader(), response2.getContentAsString()))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response3 = mvcResult3.getResponse();
        assertEquals(HttpStatus.OK.value(), response3.getStatus());
    }

    @Test
    public void createUserWithoutFields_shouldThrowException() throws Exception {
        for (int i = 0; i < 10; i++) {
            UserDto.UserDtoBuilder user = UserDto.UserDtoBuilder.aUserDto();
            for (int j = 0; j < 10; j++) {

                if (j != i) {
                    switch (j) {
                        case 0: user.withEmail("testUserFields@email.com");
                        case 1: user.withPassword("password");
                        case 2: user.withCity("Wien");
                        case 3: user.withCountry("AL");
                        case 4: user.withDisabled(false);
                        case 5: user.withFirstName("Gucci");
                        case 6: user.withLastName("King");
                        case 7: user.withPhone("0664 123 456");
                        case 8: user.withStreet("street 1");
                        case 9: user.withZip("1010");
                    }
                }
            }
            String body = objectMapper.writeValueAsString(user.build());
            MvcResult mvcResult = this.mockMvc.perform(post(USER_BASE_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
                .andDo(print())
                .andReturn();

            MockHttpServletResponse response = mvcResult.getResponse();
            assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus());
        }
    }

    @Test
    public void createUserWithSameEmailTwice_shouldThrowException() throws Exception {
        ApplicationUser user = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
            .withEmail("test2@email.com")
            .withPassword("password").withAdmin(true).withId(1L).withCity("Wien")
            .withCountry("AL").withDisabled(false).withFirstName("Gucci").withLastName("King").withPhone("0664 123 456")
            .withSalutation("mr").withStreet("street 1").withZip("1010").withLockedCounter(0).build();

        String body = objectMapper.writeValueAsString(user);
        MvcResult mvcResult = this.mockMvc.perform(post(USER_BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        MvcResult mvcResult2 = this.mockMvc.perform(post(USER_BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response2 = mvcResult2.getResponse();
        assertEquals(HttpStatus.CONFLICT.value(), response2.getStatus());
    }

    @Test
    public void loginWithoutRegistration_shouldReturnHttpStatusUnauthorized() throws Exception {
        ApplicationUser user = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
            .withEmail("test@email.com")
            .withPassword("password").withAdmin(true).withId(1L).withCity("Wien")
            .withCountry("AL").withDisabled(false).withFirstName("Gucci").withLastName("King").withPhone("0664 123 456")
            .withSalutation("mr").withStreet("street 1").withZip("1010").withLockedCounter(0).build();

        String body2 = objectMapper.writeValueAsString(UserLoginDto.UserLoginDtoBuilder.anUserLoginDto()
            .withEmail(user.getEmail()).withPassword(user.getPassword()).build());

            MvcResult mvcResult2 = this.mockMvc.perform(post(AUTHENTICATION_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body2))
                .andDo(print())
                .andReturn();
            MockHttpServletResponse response = mvcResult2.getResponse();
            assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

    @Test
    public void lockedUser_shouldReturnHttpStatusUnauthorized() throws Exception {
        ApplicationUser user = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
            .withEmail("test@email.com")
            .withPassword("password").withAdmin(true).withId(1L).withCity("Wien")
            .withCountry("AL").withDisabled(false).withFirstName("Gucci").withLastName("King").withPhone("0664 123 456")
            .withSalutation("mr").withStreet("street 1").withZip("1010").withLockedCounter(5).build();

        String body2 = objectMapper.writeValueAsString(UserLoginDto.UserLoginDtoBuilder.anUserLoginDto()
            .withEmail(user.getEmail()).withPassword(user.getPassword()).build());

        MvcResult mvcResult2 = this.mockMvc.perform(post(AUTHENTICATION_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body2))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult2.getResponse();
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

}
