package com.personia.hr.exception;

public class LoopInEmployeeHierarchyException extends Exception {
    public static final String REASON = "There is a loop in the employee hierarchy";

    public LoopInEmployeeHierarchyException() {
        super(REASON);
    }
}
