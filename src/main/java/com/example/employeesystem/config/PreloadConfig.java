package com.example.employeesystem.config;

import com.example.employeesystem.domain.Employee;
import com.example.employeesystem.domain.EmployeeCategory;
import com.example.employeesystem.service.EmployeeCategoryService;
import com.example.employeesystem.service.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("preload")
public class PreloadConfig {
    @Bean
    public CommandLineRunner preload(EmployeeService employeeService, EmployeeCategoryService categoryService){
        return args -> {
            EmployeeCategory pm = new EmployeeCategory("PM");
            EmployeeCategory director = new EmployeeCategory("Director");

            categoryService.save(pm);
            categoryService.save(director);

            Employee employee1 = new Employee("Alexander");
            employee1.setEmployeeCategory(pm);

            Employee employee2 = new Employee("Victor");
            employee2.setEmployeeCategory(director);

            employeeService.save(employee1);
            employeeService.save(employee2);
        };
    }
}
