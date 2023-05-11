package com.mindex.challenge.data;

import java.util.List;

public class ReportingStructure {

    private Employee employee;
    private int numberOfReports;

    public ReportingStructure() {
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getNumberOfReports() {
        return this.numberOfReports;
    }

    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }

    //Recursion function to find all reports looping through the list.
    public void findAllReports(Employee employee, int total) {
        if (employee.getDirectReports() == null) {
            return;
        }
        total += employee.getDirectReports().size();
        for (Employee reports : employee.getDirectReports()) {
            findAllReports(reports, total);
        }
    }
}
