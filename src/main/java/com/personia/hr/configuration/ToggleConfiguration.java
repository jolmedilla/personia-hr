package com.personia.hr.configuration;

import com.github.dozermapper.core.Mapper;
import com.personia.hr.model.Hierarchy;
import com.personia.hr.model.HierarchySimpleImpl;
import com.personia.hr.model.HierarchyPersistentImpl;
import com.personia.hr.repository.EmployeeRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ToggleConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(prefix="toggles",value="use-persistence",havingValue = "false")
    public Hierarchy hierarchySimple() {
        return new HierarchySimpleImpl();
    }

    @Bean
    @ConditionalOnProperty(prefix="toggles",value="use-persistence",havingValue = "true")
    public Hierarchy hierarchyPersistent(EmployeeRepository employeeRepository, Mapper mapper){
        return new HierarchyPersistentImpl(employeeRepository,mapper);
    }

}
