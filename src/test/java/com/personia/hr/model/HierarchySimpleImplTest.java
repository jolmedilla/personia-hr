package com.personia.hr.model;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HierarchySimpleImplTest implements HierarchyTest<HierarchySimpleImpl> {

    @InjectMocks
    private HierarchySimpleImpl hierarchySimple;

    @Override
    public HierarchySimpleImpl getHierarchy() {
        return hierarchySimple;
    }

}