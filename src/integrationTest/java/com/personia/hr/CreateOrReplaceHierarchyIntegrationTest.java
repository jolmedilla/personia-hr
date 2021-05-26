package com.personia.hr;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.personia.hr.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CreateOrReplaceHierarchyIntegrationTest {

    private static final String SAMPLE_INPUT_HIERARCHY = "{ " +
            " \"Pete\": \"Nick\"," +
            " \"Barbara\": \"Nick\"," +
            " \"Nick\": \"Sophie\"," +
            " \"Sophie\": \"Jonas\"" +
            "}";

    private static final String EXPECTED_OUTPUT = "{" +
            "          \"Jonas\": {" +
            "              \"Sophie\": {" +
            "                  \"Nick\": {" +
            "                      \"Pete\": {}," +
            "                      \"Barbara\": {}" +
            "                  }" +
            "} }" +
            "}";

    private static final String TWO_SUPERVISORS_SAMPLE ="{" +
            " \"Pete\": \"Nick\" ," +
            " \"Pete\": \"Barbara\"}";

    private static final String LOOP_IN_HIERARCHY ="{" +
            " \"Pete\": \"Nick\" ," +
            " \"Nick\": \"Pete\"}";

    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp() {
        employeeRepository.deleteAll();
    }

    @Test
    public void shouldReturnOk() throws Exception {

        mockMvc.perform(put("/api/v1/hierarchies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(SAMPLE_INPUT_HIERARCHY))
                .andExpect(content().json(EXPECTED_OUTPUT))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings ={TWO_SUPERVISORS_SAMPLE,LOOP_IN_HIERARCHY})
    public void shouldReturnBadRequest(String requestBody) throws Exception {
        mockMvc.perform(put("/api/v1/hierarchies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

}
