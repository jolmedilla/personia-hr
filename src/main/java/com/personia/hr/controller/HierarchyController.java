package com.personia.hr.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.personia.hr.exception.EmployeeHasTwoSupervisorsException;
import com.personia.hr.exception.EmployeeNotFoundException;
import com.personia.hr.exception.LoopInEmployeeHierarchyException;
import com.personia.hr.exception.MultipleRootHierarchyException;
import com.personia.hr.facade.HierarchyService;
import com.personia.hr.model.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class HierarchyController {

    private final HierarchyService hierarchyService;

    @PutMapping("/hierarchies")
    public ResponseEntity<EmployeeDto> replace(@RequestBody final String json)  {
        try {
            return ResponseEntity.ok(hierarchyService.update(json));
        } catch (EmployeeHasTwoSupervisorsException |
                MultipleRootHierarchyException |
                LoopInEmployeeHierarchyException |
                JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(),e);
        }
    }

    @GetMapping("/hierarchies/employees/{name}/supervisors")
    public ResponseEntity<EmployeeDto> supervisors(@PathVariable final String name)  {
        try {
            return ResponseEntity.ok(hierarchyService.supervisors(name).orElseThrow(() -> new EmployeeNotFoundException(name)));
        } catch (EmployeeNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(),e);
        }
    }
}
