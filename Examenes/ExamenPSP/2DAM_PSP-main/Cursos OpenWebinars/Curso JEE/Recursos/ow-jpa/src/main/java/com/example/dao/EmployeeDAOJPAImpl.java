package com.example.dao;

import java.util.List;

import com.example.domain.Employee;
import com.example.util.JpaUtil;

import jakarta.persistence.EntityManager;

public class EmployeeDAOJPAImpl implements EmployeeDAO{

	@Override
	public List<Employee> findAll() {
		EntityManager em = JpaUtil.getEntityManager();
		List<Employee> employees = em.createQuery("select e from Employee e", Employee.class).getResultList();
		 em.close();
		
		return employees;
	}

	@Override
	public Employee findOne(Long id) {
		EntityManager em = JpaUtil.getEntityManager();
		Employee employee = em.find(Employee.class, id);
		em.close();
		System.out.println(employee);
		return employee;
	}

	@Override
	public boolean create(Employee employee) {
		EntityManager em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(employee);
		em.getTransaction().commit();
		return true;
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
