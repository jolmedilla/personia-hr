package com.personia.hr.exception;

public class EmployeeHasTwoSupervisorsException extends Exception{

    public EmployeeHasTwoSupervisorsException() {
        super("An employee  has two supervisors");
    }
}
