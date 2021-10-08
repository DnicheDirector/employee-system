package com.example.employeesystem.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    public Employee() {
    }

    public Employee(String name) {
        this.name = name;
    }

    private String name;

    @OneToOne
    @JoinTable(
            name = "CATEGORY_EMPLOYEE",
            joinColumns = @JoinColumn(name = "EMPLOYEE_ID"),
            inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID"))
    private EmployeeCategory employeeCategory;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EmployeeCategory getEmployeeCategory() {
        return employeeCategory;
    }

    public void setEmployeeCategory(EmployeeCategory employeeCategory) {
        this.employeeCategory = employeeCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(name, employee.name) && Objects.equals(employeeCategory, employee.employeeCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, employeeCategory);
    }
}
