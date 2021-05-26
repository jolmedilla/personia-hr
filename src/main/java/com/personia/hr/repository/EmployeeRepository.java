package com.personia.hr.repository;

import com.personia.hr.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findOptionalByNameIs(String name);

    List<Employee> findDistinctBySupervisorIsNull();
}
