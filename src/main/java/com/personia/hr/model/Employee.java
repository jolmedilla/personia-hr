package com.personia.hr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @JsonIgnore
    @Id
    @GeneratedValue
    private Long Id;

    @JsonIgnore
    @Column(unique=true)
    private String name;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="supervisor")
    private Employee supervisor;

    @JsonIgnore
    @OneToMany
    @Builder.Default
    private List<Employee> team = new ArrayList<>();

    public void add(Employee supervised) {
        team.add(supervised);
    }

}
