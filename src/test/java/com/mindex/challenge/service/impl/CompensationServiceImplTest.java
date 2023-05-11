package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String compensationUrl;
    private String compensationIdUrl;
    private String employeeId;


    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
        String employeeUrl = "http://localhost:" + port + "/employee";
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
        employeeId = createdEmployee.getEmployeeId();
    }

    @Test
    public void testCreateRead() {
        String salary = "70000";
        String date = "2023-06-22";

        compensationUrl += "?employeeId=" + employeeId + "&salary=" + salary + "&date=" + date;

        ResponseEntity<String> responseEntity = restTemplate.exchange(compensationUrl,
                HttpMethod.POST, null, String.class);

        HttpStatus statusCode = responseEntity.getStatusCode();
        assertEquals(HttpStatus.OK, statusCode);

        Compensation compensation = restTemplate.getForEntity(compensationIdUrl, Compensation.class, employeeId).getBody();
        // verify compensation matches expected
        assertNotNull(compensation);
        assertNotNull(compensation.getEmployee());
        assertEquals(employeeId, compensation.getEmployee().getEmployeeId());
        assertEquals(Integer.parseInt(salary), compensation.getSalary());
        //need to convert our previous string date into what would it would look like as instant
        assertEquals(compensation.getEffectiveDate().toInstant().toString(), "2023-06-22T00:00:00Z");
    }
}
