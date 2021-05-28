package com.personia.hr;

import com.personia.hr.exception.EmployeeHasTwoSupervisorsException;
import com.personia.hr.exception.LoopInEmployeeHierarchyException;
import com.personia.hr.exception.MultipleRootHierarchyException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Definition of tests that any implementation of the this User story must comply with.
 * User Story: CREATE/REPLACE COMPANY HIERARCHY
 */
interface CreateOrReplaceHierarchyTest extends PersoniaHrBaseTest {

    static Stream<Arguments> provideWrongSamplesAndResponseMessages() {
        return Stream.of(
                Arguments.of(CreateOrReplaceHierarchyInMemoryImplTest.TWO_SUPERVISORS_SAMPLE, EmployeeHasTwoSupervisorsException.REASON),
                Arguments.of(CreateOrReplaceHierarchyInMemoryImplTest.LOOP_IN_HIERARCHY, LoopInEmployeeHierarchyException.REASON),
                Arguments.of(CreateOrReplaceHierarchyInMemoryImplTest.MULTIPLE_ROOT_SAMPLE, MultipleRootHierarchyException.REASON)
        );
    }

    @Test
    default void shouldReturnOkWhenReceivingWellFormedHierarchy() throws Exception {

        getMockMvc().perform(put("/api/v1/hierarchies")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(createHeaders(USERNAME,PASSWORD))
                .content(FIRST_SAMPLE_INPUT_HIERARCHY))
                .andExpect(content().json(FIRST_EXPECTED_OUTPUT))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    default void shouldReturnOkWhenReplacingExistingHierarchy() {

        getMockMvc().perform(put("/api/v1/hierarchies")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(createHeaders(USERNAME,PASSWORD))
                .content(FIRST_SAMPLE_INPUT_HIERARCHY))
                .andExpect(content().json(FIRST_EXPECTED_OUTPUT))
                .andExpect(status().isOk());
        getMockMvc().perform(put("/api/v1/hierarchies")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(createHeaders(USERNAME,PASSWORD))
                .content(SECOND_SAMPLE_INPUT_HIERARCHY))
                .andExpect(content().json(SECOND_EXPECTED_OUTPUT))
                .andExpect(status().isOk());
    }

    @ParameterizedTest(name = "shoudReturnBadRequestWithCorrectErrorMessage, request=\"{0}\", error=\"{1}\"")
    @MethodSource("provideWrongSamplesAndResponseMessages")
    @SneakyThrows
    default void shouldReturnBadRequestWithCorrectErrorMessage(String requestBody, String reason) {
        getMockMvc().perform(put("/api/v1/hierarchies")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(createHeaders(USERNAME,PASSWORD))
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(reason));
    }
}
