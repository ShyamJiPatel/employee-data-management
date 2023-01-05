package com.test.employeemanagement.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.test.employeemanagement.model.Company;
import com.test.employeemanagement.model.Employee;

public class ExcelHelper {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Emp Name", "Emp Salary", "Emp DOJ", "Company Name", "Company Website" };
	static String SHEET = "Sheet1";

	public static boolean hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static List<Employee> excelToEmployees(InputStream is) {
		try {
			Workbook workbook = new XSSFWorkbook(is);

			Sheet sheet = workbook.getSheet(SHEET);
			Iterator<Row> rows = sheet.iterator();

			List<Employee> employees = new ArrayList<Employee>();

			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();

				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();

				Employee employee = new Employee();
				Company company = new Company();

				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();

					switch (cellIdx) {
					case 0:
						employee.setName(currentCell.getStringCellValue());
						break;

					case 1:
						employee.setSalary(currentCell.getNumericCellValue());
						break;

					case 2:
						employee.setDoj(String.valueOf(currentCell.getNumericCellValue()));
						break;

					case 3:
						company.setName(currentCell.getStringCellValue());
						break;

					case 4:
						company.setWebsite(currentCell.getStringCellValue());
						break;

					default:
						break;
					}

					cellIdx++;
				}
				employee.setCompany(company);
				employees.add(employee);
			}

			workbook.close();

			return employees;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}
}
