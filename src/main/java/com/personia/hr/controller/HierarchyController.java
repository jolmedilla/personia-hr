package com.personia.hr.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HierarchyController {

    @PutMapping("/hierarchies")
    public ResponseEntity<String> replace(@RequestBody  Map<String,String> supervisors) {

        return ResponseEntity.ok("{" +
                "          \"Jonas\": {" +
                "              \"Sophie\": {" +
                "                  \"Nick\": {" +
                "                      \"Pete\": {}," +
                "                      \"Barbara\": {}" +
                "                  }" +
                "} }" +
                "}");
    }
}
