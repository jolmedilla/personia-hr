package com.personia.hr;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Definition of tests that any implementation of the this User story must comply with.
 * User Story: CREATE/REPLACE COMPANY HIERARCHY
 */
interface GetSupervisorAndSupervisorsSupervisorsIntegrationTest extends PersoniaHrBaseIntegrationTest{

    String FIRST_SAMPLE_EMPLOYEE = "Pete";

    String FIRST_SAMPLE_EMPLOYEE_EXPECTED_RESPONSE = "{\"Sophie\": {\"Nick\": {}}}";

    @SneakyThrows
    @Test
    default void shouldReturnEmployeesSupervisorAndSupervisorsSupervisor() {
        getMockMvc().perform(put("/api/v1/hierarchies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(FIRST_SAMPLE_INPUT_HIERARCHY))
                .andExpect(content().json(FIRST_EXPECTED_OUTPUT))
                .andExpect(status().isOk());
        getMockMvc().perform(get("/api/v1/hierarchies/employees/"
                +FIRST_SAMPLE_EMPLOYEE+"/supervisors"))
                .andExpect(status().isOk())
                .andExpect(content().json(FIRST_SAMPLE_EMPLOYEE_EXPECTED_RESPONSE));
    }
}
