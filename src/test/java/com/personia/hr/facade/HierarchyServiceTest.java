package com.personia.hr.facade;

import com.personia.hr.exception.EmployeeHasTwoSupervisorsException;
import com.personia.hr.exception.LoopInEmployeeHierarchyException;
import com.personia.hr.exception.MultipleRootHierarchyException;
import com.personia.hr.model.Hierarchy;
import com.personia.hr.model.Team;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HierarchyServiceTest {

    private final HierarchyService hierarchyService = new HierarchyService();

    private static Stream<Arguments> provideLoopsInHierarchy() {
        return Stream.of(
                Arguments.of(Map.ofEntries(entry("Pete","Barbara"),
                        entry("Barbara","Nick"),
                        entry("Sophie", "Nick"),
                        entry("Nick","Pete") )),
                Arguments.of(Map.ofEntries(entry("Pete","Barbara"),
                        entry("Barbara","Nick"),
                        entry("Nick","Pete"))));
    }

    @Test
    public void shouldReturnEmptyHierarchyWhenReceivesNoSupervisors() throws EmployeeHasTwoSupervisorsException, MultipleRootHierarchyException, LoopInEmployeeHierarchyException {
        Hierarchy hierarchy = hierarchyService.update(Collections.emptyMap());
        assertThat(hierarchy.getSupervisor()).isNull();
    }

    @Test
    public void shouldReturnBasicHierarchyWhenReceivesOneSupervisor() throws EmployeeHasTwoSupervisorsException, MultipleRootHierarchyException, LoopInEmployeeHierarchyException {
        Hierarchy hierarchy = hierarchyService.update(Collections.singletonMap("Pete","Nick"));
        assertThat(hierarchy.getSupervisor()).isEqualTo("Nick");
        assertThat(hierarchy.getTeam()).isEqualTo(Team.builder()
                .hierarchyList(Collections.singletonList(Hierarchy.builder().supervisor("Pete").build())).build());
    }

    @ParameterizedTest
    @MethodSource("provideLoopsInHierarchy")
    public void shouldThrowExceptionWhenThereIsALoop(Map<String,String> relationships)  {
        assertThrows(LoopInEmployeeHierarchyException.class,() ->hierarchyService.update(relationships));
    }

}