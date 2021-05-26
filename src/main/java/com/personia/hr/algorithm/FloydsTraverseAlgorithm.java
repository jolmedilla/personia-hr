package com.personia.hr.algorithm;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FloydsTraverseAlgorithm {

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
