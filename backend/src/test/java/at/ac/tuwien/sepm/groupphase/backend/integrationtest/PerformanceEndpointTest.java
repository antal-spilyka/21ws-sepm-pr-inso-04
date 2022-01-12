package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.repository.AddressRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventPlaceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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

import static at.ac.tuwien.sepm.groupphase.backend.basetest.TestData.PERFORMANCE_URI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class PerformanceEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private PerformanceRepository performanceRepository;

    @BeforeAll
    public void beforeAll() {
        performanceRepository.deleteAll();
    }

    @Test
    public void findPerformanceByNonExistingId_shouldReturnHttpStatusNotFound() throws Exception{
        MvcResult mvcResult = this.mockMvc.perform(get(PERFORMANCE_URI + "/123456789")
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void findPerformanceByNonExistingDateTime_shouldReturnHttpStatusNotFound() throws Exception{
        String body = objectMapper.writeValueAsString(TestData.performanceToFind);

        MvcResult mvcResult = this.mockMvc.perform(get(PERFORMANCE_URI + "/search")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(TestData.ADMIN_USER, TestData.ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        // Since there is no data added yet
        assertEquals(response.getContentLength(), 0);
    }

    @Test
    public void findPerformanceByNonExistingArtist_shouldReturnHttpStatusNotFound() throws Exception{
        String body = objectMapper.writeValueAsString(TestData.performanceToFind);

        MvcResult mvcResult = this.mockMvc.perform(get(PERFORMANCE_URI + "/artist/12345")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(TestData.ADMIN_USER, TestData.ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        // Since there is no data added yet
        assertEquals(response.getContentLength(), 0);
    }
}
