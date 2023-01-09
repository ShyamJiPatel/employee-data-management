package com.test.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.employeemanagement.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
