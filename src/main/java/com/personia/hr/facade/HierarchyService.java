package com.personia.hr.facade;

import com.personia.hr.exception.EmployeeHasTwoSupervisorsException;
import com.personia.hr.exception.LoopInEmployeeHierarchyException;
import com.personia.hr.exception.MultipleRootHierarchyException;
import com.personia.hr.model.Hierarchy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Component
public class HierarchyService {

    public Hierarchy update(Map<String,String> supervisors) throws EmployeeHasTwoSupervisorsException,
            MultipleRootHierarchyException,
            LoopInEmployeeHierarchyException {
        Map<String,Hierarchy> repository = new HashMap<>();
        Set<String> employeesWithoutSupervisor = new HashSet<>();
        Set<String> employeesWithSupervisor = new HashSet<>();
        String tortoise = null;
        String hare = null;
        int length = supervisors.size();
        int index = 0;
        for (Map.Entry<String,String> relationship: supervisors.entrySet()) {
            tortoise = index > 0 ? supervisors.get(tortoise) : supervisors.get(relationship.getKey());
            hare = index > 0 ? supervisors.get(supervisors.get(hare)) :  supervisors.get(supervisors.get(relationship.getValue()));
            if (tortoise != null && tortoise.equals(hare)) {
                throw new LoopInEmployeeHierarchyException();
            }            index++;
            if (employeesWithSupervisor.contains(relationship.getKey())) {
                throw new EmployeeHasTwoSupervisorsException(relationship.getKey());
            }
            Hierarchy supervised = updateSetIfAbsentInRepository(repository, employeesWithSupervisor, relationship.getKey());
            employeesWithoutSupervisor.remove(relationship.getKey());
            Hierarchy supervisor = updateSetIfAbsentInRepository(repository, employeesWithoutSupervisor, relationship.getValue());
            supervisor.getTeam().add(supervised);
        }
        if (employeesWithoutSupervisor.size() > 1) {
            throw new MultipleRootHierarchyException();
        }
        return employeesWithoutSupervisor.size() != 1 ? Hierarchy.builder().build() : repository.get(employeesWithoutSupervisor.iterator().next());
    }

    private Hierarchy updateSetIfAbsentInRepository(Map<String, Hierarchy> repository, Set<String> employeeSubSet, String employee) {
        Hierarchy result = repository.get(employee);
        if (result == null) {
            result = Hierarchy.builder().supervisor(employee).build();
            repository.put(employee, result);
            employeeSubSet.add(employee);
        }
        return result;
    }
}
