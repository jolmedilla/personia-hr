package com.personia.hr.facade;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personia.hr.algorithm.FloydsTraverseAlgorithm;
import com.personia.hr.algorithm.GraphCycleDetector;
import com.personia.hr.parser.JsonEmployeeToSupervisorParser;

public class HierarchyServiceSimpleImplTest implements HierarchyServiceTest <HierarchyService>{
    @Override
    public HierarchyService hierarchyService() {
        return new HierarchyService(new JsonEmployeeToSupervisorParser(new ObjectMapper(), new GraphCycleDetector(new FloydsTraverseAlgorithm())), new HierarchyFactorySimpleImpl());
    }
}
