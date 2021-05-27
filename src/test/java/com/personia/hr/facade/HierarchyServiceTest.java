package com.personia.hr.facade;

import com.personia.hr.model.EmployeeDto;
import com.personia.hr.model.Hierarchy;
import com.personia.hr.parser.JsonEmployeeToSupervisorParser;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HierarchyServiceTest {

    @InjectMocks
    private HierarchyService hierarchyService;

    @Mock
    private JsonEmployeeToSupervisorParser parser;

    @Mock
    private Hierarchy hierarchy;

    @Test
    @SneakyThrows
     void shouldReturnEmptyHierarchyWhenReceivesNoSupervisors() {
        when(parser.parse(any(String.class))).thenReturn(new HashMap<>());
        when(hierarchy.getRoot()).thenReturn(EmployeeDto.builder().build());
        hierarchyService.update("{}");
        verify(hierarchy).clear();
        verify(hierarchy).getRoot();
        verifyNoMoreInteractions(hierarchy);
    }

    @Test
    @SneakyThrows
     void shouldReturnBasicHierarchyWhenReceivesOneSupervisor() {
        when(parser.parse(any(String.class))).thenReturn(Map.of("Pete","Barbara","Barbara","Nick"));
        when(hierarchy.getRoot()).thenReturn(EmployeeDto.builder().build());
        hierarchyService.update("{}");
        verify(parser).parse(any(String.class));
        verify(hierarchy).clear();
        verify(hierarchy).getRoot();
        verify(hierarchy).add("Pete","Barbara");
        verify(hierarchy).add("Barbara","Nick");
        verifyNoMoreInteractions(hierarchy);
    }

    @Test
    void shouldReturnSubHierarchyWhenReceivesEmployeesSupervisorsRequest() {
        EmployeeDto employeeDto = hierarchyService.supervisors("Pete");
        assertThat(employeeDto.getName()).isEqualTo("Sophie");
        assertThat(employeeDto.getTeam().size()).isEqualTo(1);
        assertThat(employeeDto.getTeam().get(0).getName()).isEqualTo("Nick");
    }

}