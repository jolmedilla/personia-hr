package com.personia.hr.configuration;

import com.github.dozermapper.core.Mapper;
import com.personia.hr.model.EmployeeDto;
import com.personia.hr.model.Hierarchy;
import com.personia.hr.model.HierarchyPersistentImpl;
import com.personia.hr.model.HierarchySimpleImpl;
import com.personia.hr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ToggleConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(prefix="toggles",value="use-persistence",havingValue = "false")
    public Hierarchy hierarchySimple(@Qualifier("inMemoryEmployeeRepository") Map<String,EmployeeDto> repository) {
        return new HierarchySimpleImpl(repository);
    }

    @Bean
    @ConditionalOnProperty(prefix="toggles",value="use-persistence",havingValue = "true")
    public Hierarchy hierarchyPersistent(EmployeeRepository employeeRepository, Mapper mapper){
        return new HierarchyPersistentImpl(employeeRepository,mapper);
    }

    @Bean("inMemoryEmployeeRepository")
    public Map<String, EmployeeDto> employeeDtoRepository(){
        return new HashMap<>();
    }

}
