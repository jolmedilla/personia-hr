package com.personia.hr.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
public class Team {

    @JsonIgnore
    @Builder.Default
    private List<Hierarchy> hierarchyList = new ArrayList<Hierarchy>();

    @JsonAnyGetter
    public Map<String,Object> any() {
        return hierarchyList.stream().collect(Collectors.toMap(Hierarchy::getSupervisor,Hierarchy::getTeam));
    }
}
