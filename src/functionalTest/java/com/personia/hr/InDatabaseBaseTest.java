package com.personia.hr;

import com.personia.hr.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class InDatabaseBaseTest extends BaseTest {


    @Autowired
    private EmployeeRepository employeeRepository;


    @BeforeEach
    public void setUp() {
        employeeRepository.deleteAll();
    }

}
