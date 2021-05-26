package com.personia.hr.algorithm;

import com.personia.hr.exception.LoopInEmployeeHierarchyException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class GraphCycleDetectorTest {

    static final Map<String,String> TWO_BRANCH_SAMPLE_WITH_LOOP =
            Map.of("Andrew","Sophie","Pete","Barbara","Barbara","Nick","Nick","Pete","Sophie","Juan");

    static final Map<String,Boolean> TWO_BRANCH_SAMPLE_WITH_LOOP_NODE_TRAVERSE_IS_CYCLE =
            Map.of("Andrew",false,"Pete",true,"Barbara",true,"Nick",true,"Sophie",false);

    static final Map<String,String> SINGLE_BRANCH_LOOP_SAMPLE_WITH_LOOP = Map.of("Pete","Barbara","Barbara","Nick","Nick","Pete")   ;

    static final Map<String,Boolean> SINGLE_BRANCH_LOOP_SAMPLE_WITH_LOOP_NODE_TRAVER_IS_CYCLE =
            Map.of("Pete",true,"Barbara",true,"Nick",true);

    static Stream<Arguments> provideFailureDefinitions() {
        return Stream.of(Arguments.of(TWO_BRANCH_SAMPLE_WITH_LOOP,TWO_BRANCH_SAMPLE_WITH_LOOP_NODE_TRAVERSE_IS_CYCLE),
                Arguments.of(SINGLE_BRANCH_LOOP_SAMPLE_WITH_LOOP,SINGLE_BRANCH_LOOP_SAMPLE_WITH_LOOP_NODE_TRAVER_IS_CYCLE)
                );
    }

    static Stream<Arguments> provideSuccessDefinitions() {
        return Stream.of(
                Arguments.of(Map.of("Pete","Nick")),
                Arguments.of(Map.of("Pete","Nick","Barbara","Nick","Nick","Sophie")),
                Arguments.of(Map.of("Pe,te","Nick"))
        );
    }
    
    @InjectMocks
    private GraphCycleDetector graphCycleDetector;

    @Mock
    private FloydsTraverseAlgorithm floydsTraverseAlgorithm;

    @ParameterizedTest
    @MethodSource("provideFailureDefinitions")
    public void shouldDetectCycle(Map<String,String> map, Map<String,Boolean> isCycle) {
        isCycle.forEach((key,value) -> lenient().when(floydsTraverseAlgorithm.traverse(any(Map.class),eq(key))).thenReturn(value));
        assertThrows(LoopInEmployeeHierarchyException.class,()->graphCycleDetector.execute(map));
    }

    @ParameterizedTest
    @MethodSource("provideSuccessDefinitions")
    public void shouldNotDetectCycle(Map<String,String> map) {
        map.forEach((key,value) -> lenient().when(floydsTraverseAlgorithm.traverse(any(Map.class),eq(key))).thenReturn(false));
        assertDoesNotThrow(()->graphCycleDetector.execute(map));
    }
    

}