package com.example.employeesystem.data;

import com.example.employeesystem.domain.EmployeeCategory;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeCategoryRepository extends CrudRepository<EmployeeCategory, Long> {
}
