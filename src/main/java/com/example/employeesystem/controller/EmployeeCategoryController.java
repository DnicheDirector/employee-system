package com.example.employeesystem.controller;

import com.example.employeesystem.domain.EmployeeCategory;
import com.example.employeesystem.service.EmployeeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class EmployeeCategoryController {
    @Autowired
    private EmployeeCategoryService categoryService;

    @GetMapping("{id}")
    public ResponseEntity<EmployeeCategory> get(@PathVariable("id") Long id){
        return new ResponseEntity<>(categoryService.findCategoryById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<EmployeeCategory>> getAll(){
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EmployeeCategory> save(@RequestBody EmployeeCategory employeeCategory){
        return new ResponseEntity<>(categoryService.save(employeeCategory), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<EmployeeCategory> patch(@RequestBody EmployeeCategory employeeCategory){
        EmployeeCategory oldCategory = categoryService.findCategoryById(employeeCategory.getId());

        if(employeeCategory.getName()!=null){
            oldCategory.setName(employeeCategory.getName());
        }

        return new ResponseEntity<>(categoryService.save(oldCategory), HttpStatus.OK);
    }

}
