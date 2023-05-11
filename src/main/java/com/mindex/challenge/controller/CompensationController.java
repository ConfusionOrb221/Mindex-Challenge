package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class CompensationController {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @PostMapping("/compensation")
    public Compensation create(@RequestParam String employeeId, @RequestParam int salary,
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam Date date) {
        // Use request params here because it allows me to use int, and Date as parameters and let the conversion
        // be left up to those annotations.
        LOG.debug("Received employee create request for [{}]", employeeId);
        return compensationService.create(employeeId, salary, date);
    }

    @GetMapping("/compensation/{id}")
    public Compensation read(@PathVariable String id) {
        LOG.debug("Received compensation read request for id [{}]", id);

        return compensationService.read(id);
    }
}
