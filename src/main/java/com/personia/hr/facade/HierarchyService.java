package com.personia.hr.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.personia.hr.exception.EmployeeHasTwoSupervisorsException;
import com.personia.hr.exception.LoopInEmployeeHierarchyException;
import com.personia.hr.exception.MultipleRootHierarchyException;
import com.personia.hr.model.EmployeeDto;
import com.personia.hr.parser.JsonEmployeeToSupervisorParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class HierarchyService {

    private final JsonEmployeeToSupervisorParser parser ;

    private final HierarchyFactorySimpleImpl hierarchyFactory;

    private Map<String,EmployeeDto> repository = new HashMap<>();

    public EmployeeDto update(final String json) throws EmployeeHasTwoSupervisorsException,
            MultipleRootHierarchyException,
            LoopInEmployeeHierarchyException,
            JsonProcessingException {
        return hierarchyFactory.create(parser.parse(json));
    }


}
