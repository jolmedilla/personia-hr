package com.personia.hr.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
public class Team {

    @JsonIgnore
    @Builder.Default
    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    private List<Hierarchy> hierarchyList = new ArrayList<Hierarchy>();

    public boolean add(Hierarchy member) {
        return hierarchyList.add(member);
    }

    public boolean remove(Hierarchy member) {
        return hierarchyList.remove(member);
    }

    @JsonAnyGetter
    public Map<String,Object> any() {
        return hierarchyList.stream().collect(Collectors.toMap(Hierarchy::getSupervisor,Hierarchy::getTeam));
    }
}
