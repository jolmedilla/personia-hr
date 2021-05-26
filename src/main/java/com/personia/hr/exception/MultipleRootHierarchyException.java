package com.personia.hr.exception;

public class MultipleRootHierarchyException extends Exception {
    public static final String REASON = "Multiple roots in the hierarchy";

    public MultipleRootHierarchyException() {
        super(REASON);
    }

}
