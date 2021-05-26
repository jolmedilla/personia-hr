package com.personia.hr.configuration;

import com.github.dozermapper.core.Mapper;
import com.personia.hr.facade.HierarchyFactory;
import com.personia.hr.facade.HierarchyFactorySimpleImpl;
import com.personia.hr.facade.HierarchyFactoryPersistentImpl;
import com.personia.hr.parser.JsonEmployeeToSupervisorParser;
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
    public HierarchyFactory hierarchyServiceSimple(JsonEmployeeToSupervisorParser parser, HierarchyFactorySimpleImpl hierarchyFactory) {
        return new HierarchyFactorySimpleImpl();
    }

    @Bean
    @ConditionalOnProperty(prefix="toggles",value="use-persistence",havingValue = "true")
    public HierarchyFactory hierarchyServicePersistent(EmployeeRepository employeeRepository, Mapper mapper){
        return new HierarchyFactoryPersistentImpl(employeeRepository,mapper);
    }

}
