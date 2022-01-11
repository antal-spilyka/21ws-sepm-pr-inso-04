package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaymentInformationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserEditDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserLoginDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserRegisterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.repository.PaymentInformationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.SeenNewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserEndpointTest implements TestData {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentInformationRepository paymentInformationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SeenNewsRepository seenNewsRepository;

    @Autowired
    private SecurityProperties securityProperties;

    @BeforeEach
    public void beforeEach() {
        seenNewsRepository.deleteAll();
        paymentInformationRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void createUser_shouldEnableLoginAndBeAbleToAccessRoute() throws Exception {
        String body = objectMapper.writeValueAsString(user1);

        // Register
        MvcResult mvcResult = this.mockMvc.perform(post(USER_BASE_URI + "/register")
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
        MvcResult mvcResult3 = this.mockMvc.perform(get(EVENT_BASE_URI)
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
            MvcResult mvcResult = this.mockMvc.perform(post(USER_BASE_URI + "/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
                .andDo(print())
                .andReturn();

            MockHttpServletResponse response = mvcResult.getResponse();
            assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus());
        }
    }

    @Test
    public void createUserWithSameEmailTwice_shouldReturnHttpStatusConflict() throws Exception {
        String body = objectMapper.writeValueAsString(user1);
        MvcResult mvcResult = this.mockMvc.perform(post(USER_BASE_URI + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        MvcResult mvcResult2 = this.mockMvc.perform(post(USER_BASE_URI + "/register")
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

        for (int i = 0; i < 13; i++) {
            UserEditDto.UserEditDtoBuilder user = UserEditDto.UserEditDtoBuilder.aUserDto();
            for (int j = 0; j < 13; j++) {
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
                        case 10:
                            user.withPaymentInformation(paymentInformation);
                        case 11:
                            user.withNewEmail("testNewEmail@email.com");
                        case 12:
                            user.withAdmin(false);
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
        ApplicationUser user = ApplicationUser.ApplicationUserBuilder.aApplicationUser()
            .withEmail("test@email.com")
            .withPassword("password").withAdmin(false).withId(1L).withCity("Wien")
            .withCountry("AL").withDisabled(false).withFirstName("Gucci").withLastName("King").withPhone("0664 123 456")
            .withSalutation("mr").withStreet("street 1").withZip("1010").withLockedCounter(0).build();

        UserEditDto toUpdateUser = UserEditDto.UserEditDtoBuilder.aUserDto()
            .withEmail("test2@email.com").withNewEmail("test@email.com")
            .withPassword("password").withAdmin(false).withCity("Wien")
            .withCountry("AL").withDisabled(false).withFirstName("Gucci").withLastName("King").withPhone("0664 123 456")
            .withSalutation("mr").withStreet("street 1").withZip("1010").build();

        // Register first user
        String body = objectMapper.writeValueAsString(user);
        MvcResult mvcResult = this.mockMvc.perform(post(USER_BASE_URI + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response1 = mvcResult.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response1.getStatus());

        // Register second User
        body = objectMapper.writeValueAsString(toUpdateUser);
        mvcResult = this.mockMvc.perform(post(USER_BASE_URI + "/register")
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
        MvcResult mvcResult = this.mockMvc.perform(post(USER_BASE_URI + "/register")
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

    @Test
    public void setAdminWithoutAdminRights_shouldReturnHttpStatusForbidden() throws Exception {
        String body = objectMapper.writeValueAsString(user2);

        // Register user to be changed
        MvcResult mvcResult = this.mockMvc.perform(post(USER_BASE_URI + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        body = objectMapper.writeValueAsString(user1);

        // Register the second user
        mvcResult = this.mockMvc.perform(post(USER_BASE_URI + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andReturn();

        response = mvcResult.getResponse();
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

        // Make user admin
        MvcResult mvcResult3 = this.mockMvc.perform(put(USER_BASE_URI + "/" + user2.getEmail())
                .header(securityProperties.getAuthHeader(), response2.getContentAsString()))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response3 = mvcResult3.getResponse();
        assertEquals(HttpStatus.FORBIDDEN.value(), response3.getStatus());
    }

    @Test
    public void deletingOthersAccountShouldReturnHttpStatusForbidden() throws Exception {
        String body = objectMapper.writeValueAsString(user1);

        // Register user to be changed
        MvcResult mvcResult = this.mockMvc.perform(post(USER_BASE_URI + "/register")
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

        MvcResult mvcResult3 = this.mockMvc.perform(put(USER_BASE_URI + "/" + user2.getEmail())
            .header(securityProperties.getAuthHeader(), response2.getContentAsString()))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response3 = mvcResult3.getResponse();
        assertEquals(HttpStatus.FORBIDDEN.value(), response3.getStatus());
    }

    @Test
    public void addUserWithoutLogin_shouldReturnHttpStatusForbidden() throws Exception {
        // Register admin
        String bodyRegister = objectMapper.writeValueAsString(user3);
        MvcResult mvcResultRegister = this.mockMvc.perform(post(USER_BASE_URI + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyRegister))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse responseRegister = mvcResultRegister.getResponse();
        assertEquals(HttpStatus.CREATED.value(), responseRegister.getStatus());

        // Login
        String bodyLogin = objectMapper.writeValueAsString(UserLoginDto.UserLoginDtoBuilder.anUserLoginDto()
            .withEmail(user3.getEmail()).withPassword(user3.getPassword()).build());

        MvcResult mvcResultLogin = this.mockMvc.perform(post(AUTHENTICATION_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyLogin))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse responseLogin = mvcResultLogin.getResponse();
        assertEquals(HttpStatus.OK.value(), responseLogin.getStatus());

        UserEditDto user = UserEditDto.UserEditDtoBuilder.aUserDto()
            .withEmail(newUser1.getEmail())
            .withPassword("testPassword")
            .withFirstName("test")
            .withLastName("person")
            .withSalutation("mr")
            .withPhone("+430101011010")
            .withAdmin(true)
            .withDisabled(false)
            .withCountry("Austria")
            .withCity("Vienna")
            .withStreet("Test Street")
            .withZip("12345")
            .build();
        String body = objectMapper.writeValueAsString(user);
        MvcResult mvcResult = this.mockMvc.perform(post(USER_BASE_URI + "/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

}
