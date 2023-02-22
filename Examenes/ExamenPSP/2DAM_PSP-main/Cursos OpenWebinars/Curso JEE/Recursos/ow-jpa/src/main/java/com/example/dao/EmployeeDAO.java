package com.example.dao;

import java.util.List;

import com.example.domain.Employee;

public interface EmployeeDAO {

	List<Employee> findAll();
	
	Employee findOne(Long id);
	
	boolean create(Employee employee);
	
	boolean update(Employee employee);
	
	boolean delete(Long id);
	
}
