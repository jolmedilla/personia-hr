package com.personia.hr.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personia.hr.exception.EmployeeHasTwoSupervisorsException;
import com.personia.hr.exception.MultipleRootHierarchyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JsonEmployeeToSupervisorParserTest {

    static final String EMPLOYEE_WITH_TWO_SUPERVISORS_STRING = "{\"Pete\":\"Barbara\",\"Pete\":\"Sophie\"}";
    static final Map<String, String> EMPLOYEE_WITH_TWO_SUPERVISORS_MAP = Map.of("Pete", "Sophie");

    @InjectMocks
    private JsonEmployeeToSupervisorParser jsonEmployeeToSupervisorParser;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    public void shouldThrowExceptionWhenEmployeeHasTwoSupervisors() throws JsonProcessingException {
        when(objectMapper.readValue(any(String.class), eq(Map.class))).thenReturn(EMPLOYEE_WITH_TWO_SUPERVISORS_MAP);
        assertThrows(EmployeeHasTwoSupervisorsException.class, () -> jsonEmployeeToSupervisorParser.parse(EMPLOYEE_WITH_TWO_SUPERVISORS_STRING));
    }
}