package com.example.service;

import com.example.dao.EmployeeDAO;
import com.example.dao.EmployeeDAOJPAImpl;
import com.example.domain.Employee;

public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeDAO employeeDAO;
	
	public EmployeeServiceImpl() {
		this.employeeDAO = new EmployeeDAOJPAImpl();
	}
	
	@Override
	public boolean create(Employee employee) {
		// logica de negocio
		
		
		return this.employeeDAO.create(employee);

	}

}
