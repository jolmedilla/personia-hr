package com.personia.hr.algorithm;

import com.personia.hr.exception.LoopInEmployeeHierarchyException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class FloydsTraverseAlgorithm {

    /**
     * Implementation of the first bit, cycle detection, of Floyd's tortoise and hare algorithm
     * (see See <a href="https://en.wikipedia.org/wiki/Cycle_detection">http://https://en.wikipedia.org/wiki/Cycle_detection</a>)
     * This part of the algorithm is O(lambda) being lambda the length of the cycle or the way to the top of the
     * hierarchy if there is no loop starting at this node
     *
     * @param map the function with that maps employee->supervisor
     * @param x_0 the employee for which we want to traverse the map up to the root
     * @return true if there is a loop starting at this employee
     */
    public boolean traverse(final Map<String,String> map, String x_0) {
        String tortoise = map.get(x_0);
        String hare = map.get(tortoise);
        while (tortoise != null && hare != null) {
            if (tortoise.equals(hare)) {
                return true;
            } else {
                tortoise = map.get(tortoise);
                hare = map.get(map.getOrDefault(hare,hare));
            }
        }
        return false;
    }
}
