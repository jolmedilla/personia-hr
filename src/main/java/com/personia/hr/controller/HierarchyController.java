package com.personia.hr.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HierarchyController {

    @PutMapping("/hierarchies")
    public ResponseEntity<?> replace() {

        return ResponseEntity.ok().build();
    }
}
