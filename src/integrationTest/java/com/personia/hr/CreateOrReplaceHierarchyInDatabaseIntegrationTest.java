package com.personia.hr;

import com.personia.hr.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"toggles.use-persistence=true"})
@AutoConfigureMockMvc
public class CreateOrReplaceHierarchyInDatabaseIntegrationTest extends InDatabaseBaseIntegrationTest implements CreateOrReplaceHierarchyIntegrationTest{

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp() {
        employeeRepository.deleteAll();
    }

}
