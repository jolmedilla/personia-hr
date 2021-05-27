package com.personia.hr.model;

import com.personia.hr.exception.MultipleRootHierarchyException;

import java.util.Optional;

public interface Hierarchy {

    void clear();

    void add(String employee, String supervisor);

    EmployeeDto getRoot() throws MultipleRootHierarchyException;

    Optional<EmployeeDto> findEmployee(String employeeName);
}