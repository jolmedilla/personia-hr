package com.personia.hr.exception;

public class EmployeeHasTwoSupervisorsException extends Exception{

    public static final String REASON = "An employee  has two supervisors";

    public EmployeeHasTwoSupervisorsException() {
        super(REASON);
    }
}
