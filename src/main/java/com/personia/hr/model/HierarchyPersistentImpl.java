package com.personia.hr.model;

import com.github.dozermapper.core.Mapper;
import com.personia.hr.exception.MultipleRootHierarchyException;
import com.personia.hr.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class HierarchyPersistentImpl implements Hierarchy {

    private final EmployeeRepository employeeRepository;

    private final Mapper mapper;

    @Override
    public void add(String employeeName, String supervisorName) {
        Employee employee = employeeRepository
                .findOptionalByNameIs(employeeName).orElseGet(() ->
                        Employee.builder().name(employeeName).build());
        Employee supervisor = employeeRepository
                .findOptionalByNameIs(supervisorName).orElseGet(() ->
                        Employee.builder().name(supervisorName).build());
        employee.setSupervisor(supervisor);
        supervisor.add(employee);
        employeeRepository.save(employee);

    }

    @Override
    public EmployeeDto getRoot() throws MultipleRootHierarchyException {
        List<Employee> hierarchy = employeeRepository.findDistinctBySupervisorIsNull();
        if (hierarchy.size() > 1 ) {
            throw new  MultipleRootHierarchyException();
        }
        return hierarchy.size() !=1 ? EmployeeDto.builder().build() : mapper.map(hierarchy.get(0),EmployeeDto.class);
    }

}
