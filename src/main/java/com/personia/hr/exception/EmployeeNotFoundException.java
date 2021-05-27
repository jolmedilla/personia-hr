package com.personia.hr.exception;

public class EmployeeNotFoundException extends Exception{

    public static final String REASON ="Employee with name {} not found";

    public EmployeeNotFoundException(String name) {
        super(String.format(REASON,name));
    }

}
