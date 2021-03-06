package com.personia.hr.model;

import com.personia.hr.exception.MultipleRootHierarchyException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class HierarchySimpleImpl implements Hierarchy {

    private final Set<String> employeesWithoutSupervisor = new HashSet<>();

    @Qualifier("inMemoryEmployeeRepository")
    private final Map<String,EmployeeDto> repository;

    @Override
    public void clear() {
        employeesWithoutSupervisor.clear();
        repository.clear();
    }

    @Override
    public void add(String employeeName, String supervisorName) {
        repository.putIfAbsent(employeeName, EmployeeDto.builder().name(employeeName).build());
        employeesWithoutSupervisor.remove(employeeName);
        repository.computeIfAbsent(supervisorName, v -> {
            EmployeeDto result = EmployeeDto.builder().name(supervisorName).build();
            employeesWithoutSupervisor.add(supervisorName);
            return result;
        });
        EmployeeDto supervisor = repository.get(supervisorName);
        EmployeeDto employee = repository.get(employeeName);
        supervisor.add(employee);
        employee.setSupervisor(supervisor);
    }

    public EmployeeDto getRoot() throws MultipleRootHierarchyException {
        if (employeesWithoutSupervisor.size() > 1) {
            throw new MultipleRootHierarchyException();
        }
        return employeesWithoutSupervisor.size() != 1 ? EmployeeDto.builder().build() : repository.get(employeesWithoutSupervisor.iterator().next());
    }

    @Override
    public Optional<EmployeeDto> findEmployee(String employeeName) {
        return Optional.ofNullable(repository.get(employeeName));
    }

}
