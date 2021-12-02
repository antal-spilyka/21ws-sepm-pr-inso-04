package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaymentInformationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserEditDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserRegisterDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserLoginDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @BeforeEach
    public void beforeEach() {
        userRepository.deleteAll();
    }

    @Test
    public void createUser_shouldEnableLoginAndBeAbleToAccessRoute() throws Exception {
        String body = objectMapper.writeValueAsString(user1);

        // Register
        MvcResult mvcResult = this.mockMvc.perform(post(USER_BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        String body2 = objectMapper.writeValueAsString(UserLoginDto.UserLoginDtoBuilder.anUserLoginDto()
            .withEmail(user1.getEmail()).withPassword(user1.getPassword()).build());

        // Login
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
            UserRegisterDto.UserRegisterDtoBuilder user = UserRegisterDto.UserRegisterDtoBuilder.aUserRegisterDto();
            for (int j = 0; j < 10; j++) {

                if (j != i) {
                    switch (j) {
                        case 0:
                            user.withEmail("testUserFields@email.com");
                        case 1:
                            user.withPassword("password");
                        case 2:
                            user.withCity("Wien");
                        case 3:
                            user.withCountry("AL");
                        case 4:
                            user.withDisabled(false);
                        case 5:
                            user.withFirstName("Gucci");
                        case 6:
                            user.withLastName("King");
                        case 7:
                            user.withPhone("0664 123 456");
                        case 8:
                            user.withStreet("street 1");
                        case 9:
                            user.withZip("1010");
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
        String body = objectMapper.writeValueAsString(user1);
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
        String body2 = objectMapper.writeValueAsString(UserLoginDto.UserLoginDtoBuilder.anUserLoginDto()
            .withEmail(user1.getEmail()).withPassword(user1.getPassword()).build());

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
        String body2 = objectMapper.writeValueAsString(UserLoginDto.UserLoginDtoBuilder.anUserLoginDto()
            .withEmail(lockedUser.getEmail()).withPassword(lockedUser.getPassword()).build());

        MvcResult mvcResult2 = this.mockMvc.perform(post(AUTHENTICATION_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body2))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult2.getResponse();
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

    @Test
    public void getAllUsersWithoutLogin_shouldReturnHttpStatusForbidden() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(USER_BASE_URI))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    public void updateUserWithoutFields_shouldThrowException() throws Exception {
        PaymentInformationDto paymentInformation = new PaymentInformationDto();
        paymentInformation.setCreditCardNr("1234123412341234");
        paymentInformation.setCreditCardExpirationDate("202022");
        paymentInformation.setCreditCardCvv("123");
        paymentInformation.setCreditCardName("Test");

        for (int i = 0; i < 10; i++) {
            UserEditDto.UserEditDtoBuilder user = UserEditDto.UserEditDtoBuilder.aUserDto();
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
                        case 10: user.withPaymentInformation(paymentInformation);
                        case 11: user.withNewEmail("testNewEmail@email.com");
                    }
                }
            }
            String body = objectMapper.writeValueAsString(user.build());
            MvcResult mvcResult = this.mockMvc.perform(put(USER_BASE_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
                .andDo(print())
                .andReturn();

            MockHttpServletResponse response = mvcResult.getResponse();
            assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus());
        }
    }

    @Test
    public void updateUserWithExistingEmail_shouldThrowException() throws Exception {
        ApplicationUser user1 = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
            .withEmail("test2@email.com")
            .withPassword("password").withAdmin(true).withId(1L).withCity("Wien")
            .withCountry("AL").withDisabled(false).withFirstName("Gucci").withLastName("King").withPhone("0664 123 456")
            .withSalutation("mr").withStreet("street 1").withZip("1010").withLockedCounter(0).build();

        ApplicationUser user2 = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
            .withEmail("test3@email.com")
            .withPassword("password").withAdmin(true).withId(1L).withCity("Wien")
            .withCountry("AL").withDisabled(false).withFirstName("Gucci").withLastName("King").withPhone("0664 123 456")
            .withSalutation("mr").withStreet("street 1").withZip("1010").withLockedCounter(0).build();

        UserEditDto toUpdateUser = UserEditDto.UserEditDtoBuilder.aUserDto()
            .withEmail("test2@email.com").withNewEmail("test3@email.com")
            .withPassword("password").withCity("Wien")
            .withCountry("AL").withDisabled(false).withFirstName("Gucci").withLastName("King").withPhone("0664 123 456")
            .withSalutation("mr").withStreet("street 1").withZip("1010").build();

        // Post first user
        String body = objectMapper.writeValueAsString(user1);
        MvcResult mvcResult = this.mockMvc.perform(post(USER_BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response1 = mvcResult.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response1.getStatus());

        // Post second User
        body = objectMapper.writeValueAsString(user2);
        mvcResult = this.mockMvc.perform(post(USER_BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response2 = mvcResult.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response2.getStatus());

        // Update Second User with Email of first User
        body = objectMapper.writeValueAsString(toUpdateUser);
        MvcResult mvcResult2 = this.mockMvc.perform(put(USER_BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response3 = mvcResult2.getResponse();
        assertEquals(HttpStatus.CONFLICT.value(), response3.getStatus());

    }

    @Test
    public void getAllUsersWithoutAdminRights_shouldReturnHttpStatusForbidden() throws Exception {
        String body = objectMapper.writeValueAsString(user1);

        // Register
        MvcResult mvcResult = this.mockMvc.perform(post(USER_BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        String body2 = objectMapper.writeValueAsString(UserLoginDto.UserLoginDtoBuilder.anUserLoginDto()
            .withEmail(user1.getEmail()).withPassword(user1.getPassword()).build());

        // Login
        MvcResult mvcResult2 = this.mockMvc.perform(post(AUTHENTICATION_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body2))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response2 = mvcResult2.getResponse();
        assertEquals(HttpStatus.OK.value(), response2.getStatus());

        MvcResult mvcResult3 = this.mockMvc.perform(get(USER_BASE_URI)
                .header(securityProperties.getAuthHeader(), response2.getContentAsString()))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response3 = mvcResult3.getResponse();
        assertEquals(HttpStatus.FORBIDDEN.value(), response3.getStatus());
    }
}
