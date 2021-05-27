package com.personia.hr;


import com.personia.hr.model.EmployeeDto;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest(properties = {"toggles.use-persistence=false"})
@AutoConfigureMockMvc
public class CreateOrReplaceHierarchyInMemoryImplIntegrationTest extends CreateOrReplaceHierarchyIntegrationTest {

    @Autowired
    @Qualifier("inMemoryEmployeeRepository")
    private Map<String, EmployeeDto> employeeRepository;

    @BeforeEach
    public void setUp() {
        employeeRepository.clear();
    }
}
