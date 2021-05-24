package com.personia.hr.facade;

import com.personia.hr.exception.EmployeeHasTwoSupervisorsException;
import com.personia.hr.exception.MultipleRootHierarchyException;
import com.personia.hr.model.Hierarchy;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class HierarchyService {

    public Hierarchy update(Map<String,String> supervisors) throws EmployeeHasTwoSupervisorsException, MultipleRootHierarchyException {
        Map<String,Hierarchy> employees = new HashMap<>();
        Set<String> unsupervisedList = new HashSet<>();
        Set<String> supervisedList = new HashSet<>();
        for (Map.Entry<String,String> relationship: supervisors.entrySet()) {
            if (supervisedList.contains(relationship.getKey())) {
                throw new EmployeeHasTwoSupervisorsException(relationship.getKey());
            }
            Hierarchy supervised = employees.get(relationship.getKey());
            Hierarchy supervisor = employees.get(relationship.getValue());
            if (supervised == null) {
                supervised = Hierarchy.builder().supervisor(relationship.getKey()).build();
                employees.put(relationship.getKey(), supervised);
                supervisedList.add(relationship.getKey());
            }
            unsupervisedList.remove(relationship.getKey());
            if (supervisor == null) {
                supervisor = Hierarchy.builder().supervisor(relationship.getValue()).build();
                employees.put(relationship.getValue(), supervisor);
                unsupervisedList.add(relationship.getValue());
            }
            supervisor.getTeam().getHierarchyList().add(supervised);
        }
        if (unsupervisedList.size() > 1) {
            throw new MultipleRootHierarchyException();
        }
        return unsupervisedList.size() != 1 ? Hierarchy.builder().build() : employees.get(unsupervisedList.iterator().next());
    }
}
