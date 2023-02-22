package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

	@Override
	public List<Employee> findAll() {
		// 1- Crear la conexion
		List<Employee> employees = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jakartaee_jdbc",
					"root", "admin");
			
			// 2 - ejecutar sentencias
			Statement statement = connection.createStatement();
			
			String sql = "SELECT * FROM employees;";
			
			// 3- procesar resultados
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				Long id = resultSet.getLong("id");
				String name = resultSet.getString("name");
				String nif = resultSet.getString("nif");
				Integer age = resultSet.getInt("age");
				System.out.println(id + " " + name + " " + nif + " " + age);
				Employee employee = new Employee(id, name, nif, age);
				employees.add(employee);
			}
			
			
			
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return employees;
	}

	@Override
	public Employee findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean create(Employee employee) {

		boolean result = false;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jakartaee_jdbc",
					"root", "admin");
			
			// 2 - ejecutar sentencias
			
			String sql = "INSERT INTO employees (name, nif, age) VALUES (?, ?, ?)";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, employee.getName());
			statement.setString(2, employee.getNif());
			statement.setInt(3, employee.getAge());
			statement.executeUpdate();
			result = true;
			
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			
		}
		return result;
		
	}

	@Override
	public boolean update(Employee employee) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
