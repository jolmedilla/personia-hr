package com.personia.hr.facade;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HierarchyServiceSimpleImplTest implements HierarchyServiceTest <HierarchyServiceSimpleImpl>{
    @Override
    public HierarchyServiceSimpleImpl hierarchyService() {
        return new HierarchyServiceSimpleImpl();
    }
}
