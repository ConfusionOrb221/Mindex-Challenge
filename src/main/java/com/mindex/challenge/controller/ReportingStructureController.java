package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReportingStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/reporting/{id}")
    public ReportingStructure read(@PathVariable String id) {
        LOG.debug("Received Reporting Structure read request for id [{}]", id);

        ReportingStructure reportingStructure = new ReportingStructure();
        Employee employee = employeeService.read(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        reportingStructure.setEmployee(employee);
        int total = 0;
        // There seems to be a bug in the current codebase where the getDirectReports is empty after
        // one loop or the persistence of these objects appears to get lost. This is why i run the size command
        // once here and then pass it to the recursive function or else it just becomes null immediately.
        total = employee.getDirectReports().size();
        for (Employee reports : employee.getDirectReports()) {
            reportingStructure.findAllReports(reports, total);
        }
        reportingStructure.setNumberOfReports(total);

        return reportingStructure;
    }
}
