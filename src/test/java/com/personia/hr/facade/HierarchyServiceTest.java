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
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
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
        EmployeeDto sophie = EmployeeDto.builder()
                .name("Sophie")
                .build();
        EmployeeDto nick = EmployeeDto.builder()
                .name("Nick").supervisor(sophie).build();
        sophie.add(nick);
        when(hierarchy.findEmployee(any(String.class)))
                .thenReturn(Optional.of(EmployeeDto.builder().name("Pete").supervisor(nick).build())
                        );
        hierarchyService.supervisors("Pete").ifPresentOrElse( e -> {
            assertThat(e.getName()).isEqualTo("Sophie");
            assertThat(e.getTeam().size()).isEqualTo(1);
            assertThat(e.getTeam().get(0).getName()).isEqualTo("Nick");
        },()->fail("Hierarchy Service return Optional.empty and we expected Sophie->Nick"));
    }

}