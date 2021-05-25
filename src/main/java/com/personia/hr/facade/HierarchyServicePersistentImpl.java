package com.personia.hr.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.personia.hr.exception.EmployeeHasTwoSupervisorsException;
import com.personia.hr.exception.LoopInEmployeeHierarchyException;
import com.personia.hr.exception.MultipleRootHierarchyException;
import com.personia.hr.model.Hierarchy;

public class HierarchyServicePersistentImpl implements HierarchyService {
    @Override
    public Hierarchy update(String json) throws EmployeeHasTwoSupervisorsException, MultipleRootHierarchyException, LoopInEmployeeHierarchyException, JsonProcessingException {
        return null;
    }
}
