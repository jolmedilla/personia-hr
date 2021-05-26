package com.personia.hr.model;

import com.personia.hr.exception.MultipleRootHierarchyException;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class HierarchySimpleImpl implements Hierarchy {

    private final Set<String> employeesWithoutSupervisor = new HashSet<>();
    private final Map<String,EmployeeDto> repository = new HashMap<>();

    @Override
    public void add(String employeeName, String supervisorName) {
        EmployeeDto employee = updateIfAbsentInRepository(null, employeeName,false);
        employeesWithoutSupervisor.remove(employeeName);
        EmployeeDto supervisor = updateIfAbsentInRepository(employeesWithoutSupervisor, supervisorName,true);
        supervisor.add(employee);

    }

    public EmployeeDto getRoot() throws MultipleRootHierarchyException {
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
