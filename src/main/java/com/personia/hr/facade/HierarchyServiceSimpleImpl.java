package com.personia.hr.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personia.hr.exception.EmployeeHasTwoSupervisorsException;
import com.personia.hr.exception.LoopInEmployeeHierarchyException;
import com.personia.hr.exception.MultipleRootHierarchyException;
import com.personia.hr.model.EmployeeDto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HierarchyServiceSimpleImpl implements HierarchyService {

    private ObjectMapper objectMapper = new ObjectMapper();

    private Map<String,EmployeeDto> repository = new HashMap<>();

    public EmployeeDto update(final String json) throws EmployeeHasTwoSupervisorsException,
            MultipleRootHierarchyException,
            LoopInEmployeeHierarchyException,
            JsonProcessingException {
        Map<String,String> relationships = objectMapper.readValue(json,Map.class);
        if (relationships.size()>0 && relationships.size() != countRelationships(json)) {
            throw new EmployeeHasTwoSupervisorsException();
        }
        Set<String> employeesWithoutSupervisor = new HashSet<>();
        Map<String,String> relationShipMap = new HashMap<>();
        for (Entry<String,String> relationship: relationships.entrySet()) {
            relationShipMap.put(relationship.getKey(), relationship.getValue());
            EmployeeDto supervised = updateIfAbsentInRepository(null, relationship.getKey(),false);
            employeesWithoutSupervisor.remove(relationship.getKey());
            EmployeeDto supervisor = updateIfAbsentInRepository(employeesWithoutSupervisor, relationship.getValue(),true);
            supervisor.add(supervised);
        }
        if (!relationShipMap.isEmpty()) {
            searchForLoop(relationShipMap);
        }
        if (employeesWithoutSupervisor.size() > 1) {
            throw new MultipleRootHierarchyException();
        }
        return employeesWithoutSupervisor.size() != 1 ? EmployeeDto.builder().build() : repository.get(employeesWithoutSupervisor.iterator().next());
    }

    private EmployeeDto updateIfAbsentInRepository(Set<String> employeeSubSet, String employee, boolean updateSet) {
        EmployeeDto result = repository.get(employee);
        if (result == null) {
            result = EmployeeDto.builder().name(employee).build();
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
