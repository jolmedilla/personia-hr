package com.personia.hr.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personia.hr.algorithm.GraphCycleDetector;
import com.personia.hr.exception.EmployeeHasTwoSupervisorsException;
import com.personia.hr.exception.LoopInEmployeeHierarchyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class JsonEmployeeToSupervisorParser {

    private final ObjectMapper objectMapper ;

    private final GraphCycleDetector graphCycleDetector;

    public Map<String,String> parse(final String text) throws EmployeeHasTwoSupervisorsException,
            JsonProcessingException, LoopInEmployeeHierarchyException {
        Map<String,String> nodes = objectMapper.readValue(text,Map.class);
        Pattern pattern = Pattern.compile("\\\"[^\"]*\\\"[ ]*:[ ]*\\\"[^\"]*\\\"[,]?");
        Matcher matcher = pattern.matcher(text);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        if (nodes.size() != count) {
            throw new EmployeeHasTwoSupervisorsException();
        }
        graphCycleDetector.execute(nodes);
        return nodes;
    }
}
