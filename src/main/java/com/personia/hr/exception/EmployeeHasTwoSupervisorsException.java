package com.personia.hr.exception;

public class EmployeeHasTwoSupervisorsException extends Exception{

    public EmployeeHasTwoSupervisorsException(String employee) {
        super(employee);
    }
}
