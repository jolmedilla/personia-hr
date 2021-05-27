package com.personia.hr.model;

import com.personia.hr.exception.MultipleRootHierarchyException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

interface HierarchyTest<T extends Hierarchy> {

    Map<String,String> MULTIPLE_ROOT_HIERARCHY_MAP = Map.of("Pete","Barbara","Nick","Sophie");

    T getHierarchy();

    default void prepareShouldThrowExceptionWhenHierarchyHasMultipleRoots() {}

    @Test
    default void shouldThrowExceptionWhenHierarchyHasMultipleRoots() {
        prepareShouldThrowExceptionWhenHierarchyHasMultipleRoots();
        MULTIPLE_ROOT_HIERARCHY_MAP.forEach(getHierarchy()::add);
        assertThrows(MultipleRootHierarchyException.class,()-> getHierarchy().getRoot());
    }

}