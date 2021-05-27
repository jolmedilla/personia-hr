package com.personia.hr.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.personia.hr.exception.EmployeeHasTwoSupervisorsException;
import com.personia.hr.exception.LoopInEmployeeHierarchyException;
import com.personia.hr.exception.MultipleRootHierarchyException;
import com.personia.hr.model.EmployeeDto;
import com.personia.hr.model.Hierarchy;
import com.personia.hr.parser.JsonEmployeeToSupervisorParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

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
     void shouldReturnEmptyHierarchyWhenReceivesNoSupervisors() throws EmployeeHasTwoSupervisorsException, MultipleRootHierarchyException, LoopInEmployeeHierarchyException, JsonProcessingException {
        when(parser.parse(any(String.class))).thenReturn(new HashMap<>());
        when(hierarchy.getRoot()).thenReturn(EmployeeDto.builder().build());
        hierarchyService.update("{}");
        verify(hierarchy).clear();
        verify(hierarchy).getRoot();
        verifyNoMoreInteractions(hierarchy);
    }

    @Test
     void shouldReturnBasicHierarchyWhenReceivesOneSupervisor() throws EmployeeHasTwoSupervisorsException, MultipleRootHierarchyException, LoopInEmployeeHierarchyException, JsonProcessingException {
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

}