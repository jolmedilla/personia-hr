package com.personia.hr.configuration;

import com.personia.hr.facade.HierarchyService;
import com.personia.hr.facade.HierarchyServicePersistentImpl;
import com.personia.hr.facade.HierarchyServiceSimpleImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ToggleConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(prefix="toggles",value="use-persistence",havingValue = "false")
    public HierarchyService hierarchyService(HierarchyServiceSimpleImpl hierarchyServiceSimple) {
        return hierarchyServiceSimple;
    }

    @Bean
    @ConditionalOnProperty(prefix="toggles",value="use-persistence",havingValue = "true")
    public HierarchyService hierarchyService(HierarchyServicePersistentImpl hierarchyServicePersistent){
        return hierarchyServicePersistent;
    }

}
