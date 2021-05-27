package com.personia.hr.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.personia.hr.exception.EmployeeHasTwoSupervisorsException;
import com.personia.hr.exception.LoopInEmployeeHierarchyException;
import com.personia.hr.exception.MultipleRootHierarchyException;
import com.personia.hr.model.EmployeeDto;
import com.personia.hr.model.Hierarchy;
import com.personia.hr.parser.JsonEmployeeToSupervisorParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HierarchyService {

    private final JsonEmployeeToSupervisorParser parser ;

    private final Hierarchy hierarchy;

    public EmployeeDto update(final String json) throws EmployeeHasTwoSupervisorsException,
            MultipleRootHierarchyException,
            LoopInEmployeeHierarchyException,
            JsonProcessingException {
        hierarchy.clear();
        parser.parse(json).forEach(hierarchy::add);
        return hierarchy.getRoot();
    }

    public EmployeeDto supervisors(final String employeeName) {
        return  EmployeeDto.builder().name("Sophie").team(
                List.of(EmployeeDto.builder().name("Nick").build())).build();
    }

}
