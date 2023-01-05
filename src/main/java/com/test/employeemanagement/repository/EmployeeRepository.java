package com.test.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.employeemanagement.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
