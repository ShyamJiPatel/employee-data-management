package com.test.employeemanagement.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.test.employeemanagement.helper.ExcelHelper;
import com.test.employeemanagement.model.Employee;
import com.test.employeemanagement.repository.EmployeeRepository;

@Service
public class ExcelService {
	@Autowired
	private EmployeeRepository repository;

	public void save(MultipartFile file) {
		try {
			List<Employee> tutorials = ExcelHelper.excelToEmployees(file.getInputStream());
			repository.saveAll(tutorials);
		} catch (IOException e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}
	}

	public List<Employee> getAllTutorials() {
		return repository.findAll();
	}
}
