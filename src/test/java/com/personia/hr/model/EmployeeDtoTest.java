package com.personia.hr.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.any;

public class EmployeeDtoTest {

    private EmployeeDto hierarchy;

    @Test
    public void shouldSerializeEmptyHierarchy() {
        hierarchy = EmployeeDto.builder().build();
        Map<String,Object> properties = hierarchy.any();
        assertThat(properties.size()).isEqualTo(1);
    }

    @Test
    public void shouldSerializeBasicHierarchy() {
        hierarchy = EmployeeDto.builder().name("Jonas").build();
        Map<String,Object> properties = hierarchy.any();
        assertThat(properties.size()).isEqualTo(1);
        assertThat(properties.containsKey("Jonas")).isTrue();
    }

    @Test
    public void shouldSerializeList()  {
        hierarchy = EmployeeDto.builder().name("Jonas").build();
        hierarchy.add(EmployeeDto.builder().name("Pete").build());
        hierarchy.add(EmployeeDto.builder().name("Barbara").build());
        Map<String,Object> properties = hierarchy.any();
        Map<String,Object> jonasTeam = (Map<String, Object>) properties.get("Jonas");
        assertThat(jonasTeam.size()).isEqualTo(2);
        assertThat(jonasTeam.get("Pete")).isNotNull();
        assertThat(jonasTeam.get("Barbara")).isNotNull();
    }
}
