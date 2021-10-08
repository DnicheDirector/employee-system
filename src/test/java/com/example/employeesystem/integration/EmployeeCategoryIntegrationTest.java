package com.example.employeesystem.integration;

import com.example.employeesystem.domain.Employee;
import com.example.employeesystem.domain.EmployeeCategory;
import com.example.employeesystem.service.EmployeeCategoryService;
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
public class EmployeeCategoryIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private EmployeeCategoryService categoryService;

    @Test
    void getAll(){
        ResponseEntity<?> response = testRestTemplate.getForEntity(getApiPath(), Iterable.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        List<EmployeeCategory> responseBody = (List<EmployeeCategory>) response.getBody();
        assertEquals(2, responseBody.size());
    }

    @Test
    void getById(){
        List<EmployeeCategory> all = (List<EmployeeCategory>) categoryService.getAll();
        Long id = all.get(1).getId();

        ResponseEntity<Employee> response = testRestTemplate.getForEntity(getApiPath() + "/{id}", Employee.class, id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void save(){
        EmployeeCategory category = new EmployeeCategory("PM");

        ResponseEntity<EmployeeCategory> response = testRestTemplate.postForEntity(getApiPath(), category, EmployeeCategory.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }


    private String getApiPath() {
        return "http://localhost:" + port + "/categories";
    }
}
