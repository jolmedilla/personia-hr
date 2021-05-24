package com.personia.hr.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamTest {

    private Team team = Team.builder().build();

    @Test
    public void shouldSerializeEmptyTeam() throws JsonProcessingException {
        assertThat(team.any()).isEqualTo(Collections.emptyMap());
    }

    @Test
    public void shouldSerializeList() throws JsonProcessingException {
        team.getHierarchyList().add(Hierarchy.builder().supervisor("Pete").build());
        team.getHierarchyList().add(Hierarchy.builder().supervisor("Barbara").build());
        Map<String,Object> properties = team.any();
        assertThat(properties.size()).isEqualTo(2);
        assertThat(properties.get("Pete")).isEqualTo(Team.builder().build());
        assertThat(properties.get("Barbara")).isEqualTo(Team.builder().build());
    }
}
