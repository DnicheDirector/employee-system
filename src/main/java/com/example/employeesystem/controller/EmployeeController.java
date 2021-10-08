package com.example.employeesystem.controller;

import com.example.employeesystem.domain.Employee;
import com.example.employeesystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("{id}")
    public ResponseEntity<Employee> get(@PathVariable("id") Long id){
        return new ResponseEntity<>(employeeService.findEmployeeById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<Employee>> getAll(){
        return new ResponseEntity<>(employeeService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Employee> save(@RequestBody Employee Employee){
        return new ResponseEntity<>(employeeService.save(Employee), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<Employee> patch(@RequestBody Employee Employee){
        Employee oldCategory = employeeService.findEmployeeById(Employee.getId());

        if(Employee.getName()!=null){
            oldCategory.setName(Employee.getName());
        }

        return new ResponseEntity<>(employeeService.save(oldCategory), HttpStatus.OK);
    }
}
