package com.personia.hr;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"toggles.use-persistence=true"})
@AutoConfigureMockMvc
public class SecurityTest extends BaseTest {

    @Test
    void shouldReturnUnauthorizedWhenReceivingRequest() throws Exception {
        getMockMvc().perform(put("/api/v1/hierarchies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(FIRST_SAMPLE_INPUT_HIERARCHY))
                .andExpect(status().isUnauthorized());
    }
}
