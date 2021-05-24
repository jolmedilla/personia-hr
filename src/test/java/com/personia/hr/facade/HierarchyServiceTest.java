package com.personia.hr.facade;

import com.personia.hr.model.Hierarchy;
import com.personia.hr.model.Team;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class HierarchyServiceTest {


    private final HierarchyService hierarchyService = new HierarchyService();

    @Ignore
    @Test
    public void shouldReturnEmptyHierarchyWhenReceivesNoSupervisors() {
        Hierarchy hierarchy = hierarchyService.update(Collections.emptyMap());
        assertThat(hierarchy.getSupervisor()).isNull();
    }

    @Ignore
    @Test
    public void shouldReturnBasicHierarchyWhenReceivesOneSupervisor() {
        Hierarchy hierarchy = hierarchyService.update(Collections.singletonMap("Pete","Nick"));
        assertThat(hierarchy.getSupervisor()).isEqualTo("Nick");
        assertThat(hierarchy.getTeam()).isEqualTo(Team.builder()
                .hierarchyList(Collections.singletonList(Hierarchy.builder().supervisor("Pete").build())).build());

    }
}