package com.personia.hr.model;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

@ExtendWith(MockitoExtension.class)
class HierarchySimpleImplTest implements HierarchyTest<HierarchySimpleImpl> {

    private HierarchySimpleImpl hierarchySimple = new HierarchySimpleImpl(new HashMap<>());

    @Override
    public HierarchySimpleImpl getHierarchy() {
        return hierarchySimple;
    }

}