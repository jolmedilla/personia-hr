package com.personia.hr.facade;

import com.personia.hr.model.Hierarchy;
import com.personia.hr.model.Team;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class HierarchyService {

    public Hierarchy update(Map<String,String> supervisors) {
        Hierarchy nick = Hierarchy.builder().supervisor("Nick").build();
        nick.getTeam().getHierarchyList().add(Hierarchy.builder().supervisor("Pete").build());
        nick.getTeam().getHierarchyList().add(Hierarchy.builder().supervisor("Barbara").build());
        Hierarchy sophie = Hierarchy.builder().supervisor("Sophie").team(Team.builder().hierarchyList(Collections.singletonList(nick)).build()).build();
        return Hierarchy.builder().supervisor("Jonas").team(Team.builder().hierarchyList(Collections.singletonList(sophie)).build()).build();
    }
}
