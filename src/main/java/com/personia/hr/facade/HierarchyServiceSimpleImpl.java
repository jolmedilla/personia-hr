package com.personia.hr.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personia.hr.exception.EmployeeHasTwoSupervisorsException;
import com.personia.hr.exception.LoopInEmployeeHierarchyException;
import com.personia.hr.exception.MultipleRootHierarchyException;
import com.personia.hr.model.Hierarchy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
@RequiredArgsConstructor
public class HierarchyServiceSimpleImpl implements HierarchyService {

    private final ObjectMapper objectMapper;

    public Hierarchy update(final String json) throws EmployeeHasTwoSupervisorsException,
            MultipleRootHierarchyException,
            LoopInEmployeeHierarchyException,
            JsonProcessingException {
        Map<String,String> relationships = objectMapper.readValue(json,Map.class);
        if (relationships.size()>0 && relationships.size() != countRelationships(json)) {
            throw new EmployeeHasTwoSupervisorsException();
        }
        Map<String,Hierarchy> repository = new HashMap<>();
        Set<String> employeesWithoutSupervisor = new HashSet<>();
        Map<String,String> relationShipMap = new HashMap<>();
        for (Entry<String,String> relationship: relationships.entrySet()) {
            relationShipMap.put(relationship.getKey(), relationship.getValue());
            Hierarchy supervised = updateIfAbsentInRepository(repository, null, relationship.getKey(),false);
            employeesWithoutSupervisor.remove(relationship.getKey());
            Hierarchy supervisor = updateIfAbsentInRepository(repository, employeesWithoutSupervisor, relationship.getValue(),true);
            supervisor.getTeam().add(supervised);
        }
        if (!relationShipMap.isEmpty()) {
            searchForLoop(relationShipMap);
        }
        if (employeesWithoutSupervisor.size() > 1) {
            throw new MultipleRootHierarchyException();
        }
        return employeesWithoutSupervisor.size() != 1 ? Hierarchy.builder().build() : repository.get(employeesWithoutSupervisor.iterator().next());
    }

    private Hierarchy updateIfAbsentInRepository(Map<String, Hierarchy> repository, Set<String> employeeSubSet, String employee, boolean updateSet) {
        Hierarchy result = repository.get(employee);
        if (result == null) {
            result = Hierarchy.builder().supervisor(employee).build();
            repository.put(employee, result);
            if (updateSet) {
                employeeSubSet.add(employee);
            }
        }
        return result;
    }

    private void searchForLoop(Map<String,String> map) throws LoopInEmployeeHierarchyException {
        String tortoise = map.entrySet().iterator().next().getKey();
        String hare = map.get(tortoise);
        boolean cycleFound = false;
        while (tortoise!= null && !(cycleFound = tortoise.equals(hare))) {
            tortoise = map.get(tortoise);
            hare = map.get(map.get(hare));
        }
        if (cycleFound) {
            throw new LoopInEmployeeHierarchyException();
        }
    }

    private int countRelationships(String text) {
        Pattern pattern = Pattern.compile("\\\"[^\"]*\\\"[ ]*:[ ]*\\\"[^\"]*\\\"[,]?");
        Matcher matcher = pattern.matcher(text);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

}
