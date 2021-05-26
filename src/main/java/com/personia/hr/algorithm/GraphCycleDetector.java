package com.personia.hr.algorithm;

import com.personia.hr.exception.LoopInEmployeeHierarchyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class GraphCycleDetector {

    private final FloydsTraverseAlgorithm floydsTraverseAlgorithm;

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
