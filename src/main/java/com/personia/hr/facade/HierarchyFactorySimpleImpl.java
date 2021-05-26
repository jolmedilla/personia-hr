package com.personia.hr.facade;

import com.personia.hr.exception.MultipleRootHierarchyException;
import com.personia.hr.model.EmployeeDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class HierarchyFactorySimpleImpl implements HierarchyFactory {

    private Set<String> employeesWithoutSupervisor = new HashSet<>();
    private Map<String,String> relationShipMap = new HashMap<>();
    private Map<String,EmployeeDto> repository = new HashMap<>();

    public EmployeeDto create(Map<String,String> map) throws MultipleRootHierarchyException {
        for (Map.Entry<String,String> relationship: map.entrySet()) {
            relationShipMap.put(relationship.getKey(), relationship.getValue());
            EmployeeDto supervised = updateIfAbsentInRepository(null, relationship.getKey(),false);
            employeesWithoutSupervisor.remove(relationship.getKey());
            EmployeeDto supervisor = updateIfAbsentInRepository(employeesWithoutSupervisor, relationship.getValue(),true);
            supervisor.add(supervised);
        }
        return getRoot();
    }

    private EmployeeDto getRoot() throws MultipleRootHierarchyException {
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

}
