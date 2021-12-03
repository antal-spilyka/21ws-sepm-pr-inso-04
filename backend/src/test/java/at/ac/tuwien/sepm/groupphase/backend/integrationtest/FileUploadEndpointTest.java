package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedMessageDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.MessageInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleMessageDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.MessageMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Message;
import at.ac.tuwien.sepm.groupphase.backend.repository.MessageRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class FileUploadEndpointTest implements TestData {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void notFoundFile_shouldThrowException() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(
            MockMvcRequestBuilders.get(FILE_URI + "/esel.jpg")
        )
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void uploadFile_shouldBeShown() throws Exception {
        File f = new File("test-data/esel.jpg");
        FileInputStream inputStream = new FileInputStream(f);
        final MockMultipartFile image = new MockMultipartFile("file", "esel.png", "image/png", inputStream);
        MvcResult mvcResult1 = this.mockMvc.perform(
            MockMvcRequestBuilders.multipart(FILE_URI).file(image)
                .contentType(MediaType.MULTIPART_FORM_DATA)
        )
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response1 = mvcResult1.getResponse();
        String uri = JsonPath.read(response1.getContentAsString(), "$.uri");
        assertEquals(HttpStatus.OK.value(), response1.getStatus());


        MvcResult mvcResult2 = this.mockMvc.perform(
            MockMvcRequestBuilders.get(uri)
        )
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response2 = mvcResult2.getResponse();
        assertEquals(HttpStatus.OK.value(), response2.getStatus());
    }
}
