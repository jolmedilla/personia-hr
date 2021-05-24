package com.personia.hr.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.Map;

@Data
@Builder
public class Hierarchy {

    @JsonIgnore
    private String supervisor;

    @JsonIgnore
    @Builder.Default
    private Team team = Team.builder().build();

    @JsonAnyGetter
    public Map<String,Object> any() {
        return Collections.singletonMap(supervisor, team);
    }
}
