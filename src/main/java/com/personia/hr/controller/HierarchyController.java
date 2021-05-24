package com.personia.hr.controller;

import com.personia.hr.exception.EmployeeHasTwoSupervisorsException;
import com.personia.hr.exception.MultipleRootHierarchyException;
import com.personia.hr.facade.HierarchyService;
import com.personia.hr.model.Hierarchy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class HierarchyController {

    private final HierarchyService hierarchyService;

    @PutMapping("/hierarchies")
    public ResponseEntity<Hierarchy> replace(@RequestBody  Map<String,String> supervisors) throws EmployeeHasTwoSupervisorsException, MultipleRootHierarchyException {

        return ResponseEntity.ok(hierarchyService.update(supervisors));
    }
}
