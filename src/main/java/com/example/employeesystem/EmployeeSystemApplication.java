package com.example.employeesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EmployeeSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeSystemApplication.class, args);
    }

}
