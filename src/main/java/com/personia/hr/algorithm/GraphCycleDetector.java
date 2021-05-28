package com.personia.hr.algorithm;

import com.personia.hr.exception.LoopInEmployeeHierarchyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class GraphCycleDetector {

    private final FloydsTraverseAlgorithm floydsTraverseAlgorithm;

    /**
     * Partial implementation of Floyd's tortoise and hare algorithm, only detection of cycle
     * (see See <a href="https://en.wikipedia.org/wiki/Cycle_detection">http://https://en.wikipedia.org/wiki/Cycle_detection</a>)
     * This part of the algorithm is O(mu) being mu the position in the map of the start of the first cycle.
     * The whole algorithm, including floyds travers, will be under O(lambda+mu)
     *
     * @param map the mapping function from employee to supervisor
     * @throws LoopInEmployeeHierarchyException if a loop is detected
     */
    public void execute(final Map<String,String> map) throws LoopInEmployeeHierarchyException {
        if (!map.isEmpty()) {
            for (String element: map.keySet()) {
                if (floydsTraverseAlgorithm.traverse(map,element)) {
                    throw new LoopInEmployeeHierarchyException();
                }
            }
        }
    }


}
