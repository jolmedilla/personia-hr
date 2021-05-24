package com.personia.hr.model;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HierarchyTest {

    private Hierarchy hierarchy;

    @Test
    public void shouldSerializeEmptyHierarchy() {
        hierarchy = Hierarchy.builder().build();
        Map<String,Object> properties = hierarchy.any();
        assertThat(properties.size()).isEqualTo(1);
        assertThat(properties.values().contains(Team.builder().build())).isTrue();
    }

    @Test
    public void shouldSerializeBasicHierarchy() {
        hierarchy = Hierarchy.builder().supervisor("Jonas").build();
        Map<String,Object> properties = hierarchy.any();
        assertThat(properties.size()).isEqualTo(1);
        assertThat(properties.keySet().contains("Jonas")).isTrue();
    }
}
