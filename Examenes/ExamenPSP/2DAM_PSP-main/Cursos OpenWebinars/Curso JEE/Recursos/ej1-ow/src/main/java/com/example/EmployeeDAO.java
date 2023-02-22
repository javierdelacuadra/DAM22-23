package com.example;

import java.util.List;

public interface EmployeeDAO {

	List<Employee> findAll();
	
	Employee findOne(Long id);
	
	boolean create(Employee employee);
	
	boolean update(Employee employee);
	
	boolean delete(Long id);
	
}
