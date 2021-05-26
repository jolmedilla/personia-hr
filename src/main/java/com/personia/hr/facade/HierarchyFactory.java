package com.personia.hr.facade;

import com.personia.hr.exception.MultipleRootHierarchyException;
import com.personia.hr.model.EmployeeDto;

import java.util.Map;

public interface  HierarchyFactory {
    EmployeeDto create(Map<String, String> map) throws MultipleRootHierarchyException;
}