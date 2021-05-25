package com.personia.hr.exception;

public class LoopInEmployeeHierarchyException extends Exception {
    public LoopInEmployeeHierarchyException() {
        super("There is a loop in the employee hierarchy");
    }
}
