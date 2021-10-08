package com.example.employeesystem.service;

import com.example.employeesystem.data.EmployeeRepository;
import com.example.employeesystem.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @CacheEvict(value = "employees", allEntries = true)
    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    @Cacheable(value = "employees")
    public Iterable<Employee> getAll(){
        return employeeRepository.findAll();
    }

    public Employee findEmployeeById(Long id){
        return employeeRepository.findById(id).orElseThrow();
    }

    @CacheEvict(value = "employees", allEntries = true)
    public void deleteEmployee(Long id){
        employeeRepository.deleteById(id);
    }


}
