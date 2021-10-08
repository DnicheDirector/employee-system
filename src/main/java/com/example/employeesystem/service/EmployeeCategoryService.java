package com.example.employeesystem.service;

import com.example.employeesystem.data.EmployeeCategoryRepository;
import com.example.employeesystem.domain.Employee;
import com.example.employeesystem.domain.EmployeeCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeCategoryService {

    @Autowired
    private EmployeeCategoryRepository categoryRepository;

    public EmployeeCategory save(EmployeeCategory employee){
        return categoryRepository.save(employee);
    }

    public Iterable<EmployeeCategory> getAll(){
        return categoryRepository.findAll();
    }

    public EmployeeCategory findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }
}
