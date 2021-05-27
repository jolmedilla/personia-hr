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

import java.util.Optional;

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

    public Optional<EmployeeDto> supervisors(final String employeeName) {
        var employee= hierarchy.findEmployee(employeeName);

        var firstpass = employee.map(e -> Optional.ofNullable(e.getSupervisor())
                        .orElse(EmployeeDto.builder().build()));
        var secondpass = firstpass.map(e -> Optional.ofNullable(e.getSupervisor()).map(v -> {
                            EmployeeDto bigBoss = EmployeeDto.builder().name(v.getName()).build();
                            EmployeeDto boss = EmployeeDto.builder().name(e.getName()).build();
                            bigBoss.add(boss);
                            return bigBoss;
                        }).orElse(EmployeeDto.builder().name(e.getName()).build()));
        return secondpass;
    }

}
