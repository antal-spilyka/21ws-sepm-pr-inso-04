package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ArtistEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    @Test
    public void createAndSearchForArtist() throws Exception {
        ArtistDto artistDto = new ArtistDto();
        artistDto.setDescription("an artist");
        artistDto.setBandName("testArtist");

        String body = objectMapper.writeValueAsString(artistDto);

        MvcResult mvcResult = this.mockMvc.perform(post(TestData.ARTIST_BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(TestData.ADMIN_USER, TestData.ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        ArtistSearchDto artistSearchDto = new ArtistSearchDto();
        artistSearchDto.setMisc(artistDto.getBandName());
        artistSearchDto.setPage(0);
        MvcResult mvcResult2 = this.mockMvc.perform(get(TestData.ARTIST_BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(TestData.ADMIN_USER, TestData.ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response2 = mvcResult2.getResponse();
        assertEquals(HttpStatus.OK.value(), response2.getStatus());

        ArtistDto artistDtoRet = objectMapper.readValue(response.getContentAsString(), ArtistDto.class);
        artistDto.setId(artistDtoRet.getId());
        assertEquals(artistDtoRet, artistDto);

    }

    @Test
    public void SearchForNothing() throws Exception {
        ArtistSearchDto artistSearchDto = new ArtistSearchDto();
        artistSearchDto.setMisc("not existing");
        artistSearchDto.setPage(0);
        MvcResult mvcResult = this.mockMvc.perform(get(TestData.ARTIST_BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(TestData.ADMIN_USER, TestData.USER_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        assertEquals("[]", response.getContentAsString());
    }
}
