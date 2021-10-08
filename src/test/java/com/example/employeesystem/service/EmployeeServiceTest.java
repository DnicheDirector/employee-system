package com.example.employeesystem.service;

import com.example.employeesystem.data.EmployeeRepository;
import com.example.employeesystem.domain.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeeServiceTest {

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CacheManager cacheManager;

    private static List<Employee> employees;

    @BeforeAll
    public static void initEmployees(){
        Employee employee1 = new Employee("Alexander");
        Employee employee2 = new Employee("Victor");

        employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
    }

    @Test
    void save() {
        Employee employee = employees.get(1);

        when(employeeRepository.save(employee))
                .thenReturn(employee);

        Employee saved = employeeService.save(employees.get(1));

        assertNotNull(saved);
        assertEquals(employee, saved);

        verify(employeeRepository, times(1))
                .save(employee);
    }

    @Test
    void getAll() {
        when(employeeRepository.findAll())
                .thenReturn(employees);

        Iterable<Employee> all = employeeService.getAll();

        assertNotNull(all);
        assertEquals(employees, all);

        verify(employeeRepository, times(1))
                .findAll();

    }

    @Test
    void findEmployeeById() {
        Employee employee = employees.get(1);

        when(employeeRepository.findById(employee.getId()))
                .thenReturn(Optional.of(employee));

        Employee returned = employeeService.findEmployeeById(employee.getId());

        assertNotNull(returned);
        assertEquals(employee, returned);

        verify(employeeRepository, times(1))
                .findById(employee.getId());

    }

    @Test
    void shouldThrowExceptionIfEmployeeDoesntExists() {
        Long id = 1L;
        when(employeeRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, ()-> employeeService.findEmployeeById(id));

        verify(employeeRepository, times(1))
                .findById(id);

    }

    @Test
    void deleteEmployee() {
        Long id = 1L;

        doNothing().when(employeeRepository).deleteById(id);

        employeeService.deleteEmployee(id);

        verify(employeeRepository, times(1))
                .deleteById(id);
    }

    @Test
    void testCaching(){
        when(employeeRepository.findAll())
                .thenReturn(employees);

        Iterable<Employee> all = new ArrayList<>();

        for (int i = 0; i<=2; i++){
            all = employeeService.getAll();
        }
        Object cached = cacheManager.getCache("employees").get(SimpleKey.EMPTY).get();

        assertNotNull(all);
        assertSame(all, cached);

        verify(employeeRepository, times(1))
                .findAll();
    }

    @Test
    void testCachingShouldEvictOnChange(){
        when(employeeRepository.findAll())
                .thenReturn(employees);

        Iterable<Employee> all = employeeService.getAll();

        Object cached = cacheManager.getCache("employees").get(SimpleKey.EMPTY).get();

        assertNotNull(all);
        assertSame(all, cached);

        employeeService.save(employees.get(1));

        Object after = cacheManager.getCache("employees").get(SimpleKey.EMPTY);

        assertNull(after);
    }

}