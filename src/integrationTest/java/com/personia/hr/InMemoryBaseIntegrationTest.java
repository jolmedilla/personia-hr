package com.personia.hr;

import com.personia.hr.model.EmployeeDto;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;

public abstract class InMemoryBaseIntegrationTest extends BaseIntegrationTest{


    @Autowired
    @Qualifier("inMemoryEmployeeRepository")
    private Map<String, EmployeeDto> employeeRepository;

    @BeforeEach
    public void setUp() {
        employeeRepository.clear();
    }

}
