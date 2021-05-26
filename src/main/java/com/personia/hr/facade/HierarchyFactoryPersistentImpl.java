package com.personia.hr.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dozermapper.core.Mapper;
import com.personia.hr.exception.EmployeeHasTwoSupervisorsException;
import com.personia.hr.exception.LoopInEmployeeHierarchyException;
import com.personia.hr.exception.MultipleRootHierarchyException;
import com.personia.hr.model.Employee;
import com.personia.hr.model.EmployeeDto;
import com.personia.hr.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
public class HierarchyFactoryPersistentImpl implements HierarchyFactory {

    private final EmployeeRepository employeeRepository;

    private final Mapper mapper;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    @Override
    public EmployeeDto create(Map<String, String> map) throws MultipleRootHierarchyException{
        for (Map.Entry<String,String> relationship: map.entrySet()) {
            Employee employee = employeeRepository
                    .findOptionalByNameIs(relationship.getKey()).orElseGet(() ->
                            Employee.builder().name(relationship.getKey()).build());
            Employee supervisor = employeeRepository
                    .findOptionalByNameIs(relationship.getValue()).orElseGet(() ->
                            Employee.builder().name(relationship.getValue()).build());
            employee.setSupervisor(supervisor);
            supervisor.add(employee);
            employeeRepository.save(employee);
        }
        List<Employee> hierarchy = employeeRepository.findDistinctBySupervisorIsNull();
        if (hierarchy.size() > 1 ) {
            throw new  MultipleRootHierarchyException();
        }
        return hierarchy.size() !=1 ? EmployeeDto.builder().build() : mapper.map(hierarchy.get(0),EmployeeDto.class);
    }

}
