package com.example.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="projects")
public class Project implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// atributos propios
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	private String prefix;
	
	// asociaciones
	@ManyToMany(mappedBy = "projects")
	private List<Employee> employees = new ArrayList<>();

	public Project() {
	}
	public Project(Long id, String title, String prefix) {
		super();
		this.id = id;
		this.title = title;
		this.prefix = prefix;
	}
	public Long getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	@Override
	public String toString() {
		return "Project [id=" + id + ", title=" + title + ", prefix=" + prefix + "]";
	}
	

	
}
