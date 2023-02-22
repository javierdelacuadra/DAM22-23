package com.example;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/employees")
@Produces("application/json")
@Consumes("application/json")
public class EmployeeResource {

	@GET
	public List<Employee> findAll() {
		
		return List.of(
				new Employee("Empleado 1", 40),
				new Employee("Empleado 2", 42)
				);
	}
}