package com.example.employeesystem.integration;

import com.example.employeesystem.domain.Employee;
import com.example.employeesystem.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("preload")
public class EmployeeIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private EmployeeService employeeService;

    @Test
    void getAll(){
        ResponseEntity<?> response = testRestTemplate.getForEntity(getApiPath(), Iterable.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        List<Employee> responseBody = (List<Employee>) response.getBody();
        assertNotNull(responseBody);
    }

    @Test
    void getById(){
        List<Employee> all = (List<Employee>) employeeService.getAll();
        Long id = all.get(0).getId();

        ResponseEntity<Employee> response = testRestTemplate.getForEntity(getApiPath() + "/{id}", Employee.class, id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void save(){
        Employee employee = new Employee("Alexander");

        ResponseEntity<Employee> response = testRestTemplate.postForEntity(getApiPath(), employee, Employee.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void delete(){
        List<Employee> before = (List<Employee>) employeeService.getAll();
        Long id = before.get(0).getId();

        testRestTemplate.delete(getApiPath() + "/{id}", id);

        List<Employee> after = (List<Employee>) employeeService.getAll();

        assertNotEquals(before.size(), after.size());
    }

    private String getApiPath() {
        return "http://localhost:" + port + "/employees";
    }
}
