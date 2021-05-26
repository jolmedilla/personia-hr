package com.personia.hr.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personia.hr.algorithm.FloydsTraverseAlgorithm;
import com.personia.hr.algorithm.GraphCycleDetector;
import com.personia.hr.exception.EmployeeHasTwoSupervisorsException;
import com.personia.hr.exception.LoopInEmployeeHierarchyException;
import com.personia.hr.exception.MultipleRootHierarchyException;
import com.personia.hr.model.EmployeeDto;
import com.personia.hr.model.HierarchySimpleImpl;
import com.personia.hr.parser.JsonEmployeeToSupervisorParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HierarchyServiceTest {

    private final HierarchyService hierarchyService = new HierarchyService(
            new JsonEmployeeToSupervisorParser(
                    new ObjectMapper(),
                    new GraphCycleDetector(new FloydsTraverseAlgorithm())),
            new HierarchySimpleImpl());

    static Stream<Arguments> provideCorrectSamplesWithTheirRoots() {
        return Stream.of(
                Arguments.of("{\"Pete\":\"Nick\"}", "Nick"),
                Arguments.of("{\"Pete\":\"Nick\",\"Barbara\":\"Nick\",\"Nick\":\"Sophie\"}", "Sophie"),
                Arguments.of("{\"Pe,te\":\"Nick\"}", "Nick")
        );
    }

    static Stream<Arguments> provideWrongSamplesAndTheirException() {
        return Stream.of(
                Arguments.of("{\"Pete\":\"Barbara\",\"Barbara\":\"Nick\",\"Sophie\":\"Nick\",\"Nick\":\"Pete\"}", LoopInEmployeeHierarchyException.class),
                Arguments.of("{\"Pete\":\"Barbara\",\"Barbara\":\"Nick\",\"Nick\":\"Pete\"}", LoopInEmployeeHierarchyException.class),
                Arguments.of("{\"Andrew\":\"Sophie\",\"Pete\":\"Barbara\",\"Barbara\":\"Nick\",\"Nick\":\"Pete\",\"Sophie\":\"Juan\"}", LoopInEmployeeHierarchyException.class),
                Arguments.of("{\"Pete\":\"Barbara\",\"Nick\":\"Sophie\"}", MultipleRootHierarchyException.class),
                Arguments.of("{\"Pete\":\"Barbara\",\"Pete\":\"Sophie\"}", EmployeeHasTwoSupervisorsException.class)

        );
    }

    @Test
    public void shouldReturnEmptyHierarchyWhenReceivesNoSupervisors() throws EmployeeHasTwoSupervisorsException, MultipleRootHierarchyException, LoopInEmployeeHierarchyException, JsonProcessingException {
        EmployeeDto hierarchy = hierarchyService.update("{}");
        assertThat(hierarchy.getName()).isNull();
    }

    @ParameterizedTest
    @MethodSource("provideCorrectSamplesWithTheirRoots")
    public void shouldReturnBasicHierarchyWhenReceivesOneSupervisor(String relationships, String root) throws EmployeeHasTwoSupervisorsException, MultipleRootHierarchyException, LoopInEmployeeHierarchyException, JsonProcessingException {
        EmployeeDto hierarchy = hierarchyService.update(relationships);
        assertThat(hierarchy.getName()).isEqualTo(root);
    }

    @ParameterizedTest
    @MethodSource("provideWrongSamplesAndTheirException")
    public void shouldThrowException(String relationships, Class<Throwable> clazz)  {
        assertThrows(clazz,() ->hierarchyService.update(relationships));
    }

}