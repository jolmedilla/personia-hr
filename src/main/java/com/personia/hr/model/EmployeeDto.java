package com.personia.hr.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    @JsonIgnore
    private String name;

    @JsonIgnore
    private EmployeeDto supervisor;

    @JsonIgnore
    @Builder.Default
    private List<EmployeeDto> team = new ArrayList<>();

    public void add(EmployeeDto supervised) {
        team.add(supervised);
    }

    @JsonAnyGetter
    public Map<String,Object> any() {
        return Optional.ofNullable(name).map(value -> Collections.<String,Object>singletonMap(value,
                team.stream()
                        .map(EmployeeDto::any)
                        .flatMap (map -> map.entrySet().stream())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))).orElse(Collections.<String,Object>emptyMap());
    }

}
