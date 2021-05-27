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
        EmployeeDto supervisor = hierarchy.findEmployee(employeeName).getSupervisor();
        EmployeeDto result;
        if(supervisor!=null) {
            EmployeeDto bigBoss = supervisor.getSupervisor();
            supervisor = EmployeeDto.builder().name(supervisor.getName()).build();
            if (bigBoss != null) {
                bigBoss= EmployeeDto.builder().name(bigBoss.getName()).build();
                bigBoss.add(supervisor);
                result = bigBoss;
            } else {
                result = supervisor;
            }
        } else {
            result = EmployeeDto.builder().build();
        }
        return result;
    }

}
