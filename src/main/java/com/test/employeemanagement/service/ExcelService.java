package com.test.employeemanagement.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.test.employeemanagement.helper.ExcelHelper;
import com.test.employeemanagement.model.Employee;
import com.test.employeemanagement.repository.CompanyRepository;
import com.test.employeemanagement.repository.EmployeeRepository;

@Service
public class ExcelService {
	@Autowired
	private EmployeeRepository repository;
	@Autowired
	private CompanyRepository companyRepository;

	public void save(MultipartFile file) {
		try {
			List<Employee> employees = ExcelHelper.excelToEmployees(file.getInputStream());
			employees.forEach(employee -> {
				employee.setCompany(companyRepository.saveAndFlush(employee.getCompany()));
				repository.save(employee);
			});
//			repository.saveAll(employees);
		} catch (IOException e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}
	}

	public List<Employee> getAllEmployees() {
		return repository.findAll();
	}
}
